
package com.messagerie.mavenproject1;
import com.strim1.mavenproject1.ServicePostalUDP;
import java.util.Hashtable;

/**
 * 
 */
public class ServeurMessagerie {
    /**
     * le port d'ecoute du serveur pour les clients
     */
    private final int portClient;
    
    /**
     * le port d'ecoute du serveur pour les autres serveurs
     */
    private final int portServeur;
    
    private final Hashtable <Integer,Client> clients;
    
    private final ServicePostalUDP servicePostalUDP=new ServicePostalUDP(50008);
    
    public ServeurMessagerie(int portClient,int portServeur){
        this.clients=new Hashtable<>();
        this.portClient=portClient;
        this.portServeur=portServeur;
    }
    
    /**
     * création des deux threads qui traiteront les serveurs et les clients
     */
    public void fonctionnementService(){
        AccueilClientMessagerie acm=new AccueilClientMessagerie(portClient, clients,servicePostalUDP);
        AccueilServeurMessagerie asm=new AccueilServeurMessagerie(portServeur,clients,servicePostalUDP);
        acm.start();
        asm.start();
    }
    
    public static void main(String[] args) {
        System.out.println("debut serveur Connexion");
        new ServeurMessagerie(50006,50007).fonctionnementService();
    }
}
