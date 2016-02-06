package com.serveurinscription.mavenproject1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Classe qui créé le serveur d'incription 
 * 
 */
public class ServeurInscription{
    /**
     * le socket de service entre un client et serveur
     */
    private Socket service;
    /**
     * le socket d'ecoute du serveur
     */
    private ServerSocket ecoute;
    /**
     * definit le socket d'écoute du serveur
     * @param port le port d'ecoute du serveur
     */
    public ServeurInscription(int port){
        this.service=new Socket();
        try {
            this.ecoute=new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServeurInscription.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * attente d'un client sur le port d'ecoute
     * ouvre un thread pour le traitement d'un client
     */
    public void fonctionnementService(){
        while(true){
            System.out.println("attente client...");
            try {
                service=ecoute.accept();//accepte un client
                System.err.println("Nouvelle connexion : "+ service);
                new TraitementInscription(service).start();//ouvre un thread pour traiter un client
            } catch (IOException ex) {
                Logger.getLogger(ServeurInscription.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
     /**
     * main du serveur de d'inscription 
     * definition du port d'ecoute du serveur
     * @param args 
     */
    public static void main(String[] args) {
        System.out.println("***debut serveur Inscription***");
        new ServeurInscription(50005).fonctionnementService();
    }
}
