
package com.serveurConnexion.mavenproject1;

import java.util.HashMap;

/**
 *La classe client permet de gérer la liste des clients connectés.
 * Cette classe est partagée par plusieurs thread, elle inclue la synchronisation.
 * La lecture n'est pas synchronisée, on pose un verrour lors d'un ajout ou d'une suppression de client.
 *
 */
public class Clients {
    /**
     * la liste des clients connectés. 
     * La clé est l'identifiant de la Bdd du client
     * La valeur est un numero de session
     */
    private final HashMap<Integer, Integer> clients;
    
    /**
     * nombre de requete qui demande à verifier une connexion d'un client.
     */
    private int requeteVerifConnexion = 0;
    
    /**
     * le contructeur permet de créer la liste des clients connectés
     * vide au départ
     */
    public Clients() {
        this.clients = new HashMap<Integer, Integer>();
    }
    
    /**
     * cette methode permet de synchroniser la variable requeteVerifConnexion.
     * lorsque une demande de lecture de la variable Clients est demadée
     * requeteVerifConnexion est incrementée.
     */
    public synchronized void debutVerification() {
        requeteVerifConnexion++;
        notifyAll();
    }
    
    /**
     * cette methode permet de synchroniser la variable requeteVerifConnexion.
     * lorsque clients a été lue et la verification de connexion d'un client a été traitée
     * requeteVerifConnexion est décrementée.
     */
    public synchronized void finVerification() {
        requeteVerifConnexion--;
        notifyAll();
    }
    
    /**
     * methode pour synchroniser la variable clients lors de la suppression d'un client
     * @param idClient l'identifiant d'un client à supprimer
     * @throws InterruptedException 
     */
    public synchronized void supprimerClient(int idClient) throws InterruptedException {
        // si il y a des demandes de verification cette methode attend
        while (requeteVerifConnexion > 0) {
            wait();
        }
        //supprimer de la liste l'identifiant du client
        clients.remove(idClient);
        notifyAll();
    }
    
    /**
     * methode pour synchroniser la variable clients lors de l'ajout d'un client
     * @param idClient l'identifiant d'un client à ajouter
     * @param numSession le numero de session associé au client et à sa connexion
     * @throws InterruptedException 
     */
    public synchronized void ajouterClient(int idClient, int numSession) throws InterruptedException {
        //temps qu'il y a un demande de verification, cette methode attend
        while (requeteVerifConnexion > 0) {
            wait();
        }
        //ajout dans la liste
        clients.put(idClient, numSession);
        notifyAll();
    }
    
    /**
     * methode pour obtenir un numero de session à partir de l'identifiant d'un client
     * @param idClient l'identifiant d'un client à rechercher
     * @return retourne le numero de session associé au client 
     */
    public int getNumSession(int idClient){
        int numS;
        debutVerification();
        numS=clients.get(idClient);
        finVerification();
        return numS;
    }
    /**
     * verification si un client est présent dans la liste à partir de son identifiant
     * @param idClient l'identifiant du client à rechercher
     * @return un boolean, true si il est dans la liste, false sinon
     */
    public boolean verification(int idClient){
        boolean b;
        debutVerification();
        b=clients.containsKey(idClient);
        finVerification();
        return b;
    }
    
    /**
     * verication si un client est dans la liste à partir de son identifiant et de son numero de session
     * @param idClient l'identifiant du client à rechercher
     * @param numSession le nuemro de session du client donné par le client
     * @return un boolean, true si le client et le nuemro de session correspondent, false sinon
     */
    public boolean verification(int idClient, int numSession) {
        boolean b;
        debutVerification();
        if (clients.containsKey(idClient)) {
            b=(numSession == clients.get(idClient));
        } else {
            b=false;
        }
        finVerification();
        return b;
    }
}
