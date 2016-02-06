package com.serveurConnexion.mavenproject1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *la classe AccueilServeurCo permet de creer un thread qui attend qu'un autre serveur se connecte.
 * Dès qu'un serveur se connecte il sera dirigé vers un autre thread qui traitera sa demande.
 * Dans ce cas les serveurs "demandeurs" sont traités comme des clients.
 * Cette classe permet de séparer les demandes des serveurs et des clients.
 */
class AccueilServeurCo extends Thread{
    /**
     * Socket de service pour le dialogue entre le serveur Connexion et les autres Serveurs(clients)
     */
    private Socket service;
    
    /**
     * le socket d'ecoute du serveur de connexion
     */
    private ServerSocket ecoute;
    
    /**
     * la liste des clients connectés, vide à sa création
     * Cette variable est partagée entre les threads AccueilClientCo et AccueilServeurCo
     */
    private final Clients clients;
    
    /**
     * Le contructeur permet de definir le port d'ecoute qui est fixe et définir la liste des Clients
     * @param portServeur le port d'ecoute du serveur
     * @param clients la liste des clients, vide à sa création
     */
    public AccueilServeurCo(int portServeur,Clients clients) {
        try {
            ecoute=new ServerSocket(portServeur);
        } catch (IOException ex) {
            Logger.getLogger(AccueilServeurCo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.clients=clients;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                System.out.println("attente serveur...");
                //attend qu'un serveur se connecte
                service=ecoute.accept();
                System.err.println("Nouvelle connexion : "+ service);
                //traite la demande du serveur dans un thread
                new TraitementServeur(service,clients).start();
            } catch (IOException ex) {
                Logger.getLogger(AccueilClientCo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
