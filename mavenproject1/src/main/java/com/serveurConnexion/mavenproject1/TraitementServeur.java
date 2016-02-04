package com.serveurConnexion.mavenproject1;

import com.strim1.mavenproject1.ServicePostal;
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
    private final ServicePostal servicePostal;
    private final HashMap <Integer,Integer> clients;
    
    public TraitementServeur(Socket service,HashMap <Integer,Integer> clients){
        this.connexionCourante=service;
        this.clients=clients;
        this.servicePostal=new ServicePostal(service);
    }
    
    private void deconnexion(){
        System.err.println("deconnexion Serveur : "+ connexionCourante);
        try {
            connexionCourante.close();// ??? fermeture du socket client client
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
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
        Integer idClient=Integer.parseInt(demande[1]);
        if(clients.containsKey(idClient))
            clients.remove(idClient);
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
            demandeServeur=servicePostal.reception();
            if(demandeServeur!=null){
                retourServeur=requete(demandeServeur);
                servicePostal.emission(retourServeur);
            }
            else fermeture=true;
        }
        System.out.println("demande de deconnexion serveur.");
        deconnexion();
    }
}