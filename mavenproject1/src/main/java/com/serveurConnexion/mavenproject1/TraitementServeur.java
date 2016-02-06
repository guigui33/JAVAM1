package com.serveurConnexion.mavenproject1;

import com.strim1.mavenproject1.ServicePostal;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *cette classe permet de gerer les demandes des autres serveurs
 *
 */
public class TraitementServeur extends Thread {
  
    /**
     * la demande du client
     */
    private String demandeServeur;
    /*
    * la reponse du serveur
    */
    private String retourServeur;
    /**
     * classe qui gere la reception et l'emission
     * @see ServicePostal
     */
    private final ServicePostal servicePostal;
    
    /**
     * la liste des clients connectés
     * @see Clients
     */
    private final Clients clients;
    
/**
     * constructeur qui fixe le socket de service, la liste des clients connectés (vide au départ)
     * et creation du service postal.
     * @param service le socket de service serveur et du client
     * @param clients la liste des clients connectés
     */
    public TraitementServeur(Socket service, Clients clients) {
        this.clients = clients;
        this.servicePostal = new ServicePostal(service);
    }
/**
 * methode permettant de verifier si un client est connecté 
 * @param demande la demande du serveur contenant l'identifiant du client et son numero de session
 * @return une chaine de caractère: ok si le client est connecté , error sinon
 */
    private String verificationConnexion(String[] demande) {
        Integer idClient = Integer.parseInt(demande[1]);

        Integer numSession = clients.getNumSession(idClient);

        if (Integer.parseInt(demande[2]) == numSession) {
            return "OK";
        }
        return "ERROR";
    }

    private String deconnexionClient(String[] demande) {
        Integer idClient = Integer.parseInt(demande[1]);
        if (clients.verification(idClient)) {
            try {
                clients.supprimerClient(idClient);
            } catch (InterruptedException ex) {
                Logger.getLogger(TraitementServeur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "OK";
    }

    public String requete(String demandeClient) {
        /*decouper la requte en tableau */
        String[] decoupageRequete;
        decoupageRequete = demandeClient.split("#");
        //en fonction du mot cle on redirige vers le service correspondant
        switch (decoupageRequete[0]) {
            case "VERIFCONNEXION":
                if (decoupageRequete.length != 3) {
                    return "ERROR";
                }
                return verificationConnexion(decoupageRequete);
            case "DECONNEXION":
                if (decoupageRequete.length != 2) {
                    return "ERROR";
                }
                return deconnexionClient(decoupageRequete);
            default:
                return "ERROR";
        }
    }

    @Override
    public void run() {
        boolean fermeture = false;
        while (!fermeture) {
            demandeServeur = servicePostal.reception();
            if (demandeServeur != null) {
                retourServeur = requete(demandeServeur);
                servicePostal.emission(retourServeur);
            } else {
                fermeture = true;
            }
        }
        System.out.println("demande de deconnexion serveur.");
        servicePostal.deconnexion();
    }
}
