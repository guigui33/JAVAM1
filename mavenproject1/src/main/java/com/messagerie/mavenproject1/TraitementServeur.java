package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.ServicePostal;
import com.strim1.mavenproject1.ServicePostalUDP;
import java.net.Socket;
import java.util.Hashtable;

/**
 *
 * @author guigui
 */
public class TraitementServeur extends Thread {
    /**
     * la demande du client
     */
    private String demandeServeur;
    /*
    * la reponse du serveur
    */
    private String retourServeur;
    
    /**
     * classe qui gere la reception et l'emission
     * @see ServicePostal
     */
    private final ServicePostal servicePostal;
    
    private final ServicePostalUDP servicePostalUDP;
    
    private final Hashtable <Integer,Client> clients;
    
    
    public TraitementServeur(Socket service,Hashtable <Integer,Client> clients,ServicePostalUDP servicePostalUDP){
        this.servicePostal=new ServicePostal(service);
        this.servicePostalUDP=servicePostalUDP;
        this.clients=clients;
    }
    
    public String requete() {
        /*decouper la requte en tableau */
        String[] decoupageRequete;
        decoupageRequete = demandeServeur.split("#");
        //en fonction du mot cle on redirige vers le service correspondant
        switch (decoupageRequete[0]) {
            case "DECONNEXION":
                if (decoupageRequete.length != 2) {
                    return "ERROR";
                }
                return deconnexionClient(decoupageRequete);
            default:
                return "ERROR";
        }
    }
    
    private String deconnexionClient(String[] demande) {
        Integer idClientSupp = Integer.parseInt(demande[1]);
        clients.remove(idClientSupp);
        for (Integer idClient : clients.keySet())
        {
            Client c=clients.get(idClient);
            if(c.isDirect())
                servicePostalUDP.envoyer(idClient.toString()+c.getInformations(), c.getIp(), c.getPort());
        }
        return "OK";
    }
    
    @Override
    public void run() {
        boolean fermeture = false;
        while (!fermeture) {
            demandeServeur = servicePostal.reception();//receptionne la demande
            if (demandeServeur != null) {
                retourServeur = requete();//traite la demande
                servicePostal.emission(retourServeur);//emet la reponse
            } else {
                fermeture = true;
            }
        }
        System.out.println("demande de deconnexion serveur.");
        servicePostal.deconnexion();//ferme le socket client
    }
}
