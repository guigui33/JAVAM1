package com.serveurgestion.mavenproject1;

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
 *
 */

class TraitementDemande {
    
    public TraitementDemande(){}
    
    public String requete(String demandeClient){
        /*decouper la requte en tableau */
        String [] decoupageRequete;
        decoupageRequete=demandeClient.split("#");
        //en fonction du mot cle on redirige vers le service correspondant
        switch(decoupageRequete[0]){
            case "RECHERCHER":
                return rechercher(decoupageRequete);
            case "VISITER":
                return visiter(decoupageRequete);
            case "MODIFIERUTILISATEUR":
                return modifierUtilisateur(decoupageRequete);
            case "AJOUTERDIPLOME":
                return ajouterDiplome(decoupageRequete);
            case "AJOUTERCOMP":
                return ajouterComp(decoupageRequete);
            case "SUPPCOMPETENCE":
                return suppCompetence(decoupageRequete);
            case "SUPPDIPLOME":
                return suppDiplome(decoupageRequete);
            case "MODIFVISICOMP":
                return modifVisiComp(decoupageRequete);
            case "MODIFVISIDIP":
                return modifVisiDip(decoupageRequete);
            case "DECONNEXION":
                return deconnexion(decoupageRequete);
            default:
                return "requête inconnue, Usage: motclé#id#demande";
        }
    }
    
    public boolean verificationConnexion(String demandeClient){
        String []demande=demandeClient.split("#");
        if(!demande[0].equals("HELLO")) return false;
        if(Integer.parseInt(demande[1])==0){
            return true;
        }
        try {
            Socket serveurConnexion=new Socket("localhost",50002);
            ServicePostal servicePostalCo = new ServicePostal(serveurConnexion);
            
            String requete="VERIFCONNEXION#"+demande[1]+"#"+demande[2];
            System.out.println("requete au serveur de connexion: "+requete);
            servicePostalCo.emission(requete);
            
            String reponse= servicePostalCo.reception();
            System.out.println("Reponse du serveur connexion: " + reponse);
            
            return reponse.equals("OK");//si la reponse est ok ou error
            
        } catch (IOException ex) {
            return false;
        }
    }
    
    private String rechercher(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String visiter(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String modifierUtilisateur(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String ajouterDiplome(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String ajouterComp(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String suppCompetence(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String suppDiplome(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String modifVisiComp(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String modifVisiDip(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String deconnexion(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}