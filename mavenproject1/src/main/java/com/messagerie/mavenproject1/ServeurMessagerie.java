
package com.messagerie.mavenproject1;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class ServeurMessagerie {
    private Vector <Client> clients;
    
     /**
     * le socket de service entre un client et serveur
     */
    private Socket service;
    /**
     * le socket d'ecoute du serveur
     */
    private ServerSocket ecoute;

    public ServeurMessagerie(int port){
        
        try {
            this.service=new Socket();
            this.ecoute=new ServerSocket(port);
            this.clients=new Vector<>();
        } catch (IOException ex) {
            Logger.getLogger(ServeurMessagerie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void fonctionnementService(){
        while(true){
            System.out.println("attente client...");
           
            try {
                service=ecoute.accept();//accepte un client
                System.out.println("Nouvelle connexion : "+ service);
                new TraitementClient(service,clients).start();//ouvre un thread pour traiter un client
            } catch (IOException ex) {
                Logger.getLogger(ServeurMessagerie.class.getName()).log(Level.SEVERE, null, ex);
            }                     
        }
    }
     public static void main(String[] args) {
        System.out.println("debut serveur Connexion");
        new ServeurMessagerie(50006).fonctionnementService();
    }
}
