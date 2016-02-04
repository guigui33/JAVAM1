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
			return ("ERROR#Erreur, l'addresse mail est deja utilise:");
		case "Mdp court":
			return ("ERROR#Le Mot de passe est trop court. Il doit etre supperieur a 6 caracteres.");
		case "Mdp long":
			return ("ERROR#Le Mot de passe est trop long. Il doit etre inferieur a 16 caracteres.");
		case "Inscription ok":
			return ("OK#Votre inscription est un succes ! Bienvenue!");
		case "Erreur BDD":
			return ("ERROR#Erreur BDD");
		case "Ajout competence ok":
			return ("OK#La competence a bien ete ajoute.");
                case "Doublon competence":
			return ("ERROR#La competence existe deja, veuillez soit la supprimer ou modifier votre niveau.");
                case "Diplome ok":
			return ("OK#Le diplome a bien ete ajoute.");
                case "Doublon diplome":
			return ("ERROR#Votre diplome est deja enregistre. Supprimer le si vous vous etes trompe.");
                case "Information modif ok":
			return ("OK#La modification des informations a bien ete prise en compte.");
                case "Modif competence ok":
			return ("OK#La modification de la competence a bien ete prise en compte");
                case "Suppression diplome":
			return ("OK#Votre diplome a bien ete supprime.");
                case "Erreur suppression diplome":
			return ("ERROR#Votre diplome n'existe pas..");
                case "Suppression competence":
			return ("OK#Votre competence a bien ete supprime.");
                case "Erreur suppression competence":
			return ("ERROR#Votre competence n'existe pas..");
                case "Connexion client ok":
			return ("OK#Vous etes maintenant connecte.");        
                case "Erreur connexion":
			return ("ERROR#Adresse mail ou mot de passe incorrecte.");
                case "Recherche Erreur":
			return ("ERROR#Recherche."); 
                case "Erreur Visite":
                        return ("ERROR#Visite");
                default:
			return "ERROR#Erreur inconnue.";
		}
	}   
}
