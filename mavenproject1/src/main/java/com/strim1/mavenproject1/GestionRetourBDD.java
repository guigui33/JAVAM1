/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strim1.mavenproject1;

/**
 *
 * @author TomGui
 */
public class GestionRetourBDD {
    
    public String valeurRetour(String typeRetour){
		switch (typeRetour){
		case "Mail double":
			return ("Erreur, l'addresse mail est deja utilise:");
		case "Mdp court":
			return ("Le Mot de passe est trop court. Il doit etre supperieur a 6 caracteres.");
		case "Mdp long":
			return ("Le Mot de passe est trop long. Il doit etre inferieur a 16 caracteres.");
		case "Inscription ok":
			return ("Votre inscription est un succes ! Bienvenue!");
		case "Erreur BDD":
			return ("Erreur BDD, veuillez recommencer");
		case "Ajout competence ok":
			return ("La competence a bien ete ajoute.");
                case "Doublon competence":
			return ("La competence existe deja, veuillez soit la supprimer ou modifier votre niveau.");
                case "Diplome ok":
			return ("Le diplome a bien ete ajoute.");
                case "Doublon diplome":
			return ("Votre diplome est deja enregistre. Supprimer le si vous vous etes trompe.");
                case "Information modif ok":
			return ("La modification des informations a bien ete prise en compte.");
                case "Modif competence ok":
			return ("La modification de la competence a bien ete prise en compte");
                case "Suppression diplome":
			return ("Votre diplome a bien ete supprime.");
                case "Erreur suppression diplome":
			return ("Votre diplome n'existe pas..");
                case "Suppression competence":
			return ("Votre competence a bien ete supprime.");
                case "Erreur suppression competence":
			return ("Votre competence n'existe pas..");
                case "Connexion client ok":
			return ("Vous etes maintenant connecte.");        
                case "Erreur connexion":
			return ("Adresse mail ou mot de passe incorrecte.");    
                default:
			return "erreur inconnue.";
		}
	}   
}
