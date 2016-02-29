package com.messagerie.mavenproject1;

import com.strim1.mavenproject1.ServicePostal;
import com.strim1.mavenproject1.ServicePostalUDP;
import java.net.Socket;
import java.util.Hashtable;

/**
 *Classe qui traite des demandes des autres serveurs 
 */
public class TraitementServeur extends Thread {
    /**
     * la demande 
     */
    private String demandeServeur;
    /*
    * la reponse du serveur Messagerie
    */
    private String retourServeur;
    
    /**
     * classe qui gere la reception et l'emission en TCP
     * @see ServicePostal
     */
    private final ServicePostal servicePostal;
    
    /**
     * Le service qui gere les receptions et emissions en UDP, partagée par les threads
     */
    private final ServicePostalUDP servicePostalUDP;
    
    /**
     * La liste des clients déclarée au serveur Messagerie
     */
    private final Hashtable <Integer,Client> clients;
    
    /**
     * Le constructeur, creation du service postal avec le socket passé en paramètre
     * La liste des clients et le service postal UDP sont deja créés
     * @param service le socket
     * @param clients la liste des clients
     * @param servicePostalUDP le service postal UDP
     */
    public TraitementServeur(Socket service,Hashtable <Integer,Client> clients,ServicePostalUDP servicePostalUDP){
        this.servicePostal=new ServicePostal(service);
        this.servicePostalUDP=servicePostalUDP;
        this.clients=clients;
    }
    
    /**
     * Traite la demande du serveur
     * en fonction d'un mot clé, dirige vers le service concerné.
     * @return une chaine de caractère
     */
    public String requete() {
        /*decouper la requte en tableau */
        String[] decoupageRequete;
        decoupageRequete = demandeServeur.split("#");
        //en fonction du mot cle on redirige vers le service correspondant
        switch (decoupageRequete[0]) {
            case "DECONNEXION":
                if (decoupageRequete.length != 2) {
                    return "ERROR";
                }
                return deconnexionClient(decoupageRequete);
            default:
                return "ERROR";
        }
    }
    
    /**
     * traite d'un demande de déconnexion d'un client
     * il faut supprimer le client de la liste et avertir les autres utilisateurs que le client n'est 
     * plus disponible.
     * utilisation du service UDP
     * @param demande la demande
     * @return une chaine de caractère
     */
    private String deconnexionClient(String[] demande) {
        //recupère l'identifiant du client à supprimer
        Integer idClientSupp = Integer.parseInt(demande[1]);
        //reupère les informations sur le client dans la liste
        Client cSupp=clients.get(idClientSupp);
        //supprime le client
        clients.remove(idClientSupp);
        if(cSupp.isDisponible()){           
            //si le client était visible des autres utilisateurs
            //on avertit les autres utilisateurs 
            for (Integer idClient : clients.keySet())
            {
                //recuperation des informations des clients
                //si le client est visible, on lui envoit sur l'adresse ip et port enregistrés
                Client cAPrevenir=clients.get(idClient);
                if(cAPrevenir.isDisponible())
                    servicePostalUDP.envoyer("SUPPCLIENT#"+idClientSupp.toString(), cAPrevenir.getIp(), cAPrevenir.getPort());
            }
        }
        return "OK";
    }
    
    /**
     * la methoder run qui permet de recevoir une requète, de la traiter et de retourner une reponse.
     */
    @Override
    public void run() {
        boolean fermeture = false;
        while (!fermeture) {
            demandeServeur = servicePostal.reception();//receptionne la demande
            if (demandeServeur != null) {
                retourServeur = requete();//traite la demande
                servicePostal.emission(retourServeur);//emet la reponse
            } else {
                fermeture = true;
            }
        }
        System.out.println("demande de deconnexion serveur.");
        servicePostal.deconnexion();//ferme le socket client
    }
}
