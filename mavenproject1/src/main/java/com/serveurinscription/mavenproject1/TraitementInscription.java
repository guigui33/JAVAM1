/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.serveurinscription.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.GestionErreurs;
import com.strim1.mavenproject1.ServicePostal;
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
    
    private ServicePostal servicePostal;
    
    public TraitementInscription(Socket connexionCourante){
        this.connexionCourante=connexionCourante;
        this.servicePostal=new ServicePostal(connexionCourante);
    }
    
    private void deconnexion(){
        System.err.println("deconnexion client : "+ connexionCourante);
        
        try {
            connexionCourante.close();// ??? fermeture du socket client client
        } catch (IOException ex) {
            Logger.getLogger(TraitementInscription.class.getName()).log(Level.SEVERE, null, ex);
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
            return bdd.creerUtilisateur(demande[3],demande[4],demande[1],demande[5],demande[2],bdd.parseVisibiliter(demande[7]));
        }
        else {
            return "ERROR#Problème serveur, veuillez recommencer plus tard.";
        }
    }
    
    public void run(){
        demandeClient=servicePostal.reception();
        if(demandeClient!=null){
            reponseServeur=requete();
            servicePostal.emission(reponseServeur);
        }
        System.out.println("demande de deconnexion client.");
        deconnexion();
    }
}
