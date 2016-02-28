    package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.ServicePostal;
import com.strim1.mavenproject1.ServicePostalUDP;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;

/**
 *Traite les demades des clients
 * la liste des clients est partagée entre les threas
 * le servie postal est partagée entre les threads
 */
public class TraitementClient extends Thread{
    /**
     * la liste des clients déclarés au Serveur Messagerie
     */
    private Hashtable <Integer,Client> clients;
    
    /**
     * Le service qui gere les receptions et emissions en UDP, partagée par les threads
     */
    private final ServicePostal servicePostal;
    /**
     * la demande du client vers le serveur gestion
     */
    private String demandeClient;
    
    /**
     * la reponse du serveur au client
     */
    private String reponseServeur;
    
    /**
     * Le service qui gere les receptions et emissions en UDP, partagée par les threads
     */
    private final ServicePostalUDP servicePostalUDP;
    
    /**
     * Le constructeur qui permet de initialiser le service postal, la liste des clients et le service postal udp
     * @param service le socket de service
     * @param clients la liste des clients
     * @param servicePostalUDP le service postal udp partagé par les threads
     */
    public TraitementClient(Socket service,Hashtable <Integer,Client> clients,ServicePostalUDP servicePostalUDP) {
        this.servicePostal=new ServicePostal(service);
        this.servicePostalUDP=servicePostalUDP;
        this.clients=clients;
    }
    
    /**
     * traitement de la requète du client
     * en fonction d'un mot clé, le client est dirigé sur le service demandé
     * @return une chaine de caractère contenant les informations demandées ou une erreur
     */
    private String traitementRequete(){
        String[] decoupageRequete;
        //decoupe la requète
        decoupageRequete=demandeClient.split("#");
        //si c'est une requète HELLO, le client se declare au Serveur comme étant connecté
        if(decoupageRequete[0].equals("HELLO")){
            //une verification au pres du serveur Connexion est éffectuée
            return ajoutUilisateur(decoupageRequete);
        }
        //si autre requète le Serveur test si le client est dans sa liste d'utilsateurs déclarées
        else if(contenuListe(Integer.valueOf(decoupageRequete[1]))){
            switch(decoupageRequete[0]){
                case "LISTEMSG":
                    return ListeMsg(decoupageRequete);
                case "LIRE":
                    return lire(decoupageRequete);
                case "POSTER":
                    return poster(decoupageRequete);
                case "CONTACT":
                    return contact(decoupageRequete);
                case "UTILISATEURS":
                    return utilisateurs(decoupageRequete);
                default:
                    return "ERROR#requète inconnue.";
            }
        }
        else
            return "ERROR#Vous devez etre connecté.";
    }
    
