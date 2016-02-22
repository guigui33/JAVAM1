package com.serveurgestion.mavenproject1;

import com.strim1.mavenproject1.ServicePostal;
import java.net.Socket;


/**
 * Classe permettant le dialogue entre le serveur et le client
 * definit le socket de service, les demandes et les retours serveur et clients
 */
public class TraitementClient extends Thread {
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
     * le reponse du serveur au client
     */
    private String retourServeur;
    
    /**
     * identifiant de l'utilisateur
     */
    private int id;
    
    /**
     * le constructeur créé le service postal qui va gérer le dialogue entre le serveur et le client
     * à partir d'un socket de service
     * @param connexionCourante le socket de service
     */
    public TraitementClient(Socket connexionCourante) {
        this.servicePostal=new ServicePostal(connexionCourante);
    }
    
    @Override
    public void run(){
        boolean fermeture=false;
        //classe qui va traiter la demande du client
        TraitementDemande traitementDemande=new TraitementDemande();
        demandeClient=servicePostal.reception();//receptionne la première requète du client
         
        //verification si la première requete est correcte
        id=traitementDemande.verificationConnexion(demandeClient);
        if(id<0){
            //si la première requète est incorrecte on ferme la connexion
            retourServeur="ERROR";
            fermeture=true;
        }
        else {
            retourServeur="OK";//si non on informe le client que la connexion est verifiée 
        }
        servicePostal.emission(retourServeur);
        while(!fermeture){
            demandeClient=servicePostal.reception();//on attend la demande du client
            if(demandeClient!=null){
                retourServeur=traitementDemande.requete(demandeClient);//on traite sa demande
                if(retourServeur!=null)
                    servicePostal.emission(retourServeur);//on retourne la reponse du serveur
                else fermeture=true;
            }
            else fermeture=true;
        }
        System.out.println("demande de deconnexion client.");
        if(id>0){
            traitementDemande.requete("DECONNEXION#"+Integer.toString(id));
        }
        servicePostal.deconnexion();//on ferme le socket d'ecoute
    }
}
