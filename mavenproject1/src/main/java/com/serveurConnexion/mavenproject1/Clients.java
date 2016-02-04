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
public class Clients {

    private final HashMap<Integer, Integer> clients;
    private int requeteVerifConnexion = 0;

    public Clients() {
        this.clients = new HashMap<Integer, Integer>();
    }

    public synchronized void debutVerification() {
        requeteVerifConnexion++;
        notifyAll();
    }

    public synchronized void finVerification() {
        requeteVerifConnexion--;
        notifyAll();
    }

    public synchronized void supprimerClient(int idClient) throws InterruptedException {
        while (requeteVerifConnexion > 0) {
            wait();
        }
        clients.remove(idClient);
        notifyAll();
    }

    public synchronized void ajouterClient(int idClient, int numSession) throws InterruptedException {
        while (requeteVerifConnexion > 0) {
            wait();
        }
        clients.put(idClient, numSession);
        notifyAll();
    }
    
    public int getNumSession(int idClient){
        int numS;
        debutVerification();
        numS=clients.get(idClient);
        finVerification();
        return numS;
    }
    
    public boolean verification(int idClient){
        boolean b;
        debutVerification();
        b=clients.containsKey(idClient);
        finVerification();
        return b;
    }
    
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
