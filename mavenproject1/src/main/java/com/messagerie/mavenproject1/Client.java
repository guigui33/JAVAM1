package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.Bdd;

/**
 *
 * @author guigui
 */
public class Client {
    private int port;
    private String  ip;
    private boolean disponible;
    
    public Client(String ip,int port,int id){
        this.port=port;
        this.ip=ip;
        this.disponible=initialiserDirect(id);
    }

    private boolean initialiserDirect(int id){
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
           return bdd.estDisponible(id);
        }
        else 
            return false;
    }
    
    public String getInformations(int id){
        String nom_prenom=null;
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            nom_prenom=bdd.getNomPrenom(id);
            return "#"+nom_prenom+"#"+ip+"#"+String.valueOf(port);
        }
        return "ERROR#Problème Serveur, veuillez recommencez plus tard.";
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public boolean isDisponible() {
        return disponible;
    }
    
    
}
