/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serveurgestion.mavenproject1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigui
 */
public class ServeurGestion {

    private Socket service;
    private ServerSocket ecoute;
    
    public ServeurGestion(int portClient){
        try {
            ecoute=new ServerSocket(portClient);
        } catch (IOException ex) {
            Logger.getLogger(ServeurGestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void fonctionnementService(){
       while(true){            
           try {
               System.out.println("attente client...");
               service=ecoute.accept();
               System.err.println("Nouvelle connexion : "+ service);
               new TraitementClient(service).start();
           } catch (IOException ex) {
               Logger.getLogger(ServeurGestion.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }
    public static void main(String[] args) {
        System.out.println("***debut serveur gestion ***");
        new ServeurGestion(50003).fonctionnementService();
    }
}
