/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serveurConnexion.mavenproject1;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author guigui
 */
public class Clients {
    private final HashMap <Integer,Integer> clients;
    private int requeteVerifConnexion=0;
    
    public Clients(){
        this.clients=new HashMap<Integer,Integer>();
    }
    
    public synchronized void debutVerification(){
        requeteVerifConnexion++;
        notifyAll();
    }
    
    public synchronized void finVerification(){
        requeteVerifConnexion--;
        notifyAll();
    }
    
    public synchronized void supprimerClient(int idClient) throws InterruptedException{
        while(requeteVerifConnexion>0) wait();
        clients.remove(idClient);
        notifyAll();
    }
    
    public synchronized void ajouterClient(int idClient,int numSession) throws InterruptedException{
        while(requeteVerifConnexion>0) wait();
        clients.put(idClient, numSession);
        notifyAll();
    }
    
    public boolean verification(int idClient,int numSession){        
        if(clients.containsKey(idClient)){
          return numSession==clients.get(idClient);
        }
        else {
            return false;
        }
    }
    
    public void run(){
       Clients c=new Clients();
       
    }
}
