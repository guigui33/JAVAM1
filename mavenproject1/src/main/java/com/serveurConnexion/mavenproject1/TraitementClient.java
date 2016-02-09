
package com.serveurConnexion.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.ServicePostal;
import java.net.Socket;


/**
 *cette classe permet de gerer les demandes des clients
 *
 */
class TraitementClient extends Thread{

    /**
     * la demande du client
     */
    private String demandeClient;
    /*
    * la reponse du serveur
    */
    private String retourServeur;
    /**
     * classe qui gere la reception et l'emission
     * @see ServicePostal
     */
    private final ServicePostal servicePostal;
    
    /**
     * la liste des clients connectés
     * @see Clients
     */
    private final Clients clients;
    
    /**
     * constructeur qui fixe le socket de service, la liste des clients connectés (vide au départ)
     * et creation du service postal.
     * @param service le socket de service serveur et du client
     * @param clients la liste des clients connectés
     */
    public TraitementClient(Socket service,Clients clients) {
        this.clients=clients;
        this.servicePostal=new ServicePostal(service);
    }
     
    /**
     * Creation d'un numero de session associé à la connexion d'un client
     * Généré aléatoirement
     * @return un numero de session
     */
    private Integer generateurNumSession(){
        Integer numSession;
        //fixe les bornes
        int haut,bas;
        haut=99999;
        bas=1;
        //generation du numero de session
        numSession=(int)(Math.random()*(haut-bas)+bas);
        
        return numSession;
    }
    /**
     * traitement de la demande d'un client à se connecter
     * @param demande la demande d'un client sous forme de tableau
     * @return un message au client
     */
    private String connexion(String[] demande){
        String retour; //message à retourner au client
        Bdd bdd=new Bdd(); //creation de l'objet Bdd
        //se connecte à la bdd
        if(bdd.connexion()){
            retour=bdd.connexionClient(demande[1],demande[2]); //verification du mot de passe et login de l'utilisateur
            String []decoupe=retour.split("#");
            if(!decoupe[0].equals("ERROR")){
                //si le mot de passe et le login  correspondent
                Integer idClient=Integer.parseInt(decoupe[1]);
                Integer numSession=generateurNumSession();//création d'un nuemero de session
                
                if(clients.verification(idClient)) //verification si l'identifiant est present dans la liste
                {
                    try {
                        clients.supprimerClient(idClient);//on supprime si c'est la cas
                    } catch (InterruptedException ex) {
                        return "ERROR#erreur Serveur :( !"; // retourne une erreur si probleme
                    }
                }
                
                try {
                    clients.ajouterClient(idClient,numSession);// on ajoute l'identifiant et le numero de session à la liste
                } catch (InterruptedException ex) {
                    return "ERROR#erreur Serveur :( !";// retourne une erreur si probleme
                }
                retour+="#"+numSession;//concatene les informations à retourner au clients
                return retour;
            }
            else{
                return "ERROR#mauvais login ou mot de passe";
            }
        }
        else return "ERROR#Problème serveur, veuillez recommencer plus tard.";
    }
    
    /**
     * identification de la requète envoyé par le client
     * @return le message à retourner au client
     */
    public String requete(){
        /*decouper la requte en tableau */
        String [] decoupageRequete;
        decoupageRequete=demandeClient.split("#");
        //en fonction du mot cle on redirige vers le service correspondant
        switch(decoupageRequete[0]){
            case "CONNEXION":
                if(decoupageRequete.length!=3){
                    return "ERROR#connexion impossible.";
                }
                return connexion(decoupageRequete);
            default:
                return "ERROR#requête inconnue, Usage: motclé#id#demande";
        }
    }
    
    @Override
    public void run(){
        boolean fermeture=false; //boolean permettant de fermer le thread
        while(!fermeture){
            demandeClient=servicePostal.reception(); //attend la demande du client
            if(demandeClient!=null){
                retourServeur=requete(); // traite la requète du serveur
                servicePostal.emission(retourServeur);//Envoie le message au client
            }
            else fermeture=true;// si la demande du client == null on ferme le programme
        }
        System.out.println("demande de deconnexion client.");
       servicePostal.deconnexion();//on ferme le socket
    }
}