    /**
     * retourne la liste des messages de l'utilisateur
     * @param decoupageRequete la requète du client
     * @return la liste des messages ou une erreur
     */
    private String ListeMsg(String[] decoupageRequete) {
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            //l'identifiat de l'utilisateur
            return bdd.messagerie(Integer.valueOf(decoupageRequete[1]));
        }
        return "ERROR";
    }
    
    /**
     * retourne le contenu du message identifié en paramètre
     * @param decoupageRequete la demande de l'utilisateur contenant l'identifiant du message
     * @return le contenu du message
     */
    private String lire(String[] decoupageRequete) {
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            //l'identifiant du message
            return bdd.afficherMessage(Integer.valueOf(decoupageRequete[2]));
        }
        return "ERROR";
    }
    
    /**
     * l'utilisateur veut envoyer un message à un autre utilisateur
     * @param decoupageRequete la demande de l'utilisateur
     * @return OK si tout c'est bien passé, ERROR sinon
     */
    private String poster(String[] decoupageRequete) {
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            //idClient#idRecepteur#objet#message
            return bdd.envoyerMessage(Integer.valueOf(decoupageRequete[1]),Integer.valueOf(decoupageRequete[2]),decoupageRequete[3],decoupageRequete[4]);
        }
        return "ERROR";
    }
    
    /**
     * la liste des contacts disponibles pour le chat
     * @param decoupageRequete la demande du client
     * @return la liste des utilisateurs disponibles
     */
    private String contact(String[] decoupageRequete) {
        //identifiant de l'utilisateur demandeur
        int idClient=Integer.valueOf(decoupageRequete[1]);
        String retour="LISTE"; //initalise la reponse du serveur
        Client clientDemandeur=clients.get(idClient);
        //si le client est disponible
        if(clientDemandeur.isDisponible()){
            for (Integer id : clients.keySet())
            {
                if(id!=idClient){
                    Client c=clients.get(idClient);
                    //test si l'utilsateur est disponible pour le chat
                    if(c.isDisponible()){
                        //obtient les informatons: #id#nom#prenom#ip#port
                        retour+=('$'+Integer.toString(idClient)+c.getInformations(id));
                    }
                }
            }
            return retour;
        }
        //sinon message d'erreur
        return "ERROR#vous devez être disponible pour voir la liste du chat.";
    }
    
    /**
     * Apres une requète HELLO, ajoute un utilisateur dans la liste des clients
     * avant verification demande au serveur Connexion si le client est bien connecté en utilisant l'identifiant et le numero de session
     * ajout l'utilsateur dans la liste
     * previent les autres utilisateurs de l'ajout si l'utilisateur veut être disponible
     * @param decoupageRequete la demande
     * @return "OK"
     */
    private String ajoutUilisateur(String[] decoupageRequete) {
        //recupere l'identifiant de l'utilisateur
        int idClientAjout=Integer.valueOf(decoupageRequete[1]);
        
        //dans le cas où si la liste contient l'utilisateur d'une ancienne connexion
        if(clients.containsKey(idClientAjout)){
            clients.remove(idClientAjout);
        }
        
        if(verificationConnexion(decoupageRequete[1],decoupageRequete[2])){
            //creation du nouveau client disponible
            Client cAjout=new Client(decoupageRequete[3],Integer.valueOf(decoupageRequete[4]),idClientAjout);
            //si le client est disponible alors on previent les utilisateurs
            if(cAjout.isDisponible()){
                for (Integer idClient : clients.keySet())
                {
                    Client c=clients.get(idClient);
                    //pour tous les utilisateurs disponibles on envoit les informations sur le nouveau client
                    if(c.isDisponible())
                        servicePostalUDP.envoyer(decoupageRequete[1]+cAjout.getInformations(idClient), c.getIp(), c.getPort());
                }
            }
            //ajout du client dans la liste
            clients.put(idClientAjout,cAjout);
            return "OK";
        }
        return "ERROR#Vous etes pas connecté.";
    }
    
    /**
     * retourne true si le client est connecté, false sinon
     * @param identifiant l'identifiant du client
     * @param numS le numero de session généré par le serveur de Connexion
     * @return retourne true si le client est connecté, false sinon
     */
    public boolean verificationConnexion(String identifiant,String numS){
        try {
            //ouverture d'un session avec le serveur Connexion
            Socket serveurConnexion=new Socket("localhost",50002);
            ServicePostal servicePostalCo = new ServicePostal(serveurConnexion);
            
            /*creation de la requète pour le serveur Connexion
            *VERIFCONNEXION#idClient#numeroSesssion
            */
            String requete="VERIFCONNEXION#"+identifiant+"#"+numS;
            System.out.println("requete au serveur de connexion: "+requete);
            //emet vers le serveur de connexion la requète
            servicePostalCo.emission(requete);
            //attend la reponse du serveur
            String reponse= servicePostalCo.reception();
            System.out.println("Reponse du serveur connexion: " + reponse);
            servicePostalCo.deconnexion();//ferme le socket
            return reponse.equals("OK");//true si la reponse est ok, false sino
        } catch (IOException ex) {
            return false;//si une erreur arrive on retourne par défaut false
        }
    }
    
    /**
     * test si le client est dans la liste
     * @param idclient identifiant Bdd du client
     * @return true si il est dans la liste, false sinon
     */
    private boolean contenuListe(int idclient) {
        return clients.containsKey(idclient);
    }
    
    /**
     * run qui permet de recevoir, traiter la demande et emettre la reponse
     */
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
    
    /**
     * retourne les utilisateurs de l'application sauf l'utilisateur demandeur
     * @param decoupageRequete la demande de l'utilisateur
     * @return la liste des utilisateurs ou ERROR si problème
     */
    private String utilisateurs(String[] decoupageRequete) {
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            return bdd.utilisateurs(Integer.valueOf(decoupageRequete[1]));
        }
        return "ERROR";
    }
}
