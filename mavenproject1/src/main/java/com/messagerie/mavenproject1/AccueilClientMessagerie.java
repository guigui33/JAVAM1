/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author guigui
 */
public class AccueilClientMessagerie extends Thread{
       private final Hashtable <Integer,Client> clients;
    private final ServicePostalUDP servicePostalUDP;
     /**
     * le socket de service entre un client et serveur
     */
    private Socket service;
       
    /**
     * le socket d'ecoute du serveur
     */
    private ServerSocket ecoute;

    public AccueilClientMessagerie(int portClient, Hashtable <Integer,Client> clients,ServicePostalUDP servicePostalUDP){
              this.clients=clients;
              this.servicePostalUDP=servicePostalUDP;
           try {
               this.service=new Socket();
               this.ecoute=new ServerSocket(portClient);               
           } catch (IOException ex) {
               Logger.getLogger(AccueilClientMessagerie.class.getName()).log(Level.SEVERE, null, ex);
               
           }
           exit(1);
       }    
    
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
     
       @Override
     public void run(){
         fonctionnementService();
     }
}
