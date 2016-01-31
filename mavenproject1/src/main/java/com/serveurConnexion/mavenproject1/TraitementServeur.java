/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.serveurConnexion.mavenproject1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigui
 */
public class TraitementServeur extends Thread{
    private final Socket connexionCourante;
    
    private String demandeServeur;
    private String retourServeur;
    
    private final HashMap <Integer,Integer> clients;
    
    public TraitementServeur(Socket service,HashMap <Integer,Integer> clients){
        this.connexionCourante=service;
        this.clients=clients;
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
            demandeServeur=lecture.readLine();
            System.out.println("demande Seveur: "+demandeServeur);
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void deconnexion(){
        System.err.println("deconnexion Serveur : "+ connexionCourante);
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
            System.out.println("retour Serveur: "+retourServeur);
            fluxSortie.println(retourServeur);
        }
    }
    
    private String verificationConnexion(String[] demande){  
        Integer idClient=Integer.parseInt(demande[1]);
        
        Integer numSession=clients.get(idClient);
        
        if(numSession!=null && Integer.parseInt(demande[2])==numSession){
            return "OK";
        }
        return "ERROR";
      }
     
    private String deconnexionClient(String[] demande) {
        clients.remove(Integer.parseInt(demande[1]));
        return "OK";
    }
    
     public String requete(String demandeClient){
        /*decouper la requte en tableau */
        String [] decoupageRequete;
        decoupageRequete=demandeClient.split("#");
        //en fonction du mot cle on redirige vers le service correspondant
        switch(decoupageRequete[0]){
            case "VERIFCONNEXION":
                if(decoupageRequete.length!=3){
                    //erreur non gérée
                    return "ERROR";
                }
                return verificationConnexion(decoupageRequete);
            case "DECONNEXION":
                if(decoupageRequete.length!=2){
                    return "ERROR";
                }
                return deconnexionClient(decoupageRequete);
            default:
                return "ERROR"; 
        }
    }
    
    @Override
    public void run(){
        boolean fermeture=false;
        while(!fermeture){
            reception();
            if(demandeServeur!=null){
                retourServeur=requete(demandeServeur);
                emission();
            }
            else fermeture=true;
        }
        System.out.println("demande de deconnexion serveur.");
        deconnexion();
    }
}
