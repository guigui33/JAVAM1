package com.serveurgestion.mavenproject1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */

class TraitementDemande {
    
    public TraitementDemande(){}
    
    public String requete(String demandeClient){
        /*decouper la requte en tableau */
        String [] decoupageRequete;
        decoupageRequete=demandeClient.split("#");
        //en fonction du mot cle on redirige vers le service correspondant
        /* switch(decoupageRequete[0]){
        case "HELLO":
        
        default:
        return "requête inconnue, Usage: motclé#id#demande";
        }*/
        return null;
    }
        
    public boolean verificationConnexion(String[] demande){
        if(Integer.parseInt(demande[1])==0){
            return true;
        }
        try {
            Socket serveurConnexion=new Socket("localhost",50002);
            PrintStream fluxSortie=null;
            try {
                fluxSortie = new PrintStream(serveurConnexion.getOutputStream());
            } catch (IOException ex) {
                return false;
            }
            String requete="VERIFCONNEXION#"+demande[1]+"#"+demande[2];
            System.out.println("requete au serveur de connexion: "+requete);
            fluxSortie.println(requete);
            
            BufferedReader fluxEntreeSocket =new BufferedReader(new InputStreamReader(serveurConnexion.getInputStream()));
            String reponse=null;
            reponse= fluxEntreeSocket.readLine();
            System.out.println("Reponse du serveur connexion: " + reponse);
            if(reponse.equals("OK")){
                return true;
            }
            return false;
            
        } catch (IOException ex) {
            return false;
        }
    }
}