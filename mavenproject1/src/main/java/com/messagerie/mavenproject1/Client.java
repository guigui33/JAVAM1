package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.Bdd;

/**
 *
 * @author guigui
 */
public class Client {
    private int port;
    private String  ip;
    private boolean direct;
    private int id;
    
    public Client(String ip,int port,int id){
        this.port=port;
        this.ip=ip;
        this.id=id;
        initialiserDirect();
    }

    private void initialiserDirect(){
        Bdd bdd=new Bdd();
        if(bdd.connexion()){
           direct=bdd.etreAppelDirectement(id);
        }
        else 
            direct=false;
    }
    public String getInformations(){
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

    public boolean isDirect() {
        return direct;
    }
    
    
}
