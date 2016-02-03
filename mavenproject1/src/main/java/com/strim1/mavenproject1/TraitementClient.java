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
    private final Socket connexionCourante;
    private boolean fermeture;
    
    private String demandeClient;
    private String retourServeur;
    
    public TraitementClient(Socket connexionCourante) {
        this.connexionCourante = connexionCourante;
        this.fermeture = false;
    }
    
    private void reception(){
        InputStreamReader fluxEntree=null;
        
        try {
            fluxEntree = new InputStreamReader(connexionCourante.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BufferedReader lecture=new BufferedReader(fluxEntree);
        try {
            demandeClient=lecture.readLine();
            System.out.println("J'ai recu : " + demandeClient);
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void deconnexion(){
        try {
            connexionCourante.close();// ??? fermeture du socket client client
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void emission(){
        PrintStream fluxSortie=null;
        try {
            fluxSortie = new PrintStream(connexionCourante.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(fluxSortie!=null){
            fluxSortie.println(retourServeur);
        }
    }
    
    @Override
    public void run(){
        while(!fermeture){
            reception();
            if(demandeClient!=null){
                System.out.println("J'aime le jambon");
                TraitementDemande traitementDemande=new TraitementDemande();
                retourServeur=traitementDemande.requete(demandeClient);
                emission();
            }
            else fermeture=true;
        }
        deconnexion();
    }
}
