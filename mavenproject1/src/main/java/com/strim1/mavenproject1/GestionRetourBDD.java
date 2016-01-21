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
		case "Connexion BDD ok":
			return ("Connexion BDD effective !");
		case "Erreur Connexion":
			return ("Erreur de connexion à la BDD.");
		case "Mail double":
			return ("Erreur, l'addresse mail est deja utilisé:");
		case "Mdp court":
			return ("Le Mot de passe est trop court. Il doit etre supperieur à 6 caracteres.");
		case "Mdp long":
			return ("Le Mot de passe est trop long. Il doit etre inferieur à 16 caracteres.");
		case "Inscription ok":
			return ("Votre inscription est un succés ! Bienvenu!");
		case "Erreur BDD":
			return ("Erreur BDD, veuillez recommencer");
		case "Ajout competence ok":
			return ("La competence à bien été ajouté.");
                case "Doublon competence":
			return ("La competence existe deja, veuillez soit la supprimer ou modifier votre niveau.");
                case "Diplome ok":
			return ("Le diplome à bien été ajouté.");
                case "Doublon diplome":
			return ("Votre diplome est deja enregistré. Supprimer le si vous vous etes trompé.");
                case "Information modif ok":
			return ("La modification des informations à bien été prise en compte.");
                case "Modif competence ok":
			return ("La modification de la compétence à bien été prise en compte");
                case "Suppression diplome":
			return ("Votre diplome à bien été supprimé.");
                case "Erreur suppression diplome":
			return ("Votre diplome n'existe pas..");
                case "Suppression competence":
			return ("Votre competence à bien été supprimé.");
                case "Erreur suppression competence":
			return ("Votre competence n'existe pas..");
                case "Connexion client ok":
			return ("Vous etes maintenant connecté.");        
                case "Erreur connexion":
			return ("Adresse mail ou mot de passe incorrecte.");    
                default:
			return "erreur inconnue.";
		}
	}   
}
