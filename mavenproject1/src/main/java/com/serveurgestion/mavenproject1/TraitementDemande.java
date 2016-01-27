/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serveurgestion.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.GestionErreurs;

/**
 *
 * 
 */
class TraitementDemande {
     
    private final Bdd bdd;
    
    public TraitementDemande(Bdd bdd) {
        this.bdd=bdd;
    }
    
    public String requete(String demandeClient){
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
            case "INSCRIPTION": 
                if(decoupageRequete.length!=6){
                    return new GestionErreurs().traitementErreursRequete(decoupageRequete[0]);
                }
                return creationCompte(decoupageRequete);//modifier
            case "RECHERCHER": 
                if(decoupageRequete.length!=3){
                    return new GestionErreurs().traitementErreursRequete(decoupageRequete[0]);
                }
                return rechercher(decoupageRequete);//modifier
            case "VISITER":
                if(decoupageRequete.length!=3){
                    return new GestionErreurs().traitementErreursRequete(decoupageRequete[0]);
                }
                return visiter(decoupageRequete);
            case "MODIFIER_COORDONNEES":
                if(decoupageRequete.length!=8){
                    return new GestionErreurs().traitementErreursRequete(decoupageRequete[0]);
                }
                return modifierCoordonnees(decoupageRequete);
            case "MODIFIER_DIPLOMES":
                if(decoupageRequete.length!=5){
                    return new GestionErreurs().traitementErreursRequete(decoupageRequete[0]);
                }
                return modifierDiplomes(decoupageRequete);
            case "MODIFIER_COMPTETENCES":
                if(decoupageRequete.length!=4){
                    return new GestionErreurs().traitementErreursRequete(decoupageRequete[0]);
                }
                return modifierCompetences(decoupageRequete);
            default:
                return "requête inconnue, Usage: motclé#id#demande"; 
        }
    }   
    
    /**
     * 
     * @param demande 
     * @return  un message si la modification a été prise en compte ou non.
     *          si non, explication de l'erreur 
     */
    private String connexion(String[] demande){
        
        return bdd.connexionClient(demande[1],demande[2]); //verification du mot de passe et login de l'utilisateur
    }
    
    /**
     * 
     * @param demande
     * @return un message si la creation a été effectuée 
     *         sinon, message d'erreur contenant la raison du refus.
     */
    private String creationCompte(String []demande){
        return bdd.creerUtilisateur(demande[3], demande[4], demande[1], demande[5], demande[2]);
    }
   
    /**
     * 
     * @param demande
     * @return un message contenant les informations demandées par le client 
     *          peut être un message d'erreur, informations non trouvées ou pas les droits d'accès.
     */
    private String rechercher(String[] demande){    
        
        return "RECHERCHER";
    }

    private String visiter(String[] decoupageRequete) {
        return bdd.visiterProfil(Integer.parseInt(decoupageRequete[1]),Integer.parseInt(decoupageRequete[2]));
    }

    private String modifierCoordonnees(String[] decoupageRequete) {
        /*return bdd.modifierInformation(Integer.parseInt(decoupageRequete[1]),
                decoupageRequete[3],
                decoupageRequete[4],
                decoupageRequete[2]/*,
                bdd.parseVisibiliter(decoupageRequete[5]),
                bdd.parseVisibiliter(decoupageRequete[6]),
                bdd.parseVisibiliter(decoupageRequete[7]));*/
        return null;
    }

    private String modifierDiplomes(String[] decoupageRequete) {
        return null;
    }

    private String modifierCompetences(String[] decoupageRequete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
