package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.ServicePostal;
import com.strim1.mavenproject1.ServicePostalUDP;
import java.net.Socket;
import java.util.Hashtable;

/**
 *
 * @author guigui
 */
public class TraitementClient extends Thread{
    private Hashtable <Integer,Client> clients;
    
    private final ServicePostal servicePostal;
    /**
     * la demande du client vers le serveur gestion
     */
    private String demandeClient;
    /**
     * la reponse du serveur au client
     */
    private String reponseServeur;
    
    private final ServicePostalUDP servicePostalUDP;
    
    public TraitementClient(Socket service,Hashtable <Integer,Client> clients,ServicePostalUDP servicePostalUDP) {
        this.servicePostal=new ServicePostal(service);
        this.servicePostalUDP=servicePostalUDP;
        this.clients=clients;
    }
    
    private String traitementRequete(){
        String[] decoupageRequete;
        //decoupe la requète
        decoupageRequete=demandeClient.split("#");
        if(decoupageRequete[0].equals("HELLO")){
            return ajoutUilisateur(decoupageRequete);
        }
        else if(!contenuListe(Integer.valueOf(decoupageRequete[1]))){
            switch(decoupageRequete[0]){
                case "LISTEMSG":
                    return ListeMsg(decoupageRequete);
                case "LIRE":
                    return lire(decoupageRequete);
                case "POSTER":
                    return poster(decoupageRequete);
                case "CONTACT":
                    return contact(decoupageRequete);
                default:
                    break;
            }
        }
        return "ERRROR#requête inconnue.";
    }
    
    private String ListeMsg(String[] decoupageRequete) {
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            return bdd.messagerie(Integer.valueOf(decoupageRequete[1]));
        }
        return "ERROR";
    }
    
    private String lire(String[] decoupageRequete) {
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            return bdd.afficherMessage(Integer.valueOf(decoupageRequete[1]));
        }
        return "ERROR";
    }
    
    private String poster(String[] decoupageRequete) {
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            //return bdd.envoyerMessage(Integer.valueOf(decoupageRequete[1]),Integer.valueOf(decoupageRequete[2]),decoupageRequete[3],decoupageRequete[4]);
        }
        return "ERROR";
    }
    
    private String contact(String[] decoupageRequete) {
        int idClient=Integer.valueOf(decoupageRequete[1]);
        String retour="";
        for (Integer id : clients.keySet())
        {
            if(id!=idClient){
                Client c=clients.get(idClient);
                if(c.isDirect()){
                    retour+=("#"+Integer.toString(idClient)+c.getInformations());
                }
            }
        }
        return retour;
    }
    
    private String ajoutUilisateur(String[] decoupageRequete) {
        int idClientAjout=Integer.valueOf(decoupageRequete[1]);
        
        if(clients.containsKey(idClientAjout)){
            clients.remove(idClientAjout);
        }
        Client cAjout=new Client(decoupageRequete[3],Integer.valueOf(decoupageRequete[4]),idClientAjout);
        
        for (Integer idClient : clients.keySet())
        {
            Client c=clients.get(idClient);
            if(c.isDirect())
                servicePostalUDP.envoyer(decoupageRequete[1]+cAjout.getInformations(), c.getIp(), c.getPort());
        }
        clients.put(idClientAjout,cAjout);
        return "OK";
    }
    
    private boolean contenuListe(int idclient) {
        return clients.containsKey(idclient);
    }
    
    @Override
    public void run(){
        boolean fermeture=false; //boolean permettant de fermer le thread
        while(!fermeture){
            demandeClient=servicePostal.reception(); //attend la demande du client
            if(demandeClient!=null){
                reponseServeur=traitementRequete(); // traite la requète du serveur
                servicePostal.emission(reponseServeur);//Envoie le message au client
            }
            else fermeture=true;// si la demande du client == null on ferme le programme
        }
        System.out.println("demande de deconnexion client.");
        servicePostal.deconnexion();//on ferme le socket
    }
}
