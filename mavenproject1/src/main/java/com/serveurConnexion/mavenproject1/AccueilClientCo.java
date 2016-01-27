/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serveurConnexion.mavenproject1;

/**
 *
 * @author guigui
 */
public class AccueilClientCo {
    
   private int port;
   
    AccueilClientCo(int portClient) {
        this.port=portClient;
    }
    
      public void run(){
        while(!fermeture){
            reception();
            if(demandeClient!=null){
                TraitementDemande traitementDemande=new TraitementDemande(bdd);
                retourServeur=traitementDemande.requete(demandeClient);
                emission();
            }
            else fermeture=true;
        }
        System.out.println("demande de deconnexion client.");
        deconnexion();
    }
}
