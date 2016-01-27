/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serveurConnexion.mavenproject1;

import java.util.HashMap;

/**
 *
 * @author guigui
 */
public class ServeurConnexion {
    private final int portClient=50001;
    private final int portServeur=50002;
    /*
    les clients connectés sont enregistrés dans cette variable
    identifiantBDD est la clé, le numero de session
    */
    private final HashMap <Integer,Integer> clients;
        
    public ServeurConnexion(){
        this.clients=new HashMap<>();
    }
    
    public void fonctionnementService(){
        AccueilClientCo accueilClientCo=new AccueilClientCo(portClient,clients);
        AccueilServeurCo accueilServeurCo=new AccueilServeurCo(portServeur,clients);
        accueilClientCo.start();
        accueilServeurCo.start();
    }
    
    public static void main(String[] args) {
        System.out.println("debut serveur Connexion");
        new ServeurConnexion().fonctionnementService();
    }
    
}
