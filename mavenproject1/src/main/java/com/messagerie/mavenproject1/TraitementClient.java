package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.ServicePostal;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author guigui
 */
public class TraitementClient extends Thread{
    private Vector <Client> clients;
    
    private final ServicePostal servicePostal;
    /**
     * la demande du client vers le serveur gestion
     */
    private String demandeClient;
    /**
     * la reponse du serveur au client
     */
    private String reponseServeur;
    
    TraitementClient(Socket service,Vector <Client> clients) {
        this.servicePostal=new ServicePostal(service);
        this.clients=clients;
    }
    
    private String traitementRequete(){
        String[] decoupageRequete;
        //decoupe la requète
        decoupageRequete=demandeClient.split("#");
        switch(decoupageRequete[0]){
            case "HELLO":
                return ajoutUilisateur(decoupageRequete);
            case "INFO":
                return informationClient(decoupageRequete);
            case "LIRE":
                return lire(decoupageRequete);
            case "POSTER":
                return poster(decoupageRequete);
            case "APPEL":
                return appel(decoupageRequete);
            case "CONTACT":
                return contact(decoupageRequete);
            default:
                return "ERRROR#requête inconnue.";
        }
    }
    
    private String informationClient(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String lire(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String poster(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String appel(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String contact(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String ajoutUilisateur(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
