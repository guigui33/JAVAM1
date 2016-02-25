package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.ServicePostalUDP;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Cette classe permet d'attendre les connexions des autres Serveurs de l'application.
 * La liste des clients délarés au serveur de Messagerie et le service postal UDP sont partagés par les differents Thread de la Messagerie
 * La classe attend qu'un autre serveur se connecte et créé un Thread qui traitera la demande du Serveur
 */
public class AccueilServeurMessagerie extends Thread {
    /**
     * Socket de service pour le dialogue entre le serveur Connexion et les autres Serveurs
     */
    private Socket service;
    
    /**
     * le socket d'ecoute du serveur de connexion
     */
    private ServerSocket ecoute;
    
    /**
     * Le service postal qui gere les emissions et receptions en UDP
     */
    private final ServicePostalUDP servicePostalUDP;
    
    /**
     *la liste des clients déclarés au Serveur Messagerie, partagée par tous les Threads 
     */
    private final Hashtable <Integer,Client> clients;
    
    /**
     * Le contrcteur de la classe.
     * 
     * @param portClient le port d'ecoute du Serveur Messagerie pour les demandes des autres Serveurs
     * @param clients la liste des clients déclarés au Serveur Messagerie
     * @param servicePostalUDP le service postal gérant les échanges en UDP
     */
    public AccueilServeurMessagerie(int portClient, Hashtable <Integer,Client> clients,ServicePostalUDP servicePostalUDP){
        this.clients=clients;
        this.service=new Socket();
        this.servicePostalUDP=servicePostalUDP;
        try {
            this.ecoute=new ServerSocket(portClient);
        } catch (IOException ex) {
            Logger.getLogger(AccueilServeurMessagerie.class.getName()).log(Level.SEVERE, null, ex);
            exit(1);
        }
    }
    
    /**
     * 
     */
    public void run(){
        while(true){            
            try {
                System.out.println("attente serveur...");
                //attend qu'un serveur se connecte
                service=ecoute.accept();
                System.err.println("Nouvelle connexion : "+ service);
                //traite la demande du serveur dans un thread
                new TraitementServeur(service,clients,servicePostalUDP).start();
            } catch (IOException ex) {
                Logger.getLogger(AccueilServeurMessagerie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
