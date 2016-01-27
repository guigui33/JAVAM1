/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serveurgestion.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigui
 * la classe Accueil attend de recevoir la conection des clients
 * Connexion TCP 
 * renvoie le client sur un thread qui va gerer les demandes des clients
 */
public class Accueil {
    private Socket service; //socket de service 

    private ServerSocket socketClient; 
    private ServerSocket socketServeur;
    private int portClient=50003;
    private int portServeur=50004;
    
    /**
     * Construteur Accueil 
     * 
     */
    public Accueil(){
        try {
            socketClient=new ServerSocket(portClient);
            socketServeur=new ServerSocket(portServeur);
        } catch (IOException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    /**
     * 
     * attend la connexion d'un client
     * lance le thread qui va traiter les demandes du clients
     */
    public void fonctionnementService(){
        while(true){
            try {
                System.out.println("attente client...");
                service=ecoute.accept();
                System.err.println("Nouvelle connexion : "+ service);
            } catch (IOException ex) {
                Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
            }
            new TraitementClient(service,bdd).start();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("debut programme");
        new Accueil(50000).fonctionnementService();
    }
}
