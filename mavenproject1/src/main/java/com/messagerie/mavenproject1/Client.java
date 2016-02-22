package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.Bdd;

/**
 *
 * @author guigui
 */
public class Client {
    private int id;
    private int port;
    private String  ip;
    
    public Client(int id,String ip,int port){
        this.id=id;
        this.port=port;
        this.ip=ip;
    }

    public int getId() {
        return id;
    }
        
    public String getInformations(){
        String nom_prenom=null;
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
            //nom_prenom=bdd.getNomPrenom(id);
            return String.valueOf(id)+nom_prenom+ip+String.valueOf(port);
        }
        return "ERROR#Problème Serveur, veuillez recommencez plus tard.";
    }
}
