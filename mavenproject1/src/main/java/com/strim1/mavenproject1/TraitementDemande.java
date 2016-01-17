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
         
    public TraitementDemande() {
              
    }
    
    public String requete(String demandeClient){
        /*decouper la requte en tableau */
        String [] decoupageRequete;
        decoupageRequete=demandeClient.split(" ");
        //en fonction du mot cle on redirige vers le service correspondant
        switch(decoupageRequete[0]){
            case "modifier": 
                return modifier(decoupageRequete[1]);//modifier
            case "creationCompte": 
                return creationCompte(decoupageRequete[1]);//modifier
            case "information": 
                return information(decoupageRequete[1]);//modifier
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
    private String modifier(String demande){
        return null;
    }
    
    /**
     * 
     * @param demande
     * @return un message si la creation a été effectuée 
     *         sinon, message d'erreur contenant la raison du refus.
     */
    private String creationCompte(String demande){
        return null;
    }
   
    /**
     * 
     * @param demande
     * @return un message contenant les informations demandées par le client 
     *          peut être un message d'erreur, informations non trouvées ou pas les droits d'accès.
     */
    private String information(String demande){
        
        return null;
    }
}
