
package com.serveurConnexion.mavenproject1;

/**
 *cette classse permet de lancer le serveur de connexion
 * on definit les ports d'ecoute du serveur, separation des traitments des autres serveurs et des clients
 * lance les threads AccueilClientCo et AccueilServeurCo.
 */
public class ServeurConnexion {
    /**
     * le port d'ecoute du serveur pour les clients
     */
    private final int portClient;
    
    /**
     * le port d'ecoute du serveur pour les autres serveurs
     */
    private final int portServeur;
    
    /*
    les clients connectés sont enregistrés dans cette variable
    identifiant BDD est la clé, le numero de session est la variable
    */
    private final Clients clients;
    
    /**
     * le contructeur permet d'initaliser les ports d'ecoute du serveur de connexion
     * la liste des clients est crée vide
     * @param portClient le numero de port d'ecoute du serveur pour les clients
     * @param portServeur le nuemro de port d'ecoute du serveur pour les autres serveurs
     */
    public ServeurConnexion(int portClient,int portServeur){
        this.portClient=portClient;
        this.portServeur=portServeur;
        this.clients=new Clients();
    }
    
    /**
     * création des deux threads qui traiteront les serveurs et les clients
     */
    public void fonctionnementService(){
        AccueilClientCo accueilClientCo=new AccueilClientCo(portClient,clients);
        AccueilServeurCo accueilServeurCo=new AccueilServeurCo(portServeur,clients);
        accueilClientCo.start();
        accueilServeurCo.start();
    }
    
    /**
     * main principale du programme du serveur de connexion
     * deifnition des deux ports d'ecoute du serveur
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("debut serveur Connexion");
        new ServeurConnexion(50001,50002).fonctionnementService();
    }
    
}
