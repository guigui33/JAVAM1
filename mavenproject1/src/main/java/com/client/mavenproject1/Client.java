/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.client.mavenproject1;

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
public class Client {
    private Socket socket;
    private int identifiantClient; //identifiant de la base de donnée de l'utilisateur
    
    private boolean quitter;
    
    private String requete;
    private String reponseServeur;
    
    public Client(){
        this.socket=null;
        this.quitter=false;
    }
    
    private void connexion(int port){
        try {
            this.socket = new Socket("localhost", port);
        } catch (IOException ex) {
            System.out.print("erreur de connexion. Serveur non accessible.");
            quitter=true;
        }
    }
    
    private void deconnexion(){
          System.out.println("deconnexion...");
          System.out.println("fin client.");
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void emission(){
        
        PrintStream fluxSortie=null;
        try {
            fluxSortie = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(requete!=null){
            System.out.println("requete: "+requete);
            fluxSortie.println(requete);
        }
    }
    
    private void reception(){
        BufferedReader fluxEntreeSocket=null;
        
        try {
            fluxEntreeSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(fluxEntreeSocket!=null){
            try {
                reponseServeur= fluxEntreeSocket.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(reponseServeur!=null)
                System.out.println("Reponse du serveur : " + reponseServeur);
        }        
    }
    
    /**
     * afficher le menu des choix possibles au client
     */
    private void menu(){
        System.out.println("***menu***");
        System.out.println("1) Creation du compte\n"
                +"2)Connexion\n"
                +"3) Rechercher\n"
                +"4) Visiter\n"
                + "5) modifier coordonnées\n"
                + "6) modifier diplomes\n"
                + "7) modifier competences\n"
                +"0) quitter\n") ;
        System.out.print("choix: ");
    }
    
    
    /**
     * reponse client fomalisme de la requete à envoyer au serveur
     * @return la requète au serveur
     */
    //a refaire
    private void choix(){
                
        BufferedReader fluxEntreeStandard = new BufferedReader(
                new InputStreamReader(System.in));
        
        boolean continuer=true;
        String c=null;
        
        try {
            c = fluxEntreeStandard.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (continuer) {
            switch (c) {
                case "1":
                    requete = "INSCRIPTION";
                    continuer = false;
                    break;
                case "2":
                    requete = "CONNEXION";
                    continuer = false;
                    break;
                case "3":
                    requete = "RECHERCHER";
                    continuer = false;
                    break;
                case "4":
                    requete = "VISITER";
                    continuer = false;
                    break;
               case "5":
                    requete = "MODIFIER_COORDONNEES";
                    continuer = false;
                    break;
                   case "6":
                    requete = "MODIFIER_DIPLOMES";
                    continuer = false;
                    break;
                       case "7":
                    requete = "MODIFIER_COMPETENCES";
                    continuer = false;
                    break;
                case "0":
                    requete=null;
                    continuer = false;
                    quitter=true;
                    break;
                default:
                    System.out.println("mauvais choix.");
            }
        }
        
        //on ecrit le reste de la requete à envoyer au serveur, 
        //a faire ds une methode
        if(!quitter){
            String delimitateur="#";
            requete+=delimitateur;
            System.out.print("requête à completer: "+requete);
            try {
                requete = requete + fluxEntreeStandard.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
        
    public void fonctionnement(){
        connexion(50001);
        while(!quitter){
            menu();
            choix();
            if(!quitter){
                emission();
                reception();
            }
        }
        deconnexion();
        
    }
    
    public static void main(String[] args) {
        Client c=new Client();
        c.fonctionnement();
    }
    
}
