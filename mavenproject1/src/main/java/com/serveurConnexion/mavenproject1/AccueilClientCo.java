/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class AccueilClientCo extends Thread {
    
    private Socket service;
    private ServerSocket ecoute;
   
    private final HashMap <Integer,Integer> clients;
    
    AccueilClientCo(int portClient,HashMap <Integer,Integer> clients) {
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
                service=ecoute.accept();
                System.err.println("Nouvelle connexion : "+ service);
                new TraitementClient(service,clients).start();
            } catch (IOException ex) {
                Logger.getLogger(AccueilClientCo.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }
}
