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
public class TraitementClient extends Thread {
    private Socket service;
    private String demandeClient;
    private String retourServeur;
    
    public TraitementClient(Socket service){
        this.service=service;
    }
        
    private void reception(){
        InputStreamReader fluxEntree=null;
        
        try {
            fluxEntree = new InputStreamReader(service.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
        if(fluxEntree!=null){
            BufferedReader lecture=new BufferedReader(fluxEntree);
            try {
                demandeClient=lecture.readLine();
            } catch (IOException ex) {
                Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /*fermeture connexion client*/
            if(demandeClient==null){
                try {
                    service.close();// ??? fermeture du socket client client
                } catch (IOException ex) {
                    Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void emission(){
        PrintStream fluxSortie=null;
        try {
            fluxSortie = new PrintStream(service.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(fluxSortie!=null){
                fluxSortie.println(retourServeur);
        }
    }
    
    public void run(){
        reception();
        TraitementDemande traitementDemande=new TraitementDemande();
        retourServeur=traitementDemande.requete(demandeClient);
        emission();
    }
}
