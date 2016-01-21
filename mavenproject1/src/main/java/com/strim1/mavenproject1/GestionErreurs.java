/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.strim1.mavenproject1;

/**
 *
 * @author guigui
 */
public class GestionErreurs {
    
    /*erreurs traitées dans la Bdd
    A ajouter si application doit faire des verifs 
    */
    public String traitementErreurs(String typeErreur){
        switch (typeErreur){
            /*case "Montant negatif":
            return ("Montant negatif, impossible d'effectuer l'operation.");
            case "Identifiant existe":
            return ("Identifiant existant, choisissez un autre identifiant.");
            case "Identifiant non valide":
            return ("Identifiant non valide, impossible d'effectuer l'operation.");
            case "Solde negatif":
            return ("Pas assez d'argent sur le compte");*/
            default:
                return "erreur inconnue.";
        }
    }
    public String traitementErreursRequete(String typeErreur){
        String msgErreur="Requête "+typeErreur+" mal formulée, ";
        switch (typeErreur){
            case "INSCRIPTION":
                msgErreur+="USAGE: INSCRIPTION#adresse_mail#mot_de_passenom#prenom#AAAA-MM-JJ";
                break;
            case "CONNEXION":
                msgErreur+="USAGE: CONNEXION#adresse_mail#mot_de_passe";
                break;
            case "RECHERCHER":
                msgErreur+="USAGE: RECHERCHER#categorie#mot_clé.";
                break;
            case "VISITER":
                msgErreur+="USAGE: VISITER#idutilisateurCourant#id_utilisateurAVisiter.";
                break;
            case "MODIFIER_COORDONNEES":
                msgErreur+="MODIFIER_COORDONNEES#id_utilisateur#mot_de_passe#adresseMail"+
                        "#telephone#visiilite_coordonnes" +
                        "#visibilité_diplome#visibilité_compétence";
                break;
            case "MODIFIER_DIPLOMES":
                msgErreur+="USAGE: MODIFIER_DIPLOMES#id_utilisateur#annee#type#etablissement";
                break;
            case "MODIFIER_COMPETENCES":
                msgErreur+="USAGE: MODIFIER_COMPETENCES#id_utilisateur#compétence#niveau";
                break;
            default:
                msgErreur="ERREUR message, Mot clé inconnu";
                break;
        }
        return msgErreur;
    }
}
