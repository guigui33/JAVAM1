package com.serveurinscription.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.ServicePostal;
import java.net.Socket;

/**
 *traitement de l'inscription d'un client 
 */
public class TraitementInscription extends Thread{
     /**
     * classe qui gere la reception, l'emission et le deconnexion
     * @see ServicePostal
     */
    private final ServicePostal servicePostal;
    /**
     * la demande du client vers le serveur gestion
     */
    private String demandeClient;
    /**
     * la reponse du serveur au client
     */
    private String reponseServeur;
    /**
     * initialise le socket de service du client et le service postal permettant de 
     * dialoguer avec le client
     * @param connexionCourante socket de service du serveur
     */
    public TraitementInscription(Socket connexionCourante){
        this.servicePostal=new ServicePostal(connexionCourante);
    }
    /**
     * decoupe la requète du client 
     * redirige le client en fonction du service demandé
     * @return 
     */
    private String requete(){
        String[] decoupageRequete;
        //decoupe la requète
        decoupageRequete=demandeClient.split("#");
        
        switch(decoupageRequete[0]){
            case "INSCRIPTION"://demande d'inscription
                if(decoupageRequete.length!=7){
                    return "ERROR#inscription, requète incomplète.";
                }
                return inscription(decoupageRequete);
            default:
                return "ERRROR#requête inconnue.";
        }
    }
    /**
     * methode permettant d'inscrire un client 
     * initialise une connexion ave la Bdd 
     * envoie une requète 
     * @param demande la demande du client decoupée
     * @return une message au client d'echec ou de réussite
     */
    private String inscription(String[] demande){
        Bdd bdd=new Bdd();
        //connexion avec la Bdd
        if(bdd.connexion()){
            //incription de l'utilisateur
            return bdd.creerUtilisateur(demande[3],demande[4],demande[1],demande[5],demande[2],bdd.parseVisibiliter(demande[6]));
        }
        else {
            return "ERROR#Problème serveur, veuillez recommencer plus tard.";
        }
    }
    /**
     * fontionnement du programme 
     * recoit et emet les messages
     */
    @Override
    public void run(){
        demandeClient=servicePostal.reception();//receptionne la demande du client
        if(demandeClient!=null){
            reponseServeur=requete();//traite la demande du client
            servicePostal.emission(reponseServeur);//emission de la reponse
        }
        System.out.println("demande de deconnexion client.");
        servicePostal.deconnexion();//ferme le socket
    }
}
