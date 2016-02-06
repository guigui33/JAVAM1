/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.strim1.mavenproject1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigui
 */
public class ServicePostal {
    private final Socket connexionCourante;
    
    public ServicePostal(Socket socket){
        this.connexionCourante=socket;
    }
    
    public String reception(){
        String demandeClient=null;
        InputStreamReader fluxEntree=null;
        try {
            fluxEntree = new InputStreamReader(connexionCourante.getInputStream());
            BufferedReader lecture=new BufferedReader(fluxEntree);
            
            try {
                demandeClient=lecture.readLine();
                System.out.println("demande client: "+demandeClient);
            } catch (IOException ex) {
                Logger.getLogger(ServicePostal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServicePostal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return demandeClient;
    }
    
    public void emission(String retourServeur){
        PrintStream fluxSortie=null;
        try {
            fluxSortie = new PrintStream(connexionCourante.getOutputStream());            
            System.out.println("retour Serveur: "+retourServeur);
            fluxSortie.println(retourServeur);
            
        } catch (IOException ex) {
            Logger.getLogger(ServicePostal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * methode permetant de fermer le socket de service entre le client et le serveur
     */ 
    public void deconnexion() {
        System.err.println("deconnexion Serveur : " + connexionCourante);
                  try {
                connexionCourante.close();
            } catch (IOException ex) {
                Logger.getLogger(ServicePostal.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
}
