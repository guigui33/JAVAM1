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
    
    public Client(){
         this.socket=null;  
    }
    
    private void connexion(int port){
         try {
            this.socket = new Socket("localhost", port);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Deconnexion(){
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void emission(String reponse){
              
        PrintStream fluxSortie=null;
        try {
            fluxSortie = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(reponse!=null)
            fluxSortie.println(reponse);
    }
    
    private void reception(){
        BufferedReader fluxEntreeSocket=null;
        String retour=null;
        
        try {
            fluxEntreeSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }            
        if(fluxEntreeSocket!=null){
        try {
            retour = fluxEntreeSocket.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(retour!=null)
         System.out.println("Reponse du serveur : " + retour);
        }      
       
    }
    
    /**
     * afficher le menu des choix possibles au client
     */
    private void menu(){
        System.out.println("***menu***");
        System.out.println("1) Creation du compte"
                + "2) modifier informations"
                +"0) quitter") ;
    }
    
    
    /**
     * reponse client fomalisme de la requete à envoyer au serveur
     * @return la requète au serveur
     */
    //a refaire
    private String choix(){
        BufferedReader fluxEntreeStandard = new BufferedReader(
				new InputStreamReader(System.in));
        
        boolean continuer=true;
        String reponse=null;
        boolean quitter=false;
        
        try {
            reponse = fluxEntreeStandard.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
		while (continuer) {
			switch (reponse) {
			case "1":
				reponse = "CREATION ";
				continuer = false;
				break;
			case "2":
				reponse = "Modifier ";
				continuer = false;
				break;
			case "3":
				reponse = "RECHERCHER ";
				continuer = false;
				break;
			case "4":
				reponse = "DECO ";
				continuer = false;
				break;
			case "0":
				System.out.println("bye.");
				continuer = false;
				quitter=true;
				break;
			default:
				System.out.println("mauvais choix.");
			}
                }
                
               if(!quitter){
			System.out.print("requête: "+reponse +" ");
            try {
                reponse = reponse + fluxEntreeStandard.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
			return reponse;
		}
               return null;
    }
    
}
