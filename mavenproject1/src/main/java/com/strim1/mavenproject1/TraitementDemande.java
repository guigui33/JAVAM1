/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strim1.mavenproject1;

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
        decoupageRequete=demandeClient.split(" ");
        //en fonction du mot cle on redirige vers le service correspondant
        switch(decoupageRequete[0]){
            case "MODIFIER": 
                return modifier(decoupageRequete);//modifier
            case "INSCRIPTION": 
                return creationCompte(decoupageRequete);//modifier
            case "RECHERCHER": 
                return rechercher(decoupageRequete);//modifier
            default:
                return "requête inconnue, Usage: motclé id demande"; 
        }
    }   
    
    /**
     * 
     * @param demande 
     * @return  un message si la modification a été prise en compte ou non.
     *          si non, explication de l'erreur 
     */
    private String modifier(String[] demande){
        return "modifier";
    }
    
    /**
     * 
     * @param demande
     * @return un message si la creation a été effectuée 
     *         sinon, message d'erreur contenant la raison du refus.
     */
    private String creationCompte(String []demande){
       bdd.CreerUtilisateur(demande[3], demande[4], demande[1], demande[5], demande[2]);
        return "creation compte ok";
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
}
