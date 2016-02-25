
package com.messagerie.mavenproject1;
import com.strim1.mavenproject1.ServicePostalUDP;
import java.util.Hashtable;

/**
 *  La classe serveurMessagerie est le programme principal du Serveur Messagerie
 * lance les deux threads AccueilClient Messagerie et AccueilServeurMessagerie
 * definit les deux ports d'ecoute de connexion des clients et serveurs 
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
    
    /**
     * La liste des clients déclarés au serveur Messagerie
     */
    private final Hashtable <Integer,Client> clients;
    
    /**
     * Le service postal qui gere les emissions et receptions en UDP
     */
    private final ServicePostalUDP servicePostalUDP=new ServicePostalUDP(50008);
    
    /**
     * le constructeur qui initialise la liste des clients, elle est vide à la construction
     * @param portClient le port d'ecoute pour les clients
     * @param portServeur  le port d'ecoute pour les autres serveurs
     */
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
    
    /**
     * main principal du serveur Messagerie
     * @param args 
     */
    public static void main(String[] args) {
        System.out.println("debut serveur Connexion");
        new ServeurMessagerie(50006,50007).fonctionnementService();
    }
}
