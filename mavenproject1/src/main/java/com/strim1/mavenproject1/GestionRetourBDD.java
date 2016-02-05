/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strim1.mavenproject1;

/**
 *
 * @author TomGui
 * 
 * Description: Class gerant les retours des fonctinos de la classe Bdd.
 * Envoei des retours sous la forme : OK# ou ERROR#
 */
public class GestionRetourBDD {
    
    public String valeurRetour(String typeRetour){
		switch (typeRetour){
		case "Modif diplome ok":
			return ("OK#La modification du diplome a bien ete prise en compte");
		case "Inscription ok":
			return ("OK#Votre inscription est un succes ! Bienvenue!");
		case "Ajout competence ok":
			return ("OK#La competence a bien ete ajoute.");
                case "Diplome ok":
			return ("OK#Le diplome a bien ete ajoute.");
                case "Information modif ok":
			return ("OK#La modification des informations a bien ete prise en compte.");
                case "Modif competence ok":
			return ("OK#La modification de la competence a bien ete prise en compte");
                case "Suppression diplome":
			return ("OK#Votre diplome a bien ete supprime.");
                case "Suppression competence":
			return ("OK#Votre competence a bien ete supprime.");
                case "Connexion client ok":
			return ("OK#Vous etes maintenant connecte.");   
		case "Doublon diplome":
			return ("ERROR#Votre diplome est deja enregistre. Supprimer le si vous vous etes trompe.");
		case "Erreur BDD":
			return ("ERROR#Erreur BDD");
		case "Erreur creation":
			return ("ERROR#CreationUtilisateur");	
		case "Erreur suppression diplome":
			return ("ERROR#Votre diplome n'existe pas..");
		case "Erreur suppression competence":
			return ("ERROR#Votre competence n'existe pas..");
                case "Mail double":
			return ("ERROR#Erreur, l'addresse mail est deja utilise:");
		case "Mdp court":
			return ("ERROR#Le Mot de passe est trop court. Il doit etre supperieur a 6 caracteres.");
		case "Mdp long":
			return ("ERROR#Le Mot de passe est trop long. Il doit etre inferieur a 16 caracteres.");
                case "Erreur connexion":
			return ("ERROR#Adresse mail ou mot de passe incorrecte.");
                case "Doublon competence":
			return ("ERROR#La competence existe deja, veuillez soit la supprimer ou modifier votre niveau.");
                case "Recherche Erreur":
			return ("ERROR#Recherche."); 
                case "Erreur Visite":
                        return ("ERROR#Visite");
                default:
			return "ERROR#Erreur inconnue.";
		}
	}   
}
