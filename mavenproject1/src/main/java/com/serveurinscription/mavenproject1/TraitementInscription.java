/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serveurinscription.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import java.net.Socket;

/**
 *
 * @author guigui
 */
public class TraitementInscription extends Thread{
    private final Bdd bdd;
    private final Socket connexionCourante;
    
    private String reponseServeur;
    private String demandeClient;
    
    public TraitementInscription(Socket connexionCourante){
        this.bdd=new Bdd();
        this.connexionCourante=connexionCourante;
    }
    
    
      private void reception(){
        InputStreamReader fluxEntree=null;
        
        
            fluxEntree = new InputStreamReader(connexionCourante.getInputStream());
        
        
        BufferedReader lecture=new BufferedReader(fluxEntree);
        
            demandeClient=lecture.readLine();
            System.out.println("demande client: "+demandeClient);
       
    }
    
    private void deconnexion(){
        System.err.println("deconnexion client : "+ connexionCourante);
      
            connexionCourante.close();// ??? fermeture du socket client client
        
    }
    
    private void emission(){
        PrintStream fluxSortie=null;
       
            fluxSortie = new PrintStream(connexionCourante.getOutputStream());
       
        if(fluxSortie!=null){
            System.out.println("retour Serveur: "+retourServeur);
            fluxSortie.println(retourServeur);
        }
    }
    
    public void run(){
    
    }
}
