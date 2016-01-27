/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.serveurConnexion.mavenproject1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigui
 */
class AccueilServeurCo {
    private Socket service;
    private ServerSocket ecoute;
    
    public AccueilServeurCo(int portServeur) {
        try {
            ecoute=new ServerSocket(portServeur);
        } catch (IOException ex) {
            Logger.getLogger(AccueilServeurCo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fonctionnementService(){
        while(true){
            try {
                System.out.println("attente serveur...");
                service=ecoute.accept();
                System.err.println("Nouvelle connexion : "+ service);
            } catch (IOException ex) {
                System.err.println("probleme nouvelle connexion");
            }
            new TraitementClient(service).start();
        }
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
