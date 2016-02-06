
package com.serveurConnexion.mavenproject1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *la classe AccueilClientCo permet de creer un thread qui attend qu'un client se connecte.
 * Dès qu'un client se connecte il sera dirigé vers un autre thread qui traitera sa demande.
 *
 */
public class AccueilClientCo extends Thread {
    
    /*
    * service est le socket qui permet la communication entre un client et le serveur
    */
    private Socket service;
    
    /**
     *Le socket d'ecoute du serveur
     */
    private ServerSocket ecoute;
    
    /**
     * La liste des clients connectés, les clients sont ajoutés ou supprimés
     * @see Clients
     */
    private final Clients clients;
    
    /**
     * A la construction d'un objet AccueilClientCo, le port d'ecoute du serveur est fixé
     * la liste de clients est vide
     * @param portClient le port d'écoute du serveur est fixé
     * @param clients la liste des clients qui est vide à sa contruction
     */
    AccueilClientCo(int portClient,Clients clients) {
        try {
            ecoute=new ServerSocket(portClient);
            
        } catch (IOException ex) {
            Logger.getLogger(AccueilClientCo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.clients=clients;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                
                System.out.println("attente client...");
                //methode bloquante, attend q'un client se connecte
                service=ecoute.accept();
                System.err.println("Nouvelle connexion : "+ service);
                //créé le thread pour le client, utilisation du socket de service pour le dialogue avec le client
                new TraitementClient(service,clients).start();
            } catch (IOException ex) {
                Logger.getLogger(AccueilClientCo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
