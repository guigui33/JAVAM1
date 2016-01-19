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
    public String traitementErreurs(String typeErreur){
		switch (typeErreur){
		case "Montant negatif":
			return ("Montant negatif, impossible d'effectuer l'operation.");
		case "Identifiant existe":
			return ("Identifiant existant, choisissez un autre identifiant.");
		case "Identifiant non valide":
			return ("Identifiant non valide, impossible d'effectuer l'operation.");
		case "Solde negatif":
			return ("Pas assez d'argent sur le compte");
		default:
			return "erreur inconnue.";
		}
	}
	public String traitementErreursRequete(String typeErreur){
		String msgErreur="Requête "+typeErreur+" mal formulée, ";		
		switch (typeErreur){
		case "CREATION":
			msgErreur+="USAGE: CREATION ID SOMME.";
			break;
		case "POSITION":
			msgErreur+="USAGE: POSITION ID.";
			break;
		case "AJOUT":
			msgErreur+="USAGE: AJOUT ID SOMME.";
			break;
		case "RETRAIT":
			msgErreur+="USAGE: RETRAIT ID SOMME.";
			break;		
		default:
			msgErreur="ERREUR message, Mot clé inconnu";
			break;
		}
		return msgErreur;
	}
}
