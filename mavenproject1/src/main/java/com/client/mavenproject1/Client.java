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
    private int id_user = 8;
    private int num_session = 0;
    private String nom;
    private String prenom;
    private String adresse_mail;
    private String mdp;
    private String telephone;
    private String DateNaiss;
    ArrayList<String> listeDiplome = new ArrayList<>();
    ArrayList<String> listeCompetence = new ArrayList<>();
    
    private boolean Connecte;
    
    private boolean quitter;
    
    public Client(){
        this.socket=null;
        this.quitter=false;
         this.nom = "BADENS";
        this.prenom = "Florian";
        this.adresse_mail = "jean.marc@gmail.com";
        this.mdp = "tasoeur";
        this.telephone = "06010203040";
        this.DateNaiss = "25/12/1800";
        this.Connecte = false;
        listeDiplome.add("");
        listeDiplome.add("BAC Pro Java");
        listeDiplome.add("Master STRI");
        listeCompetence.add("");
        listeCompetence.add("Java");
        listeCompetence.add("Routage Dynamique");
    }
    
    private void connexion(int port){
        try {
            this.socket = new Socket("localhost", port);
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
    
    private void deconnexion(){
          System.out.println("deconnexion...");
          System.out.println("fin client.");
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public String envoyerRequete(int Port, String req) {
        String retourReq;
        connexion(Port);
        emission(req);
        retourReq = reception();
        deconnexion();
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

    

}
