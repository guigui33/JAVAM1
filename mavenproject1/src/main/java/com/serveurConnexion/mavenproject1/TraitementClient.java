/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.serveurConnexion.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.GestionErreurs;
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
class TraitementClient extends Thread{
    private final Socket connexionCourante;
    
    private String demandeClient;
    private String retourServeur;
    
    HashMap <Integer,Integer> clients;
    
    public TraitementClient(Socket service,HashMap <Integer,Integer> clients) {
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
            demandeClient=lecture.readLine();
            System.out.println("demande client: "+demandeClient);
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void deconnexion(){
        System.err.println("deconnexion client : "+ connexionCourante);
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
    
    //a refaire pour numSession
    private String connexion(String[] demande){
        String retour=null;
        Bdd bdd=new Bdd();
        Integer idclient;
        
        //if(bdd.connexion()){
        bdd.connexion();
        //a mettre id client
        retour=bdd.connexionClient(demande[1],demande[2]/*,idClient*/); //verification du mot de passe et login de l'utilisateur
        if(retour=="OK"){
            //a refaire
            Integer numSession=4;
            idclient=5;
            clients.put(idclient,numSession);
            return "OK#"+idclient.toString()+"#"+numSession.toString();
        }
        else{
            return "ERROR#mauvais login ou mot de passe";
        }
        //}
        //else return "Problème serveur, veuillez recommencer plus tard.";
    }
    
    public String requete(){
        /*decouper la requte en tableau */
        String [] decoupageRequete;
        decoupageRequete=demandeClient.split("#");
        //en fonction du mot cle on redirige vers le service correspondant
        switch(decoupageRequete[0]){
            case "CONNEXION":
                if(decoupageRequete.length!=3){
                    return new GestionErreurs().traitementErreursRequete(decoupageRequete[0]);
                }
                return connexion(decoupageRequete);
            default:
                return "requête inconnue, Usage: motclé#id#demande";
        }
    }
    
    @Override
    public void run(){
        boolean fermeture=false;
        while(!fermeture){
            reception();
            if(demandeClient!=null){
                retourServeur=requete();
                emission();
            }
            else fermeture=true;
        }
        System.out.println("demande de deconnexion client.");
        deconnexion();
    }
}
