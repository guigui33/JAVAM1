/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.client.mavenproject1;

import com.strim1.mavenproject1.ServicePostal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigui
 */
public class Client {
    private Socket socket;
    private int id_user = 0;
    private int id_Visite = 0;
    private int num_session = 0;
    private String nom;
    private String prenom;
    private String adresse_mail;
    private String mdp;
    private String telephone;
    private String DateNaiss;
    String listeRecherche[] = new String[100];
    String listeDiplome[] = new String[100];
    String listeCompetence[] = new String[100];
    private String Visibilite;
    
    private boolean Connecte = false;
    
    private boolean quitter;
    
    public Client(){
        this.socket=null;
        this.quitter=false;
        this.Connecte = false;
        initialiserTableau();
    }
    
    private void initialiserTableau(){
        initialiserRecherche();
        initialiserDip();
        initialiserComp();
        
    }
    
    public void initialiserRecherche(){
        for (int i = 0; i < 100; i++) {
            listeRecherche[i] = "";
        }
    }
    
    public void initialiserDip(){
       for (int i = 0; i < 100; i++) {
            listeDiplome[i] = "";
        }
    }
    
    public void initialiserComp(){
        for (int i = 0; i < 100; i++) {
            listeCompetence[i] = "";
        }
    }
    public void connexion(int port){
        try {
            this.socket = new Socket("192.168.1.2", port);
        } catch (IOException ex) {
            System.out.print("erreur de connexion. Serveur non accessible.");
            quitter=true;
        }
    }
    
    
    private void emission(String req){
        new ServicePostal(socket).emission(req);
    }
    
    private String reception(){
        return new ServicePostal(socket).reception();
    }
    
    public void deconnexion(){
          System.out.println("deconnexion...");
          System.out.println("fin client.");
          Connecte = false;
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String envoyerRequete(String req) {
        String retourReq;
        emission(req);
        retourReq = reception();
        return retourReq;

    }
    
    
    public void envoyerHello(int id, int num){
        connexion(50003);
        emission("HELLO#" + id + "#" + num);
        reception();

    }
    
    
    public String envoyerRechercheAcceuil(){
        String retourReq;
        if(!Connecte){
            connexion(50003);
            emission("HELLO#0#0");
            reception();
        }

        emission("RECHERCHER#0#NULL#NULL#NULL#NULL#NULL");
        retourReq = reception();
        if(!Connecte){
            deconnexion();
            id_user = 0;
            id_Visite = 0;
       }
        return retourReq;
    }
    
    
    
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse_mail() {
        return adresse_mail;
    }

    public String getMdp() {
        return mdp;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDateNaiss() {
        return DateNaiss;
    }

    public boolean isConnecte() {
        return Connecte;
    }

    public int getId_user() {
        return id_user;
    }

    public int getNum_session() {
        return num_session;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setNum_session(int num_session) {
        this.num_session = num_session;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse_mail(String adresse_mail) {
        this.adresse_mail = adresse_mail;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setDateNaiss(String DateNaiss) {
        this.DateNaiss = DateNaiss;
    }

    public void setConnecte(boolean Connecte) {
        this.Connecte = Connecte;
    }

    public String getVisibilite() {
        return Visibilite;
    }

    public void setVisibilite(String Visibilite) {
        this.Visibilite = Visibilite;
    }

    public int getId_Visite() {
        return id_Visite;
    }

    public void setId_Visite(int id_Visite) {
        this.id_Visite = id_Visite;
    }

    
    

}
