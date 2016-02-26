package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.Bdd;

/**
 *Information sur un client délaré au Serveur Messagerie
 * Les variales: 
 *      -le port UDP permettant de le contacter et son ip.
 *      -si le client est disponible pour être contacter directement
 *
 */
public class Client {
    /**
     * le port du client pour le contacter en UDP
     */
    private int port;
    
    /**
     * L'ip du client pour le contacter en UDP
     */
    private String  ip;
    
    /**
     * si le client veut etre contacter directement
     */
    private boolean disponible;
    
    /**
     * le constructeur de Client definit l'ip, le port et si il est disponible
     * L'identifiant permet de retouver les informations le concernant
     * @param ip ip du client
     * @param port le port du client
     * @param id l'identifiant Bdd du client
     */
    public Client(String ip,int port,int id){
        this.port=port;
        this.ip=ip;
        this.disponible=initialiserDirect(id);
    }

    /**
     * cherche si le client veut etre contacter directement
     * @param id l'identifiant Bdd du client
     * @return true si le client veut être contacter, false sinon
     *          retourne false par défaut en cas d'erreur
     */
    private boolean initialiserDirect(int id){
        Bdd bdd=new Bdd();//creation de Bdd
        //si la connection est effectuée
        if(bdd.connexion()){
           return bdd.estDisponible(id); //retourne le contenu de la Bdd
        }
        else 
            return false; //retourne flase par défaut
    }
    
    /**
     * obtenir les informations sur le Client:
     * son nom, son prenom, son ip et son port
     * @param id l'identifiant Bdd du client
     * @return une chaine contenant les informations du client
     *          #nom#prenom#ip#port
     */
    public String getInformations(int id){
        String nom_prenom=null;
        Bdd bdd=new Bdd();
        //si la connexion est acceptée
        if(bdd.connexion()){
            nom_prenom=bdd.getNomPrenom(id);//cherche le nom et le prenom de l'utilisateur 
            return "#"+nom_prenom+"#"+ip+"#"+String.valueOf(port);//retourne les informations de l'utilisateur
        }
        //en cas de probleme
        return "ERROR#Problème Serveur, veuillez recommencez plus tard.";
    }

    /**
     * retourne le port de l'utilisateur
     * @return le port de l'utilisateur
     */
    public int getPort() {
        return port;
    }
    
    /**
     * retourne l'ip de l'utilisateur
     * @return l'ip de l'utilisateur
     */
    public String getIp() {
        return ip;
    }

    /**
     * si l'utilisateur veut etre disponible pour etre contacter directement
     * @return disponible
     */
    public boolean isDisponible() {
        return disponible;
    }   
}
