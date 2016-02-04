package com.serveurConnexion.mavenproject1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigui
 */
class AccueilServeurCo extends Thread{
    private Socket service;
    private ServerSocket ecoute;
    private final Clients clients;
    
    public AccueilServeurCo(int portServeur,Clients clients) {
        try {
            ecoute=new ServerSocket(portServeur);
        } catch (IOException ex) {
            Logger.getLogger(AccueilServeurCo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.clients=clients;
    }

    public void run(){
           while(true){
            try {
                System.out.println("attente serveur...");
                service=ecoute.accept();
                System.err.println("Nouvelle connexion : "+ service);
                new TraitementServeur(service,clients).start();
            } catch (IOException ex) {
                Logger.getLogger(AccueilClientCo.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }
}
