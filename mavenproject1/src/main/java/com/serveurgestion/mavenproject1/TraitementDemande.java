package com.serveurgestion.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.ServicePostal;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
class TraitementDemande {
    private final Bdd bdd;
    public TraitementDemande(){
        this.bdd=new Bdd();
    }
    
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
        if(bdd.connexion()){
            //recherche (int idcourant,String nom, String prenom, String diplome, String matiere, niveau n)
            return bdd.recherche(Integer.parseInt(decoupageRequete[1]), decoupageRequete[2], decoupageRequete[3], decoupageRequete[4], decoupageRequete[5], bdd.parseNiveau(decoupageRequete[6]));
        }
        return "ERROR";
    }
    
    private String visiter(String[] decoupageRequete) {
        //visiterProfil(int idcourant, int idvisite)
        if(bdd.connexion()){ 
            return bdd.visiterProfil(Integer.parseInt(decoupageRequete[1]), Integer.parseInt(decoupageRequete[2]));
        }
        return "ERROR";
    }
    
    private String modifierUtilisateur(String[] decoupageRequete) {
        //modifierInformation(int id, String addrmail,String tel,String mdp,visibiliter vi)
      
        if(bdd.connexion()){ 
            return bdd.modifierInformation(Integer.parseInt(decoupageRequete[1]),decoupageRequete[4],decoupageRequete[3],decoupageRequete[2],bdd.parseVisibiliter(decoupageRequete[5]));
        }
        return "ERROR";
    }
    
    private String ajouterDiplome(String[] decoupageRequete) {
        //ajouterDiplome(int id, String dateobtention,String diplome ,String etabli,visibiliter v)
        if(bdd.connexion()){ 
            return bdd.ajouterDiplome(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2],decoupageRequete[3],decoupageRequete[4],bdd.parseVisibiliter(decoupageRequete[5]));
        }
        return "ERROR";
    }
    
    private String ajouterComp(String[] decoupageRequete) {
        //ajouterCompetence(int id, String matiere, niveau n, visibiliter v)
         if(bdd.connexion()){ 
            return bdd.ajouterCompetence(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2],bdd.parseNiveau(decoupageRequete[3]),bdd.parseVisibiliter(decoupageRequete[4]));
        }
        return "ERROR"; //To change body of generated methods, choose Tools | Templates.
    }
    
    private String suppCompetence(String[] decoupageRequete) {
        //supprimerCompetence(int id,String matiere)
        if(bdd.connexion()){ 
            return bdd.supprimerCompetence(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2]);
        }
        return "ERROR";
    }
    
    private String suppDiplome(String[] decoupageRequete) {
        //supprimerDiplome(int id,String diplome)
        if(bdd.connexion()){ 
            return bdd.supprimerDiplome(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2]);
        }
        return "ERROR";
    }
    
    private String modifVisiComp(String[] decoupageRequete) {
        if(bdd.connexion()){ 
            //return bdd.modifVisiComp(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2]);
        }
        return "ERROR";
    }
    
    private String modifVisiDip(String[] decoupageRequete) {
        if(bdd.connexion()){ 
            //return bdd.modifVisiDip(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2]);
        }
        return "ERROR";
    }
    
    private String deconnexion(String[] decoupageRequete) {
            Socket serveurConnexion;
        try {
            serveurConnexion = new Socket("localhost",50002);
             ServicePostal servicePostalCo = new ServicePostal(serveurConnexion);
            
            String requete="DECONNEXION#"+decoupageRequete[1];
            System.out.println("requete au serveur de connexion: "+requete);
            servicePostalCo.emission(requete);
            
            String reponse= servicePostalCo.reception();
            System.out.println("Reponse du serveur connexion: " + reponse);            
            return null;
        } catch (IOException ex) {
            Logger.getLogger(TraitementDemande.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}