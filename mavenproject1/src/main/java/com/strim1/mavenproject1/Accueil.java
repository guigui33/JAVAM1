/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strim1.mavenproject1;

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
    
    Socket service; //socket de service 
    ServerSocket ecoute; //
    /**
     * Construteur Accueil 
     * int port le port du service 
     */
    public Accueil(int port){
        try {
            ecoute=new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int fonctionnementService(){
        while(true){
            try {
                service=ecoute.accept();
            } catch (IOException ex) {
                Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
            }
            new TraitementClient(service).start();
        }
    }
    
    /**
     * @param args the command line arguments
     * test de la classe
     */
    public static void main(String[] args) {
      System.out.println("debut programme!!!");
    }
}
