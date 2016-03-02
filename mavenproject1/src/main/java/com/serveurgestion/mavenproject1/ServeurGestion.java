package com.serveurgestion.mavenproject1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *permet de lancer le serveur de gestion des clients
 *on definit le port d'ecoute du serveur 
 * receptionne les clients et créé les threads 
 */
public class ServeurGestion {
/**
 * le socket de service entre le serveur et le client
 */
    private Socket service;
    /**
     * le socket d'ecoute du client
     */
    private ServerSocket ecoute;
    private Vector<Integer> appels;
    
    /**
     * contructeur de la classe, definit le socket  d'ecoute du serveur
     * @param portClient le port d'ecoute du serveur
     */
    public ServeurGestion(int portClient){
        try {
            ecoute=new ServerSocket(portClient);
            appels=new Vector<>();
        } catch (IOException ex) {
            Logger.getLogger(ServeurGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   /**
    * attente de client sur le port d'ecoute
    * créé le thread de traitement du client
    */
    public void fonctionnementService(){
       while(true){            
           try {
               System.out.println("attente client...");
               service=ecoute.accept();//accepte un client
               System.err.println("Nouvelle connexion : "+ service);
               new TraitementClient(service).start();//thread de traitement du client
           } catch (IOException ex) {
               Logger.getLogger(ServeurGestion.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }
    /**
     * main du serveur de Gestion 
     * definition du port d'ecoute du serveur
     * @param args 
     */
    public static void main(String[] args) {
        System.out.println("***debut serveur gestion ***");
        new ServeurGestion(50003).fonctionnementService();
                
    }
}
