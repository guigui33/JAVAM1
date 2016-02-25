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
 *Cette Classe permet de d'attendre les clients sur le port d'ecoute
 * Il permet de creer un thread qui traitera les demandes des clients.
 * La liste des clients déclarés au serveur est partagée dans different Thread
 * Le Service postal UDP est partagé par les threads
 */
public class AccueilClientMessagerie extends Thread{
    /**
     * la liste des clients des utilisateurs déclarés au serveur de messagerie
     */
    private final Hashtable <Integer,Client> clients;
    
    /**
     *le Servie Postal permettant de gérer les envois et les receptions en UDP
     */
    private final ServicePostalUDP servicePostalUDP;
    
    /**
     * le socket de service entre un client et serveur
     */
    private Socket service;
    
    /**
     * le socket d'ecoute du serveur
     */
    private ServerSocket ecoute;
    
    /**
     * Contructeur de la classe AccueilClientMessagerie.
     * permet de fixer les paramètres de la classe
     * @param portClient le port d'ecoute du client
     * @param clients  la liste des clients déclarés au serveur de messageri, vide à la construction
     * @param servicePostalUDP le service postal UDP partagé par les threads 
     */
    public AccueilClientMessagerie(int portClient, Hashtable <Integer,Client> clients,ServicePostalUDP servicePostalUDP){
        this.clients=clients;
        this.servicePostalUDP=servicePostalUDP;
        try {
            this.service=new Socket();
            this.ecoute=new ServerSocket(portClient);
        } catch (IOException ex) {
            Logger.getLogger(AccueilClientMessagerie.class.getName()).log(Level.SEVERE, null, ex);
            exit(1);
        }
    }
    
    /**
     * Fonctionnement de l'accueil des clients
     * Attend q'un client se connecte puis creation d'un thread pour traiter ses demandes
     */
    public void fonctionnementService(){
        while(true){
            System.out.println("attente client...");
            try {
                service=ecoute.accept();//accepte un client
                System.out.println("Nouvelle connexion : "+ service);
                new TraitementClient(service,clients,servicePostalUDP).start();//ouvre un thread pour traiter un client
            } catch (IOException ex) {
                Logger.getLogger(ServeurMessagerie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     *fonctionnement de la classe.
     **/
    @Override
    public void run(){
        fonctionnementService();
    }
}
