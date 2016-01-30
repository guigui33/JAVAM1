/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.serveurinscription.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigui
 */
public class ServeurInscription{
    private Socket service;
    private ServerSocket ecoute;
    
    public ServeurInscription(int port){
        this.service=new Socket();
        try {
            this.ecoute=new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServeurInscription.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void fonctionnementService(){
        while(true){
            System.out.println("attente client...");
            try {
                service=ecoute.accept();
                System.err.println("Nouvelle connexion : "+ service);
                new TraitementInscription(service).start();
            } catch (IOException ex) {
                Logger.getLogger(ServeurInscription.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    public static void main(String[] args) {
        System.out.println("***debut serveur Inscription***");
        new ServeurInscription(50005).fonctionnementService();
    }
}
