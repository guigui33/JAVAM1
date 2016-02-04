/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.serveurgestion.mavenproject1;

import com.strim1.mavenproject1.ServicePostal;
import java.io.IOException;
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
    private final ServicePostal servicePostal;
    private String demandeClient;
    private String retourServeur;
    
    public TraitementClient(Socket connexionCourante) {
        this.connexionCourante = connexionCourante;
        this.fermeture = false;
        this.servicePostal=new ServicePostal(connexionCourante);
    }
    
    private void deconnexion(){
        System.err.println("deconnexion client : "+ connexionCourante);
        try {       
            connexionCourante.close();            
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @Override
    public void run(){
        TraitementDemande traitementDemande=new TraitementDemande();
        demandeClient=servicePostal.reception();
        if(!traitementDemande.verificationConnexion(demandeClient)){
            retourServeur="ERROR";
            fermeture=true;            
        } 
        else {
            retourServeur="OK";
        }
        servicePostal.emission(retourServeur);
        while(!fermeture){
            demandeClient=servicePostal.reception();
            if(demandeClient!=null){                
                retourServeur=traitementDemande.requete(demandeClient);
                if(retourServeur!=null)
                    servicePostal.emission(retourServeur);
                else fermeture=true;
            }
            else fermeture=true;
        }
        System.out.println("demande de deconnexion client.");
        deconnexion();
    }
}
