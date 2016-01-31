/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.serveurinscription.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.GestionErreurs;
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
public class TraitementInscription extends Thread{
    private final Socket connexionCourante;
    
    private String reponseServeur;
    private String demandeClient;
    
    public TraitementInscription(Socket connexionCourante){
        this.connexionCourante=connexionCourante;
    }
    
    private void reception(){
        InputStreamReader fluxEntree=null;
        try {
            fluxEntree = new InputStreamReader(connexionCourante.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(TraitementInscription.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BufferedReader lecture=new BufferedReader(fluxEntree);
        
        try {
            demandeClient=lecture.readLine();
        } catch (IOException ex) {
            Logger.getLogger(TraitementInscription.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("demande client: "+demandeClient);
    }
    
    private void deconnexion(){
        System.err.println("deconnexion client : "+ connexionCourante);
        
        try {
            connexionCourante.close();// ??? fermeture du socket client client
        } catch (IOException ex) {
            Logger.getLogger(TraitementInscription.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void emission(){
        PrintStream fluxSortie=null;
        
        try {
            fluxSortie = new PrintStream(connexionCourante.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(TraitementInscription.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fluxSortie!=null){
            System.out.println("retour Serveur: "+reponseServeur);
            fluxSortie.println(reponseServeur);
        }
    }
    
    private String requete(){
        String[] decoupageRequete;
        decoupageRequete=demandeClient.split("#");
        
        switch(decoupageRequete[0]){
            case "INSCRIPTION":
                if(decoupageRequete.length!=7){
                    return new GestionErreurs().traitementErreursRequete(decoupageRequete[0]);
                }
                return inscription(decoupageRequete);
            default:
                return "requête inconnue.";
        }
    }
    
    //a refaire apres bdd push
    private String inscription(String[] demande){
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            return bdd.creerUtilisateur(demande[3],demande[4],demande[1],demande[5],demande[2],demande[7]);
        }
        else {
            return "Problème serveur, veuillez recommencer plus tard.";
        }
    }
    
    public void run(){
        reception();
        if(demandeClient!=null){
            reponseServeur=requete();
            emission();
        }
        System.out.println("demande de deconnexion client.");
        deconnexion();
    }
}
