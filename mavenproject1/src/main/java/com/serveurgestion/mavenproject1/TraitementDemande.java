package com.serveurgestion.mavenproject1;

import com.strim1.mavenproject1.Bdd;
import com.strim1.mavenproject1.ServicePostal;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *traite les demandes clients
 *permet le dialogue entre la Bdd et le serveur de Gestion
 */
class TraitementDemande {
    /**
     * classe qui traite fait le lien avec la Bdd 
     */
    private final Bdd bdd;
    /**
     * le constructeur créé la classe Bdd 
     */
    public TraitementDemande(){
        this.bdd=new Bdd();
    }
    /**
     * methode permettant de diriger le client vers le bon service
     * decoupe la requète et cherche le mot clé 
     * redirige la demande en fonction du mot clé
     * @param demandeClient la demande du client
     * @return une reponse au client sous forme d'un String 
     */
    public String requete(String demandeClient){
        /*decouper la requete en tableau */
        String [] decoupageRequete;
        decoupageRequete=demandeClient.split("#");
        //en fonction du mot cle on redirige vers le service correspondant
        switch(decoupageRequete[0]){
            case "RECHERCHER"://si demande est rechercher un utilisateur
                return rechercher(decoupageRequete);
            case "VISITER"://visiter un profil 
                return visiter(decoupageRequete);
            case "MODIFIERUTILISATEUR"://modifier les informations d'un utilisateur
                return modifierUtilisateur(decoupageRequete);
            case "AJOUTERDIPLOME"://ajouter un diplme 
                return ajouterDiplome(decoupageRequete);
            case "AJOUTERCOMP"://ajouter une competence 
                return ajouterComp(decoupageRequete);
            case "SUPPCOMPETENCE"://supprimer une competence
                return suppCompetence(decoupageRequete);
            case "SUPPDIPLOME"://supprimer un diplome
                return suppDiplome(decoupageRequete);
            case "MODIFCOMP"://modifer une competence
                return modifComp(decoupageRequete);
            case "MODIFDIP"://modifier un diplome 
                return modifDip(decoupageRequete);
            case "DECONNEXION"://deconnexion du client
                return deconnexion(decoupageRequete);
            default:
                return "ERROR#requête inconnue.";
        }
    }
    /**
     * verification sur la première requète pour établir un connexion avec le serveur
     * est un requète HELLO
     * verifie ensuite si le client s'est connecté au serveur de connexion au préalable 
     * si le client en anonyme pas de verification 
     * sinon demande au serveur de connexion si il le client a le droit d'acceder au serveur Gestion
     * @param demandeClient la demande du client 
     * @return retourne l'identifiant du client qui s'est connecté au serveur Connexion ou si le client est anonyme,
     *            -1 si une erreur est rencontrée
     */
    public int verificationConnexion(String demandeClient){
        String []demande=demandeClient.split("#");//decoupe la demande du client 
        if(!demande[0].equals("HELLO")) return -1; // si la demande n'est pas  une requète HELLO
        if(Integer.parseInt(demande[1])==0){
            return 0;//si s'est un client anonyme 
        }
        try {
            //ouverture d'un session avec le serveur Connexion
            Socket serveurConnexion=new Socket("localhost",50002);
            ServicePostal servicePostalCo = new ServicePostal(serveurConnexion);
            /*creation de la requète pour le serveur Connexion
            *VERIFCONNEXION#idClient#numeroSesssion
            */
            String requete="VERIFCONNEXION#"+demande[1]+"#"+demande[2];
            System.out.println("requete au serveur de connexion: "+requete);
            //emet vers le serveur de connexion la requète 
            servicePostalCo.emission(requete);
            //attend la reponse du serveur 
            String reponse= servicePostalCo.reception();
            System.out.println("Reponse du serveur connexion: " + reponse);
            servicePostalCo.deconnexion();//ferme le socket 
            
            if(reponse.equals("OK"))//si la reponse est ok
            {
                return Integer.parseInt(demande[1]);
            }
            else {
                return -1;//si l'utilisateur n'est pas référencé 
            }
        } catch (IOException ex) {
            return -1;//si une erreur arrive on retourne par défaut false
        }
    }
    /**
     * recherche des utilisateurs dans la base de donnée
     * @param decoupageRequete la requète decoupée 
     * @return un message vers le client
     */
    private String rechercher(String[] decoupageRequete) {
        //se connecte avec la bdd
        if(bdd.connexion()){     
            //envoie les informations pour rechercher 
            return bdd.recherche(Integer.parseInt(decoupageRequete[1]), decoupageRequete[2], decoupageRequete[3], decoupageRequete[4],decoupageRequete[5],bdd.parseNiveau(decoupageRequete[6]));
        }
        return "ERROR";
    }
    
   /**
     * visiter un profil utilisateur
     * @param decoupageRequete la requète decoupée 
     * @return un message vers le client
     */
    private String visiter(String[] decoupageRequete) {
         //se connecte avec la bdd
        if(bdd.connexion()){ 
            //visiterProfil(int idcourant, int idvisite)
            return bdd.visiterProfil(Integer.parseInt(decoupageRequete[1]), Integer.parseInt(decoupageRequete[2]));
        }
        return "ERROR";
    }
    /**
     * modifier les informations concernant un utilisateur
     * @param decoupageRequete la requète decoupée
     * @return un message vers le client
     */
    private String modifierUtilisateur(String[] decoupageRequete) {
        //se connecte avec la bdd
        if(bdd.connexion()){ 
            return bdd.modifierInformation(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2],decoupageRequete[3],decoupageRequete[4],bdd.parseVisibiliter(decoupageRequete[5]));
        }
        return "ERROR";
    }
    /**
     * ajoute un diplome à l'utilisateur 
      * @param decoupageRequete la requète decoupée
     * @return un message vers le client
     */
    private String ajouterDiplome(String[] decoupageRequete) {
        //ajouterDiplome(int id, String dateobtention,String diplome ,String etabli,visibiliter v)
        if(bdd.connexion()){ 
            return bdd.ajouterDiplome(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2],decoupageRequete[3],decoupageRequete[4],bdd.parseVisibiliter(decoupageRequete[5]));
        }
        return "ERROR";
    }
    /**
     * ajoute une competence à un utilisateur
      * @param decoupageRequete la requète decoupée
     * @return un message vers le client 
     */
    private String ajouterComp(String[] decoupageRequete) {
        //ajouterCompetence(int id, String matiere, niveau n, visibiliter v)
         if(bdd.connexion()){ 
            return bdd.ajouterCompetence(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2],bdd.parseNiveau(decoupageRequete[3]),bdd.parseVisibiliter(decoupageRequete[4]));
        }
        return "ERROR"; 
    }
    /**
     * supprime une competence d'un utilisateur
     * @param decoupageRequete la requète decoupée
     * @return un message vers le client
     */
    private String suppCompetence(String[] decoupageRequete) {
        //supprimerCompetence(int id,String matiere)
        if(bdd.connexion()){ 
            return bdd.supprimerCompetence(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2]);
        }
        return "ERROR";
    }
    /**
     * supprime un diplome d'un utilisateur
    * @param decoupageRequete la requète decoupée
     * @return un message vers le client
     */
    private String suppDiplome(String[] decoupageRequete) {
        //supprimerDiplome(int id,String diplome)
        if(bdd.connexion()){ 
            return bdd.supprimerDiplome(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2]);
        }
        return "ERROR";
    }
    /**
     * modifier la competence d'un utilisateur
     * @param decoupageRequete la requète decoupée
     * @return un message vers le client
     */
    private String modifComp(String[] decoupageRequete) {
        if(bdd.connexion()){ 
           return bdd.modifierCompetence(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2],bdd.parseNiveau(decoupageRequete[3]),bdd.parseVisibiliter(decoupageRequete[4]));
        }
        return "ERROR";
    }
    /**
     * modifier un diplome d'un utilisateur
    * @param decoupageRequete la requète decoupée
     * @return un message vers le client
     */
    private String modifDip(String[] decoupageRequete) {
        if(bdd.connexion()){             
            return bdd.modifierDiplome(Integer.parseInt(decoupageRequete[1]),decoupageRequete[2],bdd.parseVisibiliter(decoupageRequete[3]));
        }
        return "ERROR";
    }
    /**
     * deconnexion d'un client
     * suppression de son identifiant de la liste des connectés du serveur connexion
     * @param decoupageRequete la requète decoupée
     * @return null pour fermer la connexion avec le client
     */
    private String deconnexion(String[] decoupageRequete) {
            Socket serveurConnexion;
        try {
            //connexion avec le serveur Connexion
            serveurConnexion = new Socket("localhost",50002); 
             ServicePostal servicePostalCo = new ServicePostal(serveurConnexion);
            //formulation de la requète de deconnexion
            //DECONNEXION#idClient
            String requete="DECONNEXION#"+decoupageRequete[1];
            System.out.println("requete au serveur de connexion: "+requete);
            servicePostalCo.emission(requete);//emission de la requete 
            
            String reponse= servicePostalCo.reception();//recoit la reponse du serveur
            System.out.println("Reponse du serveur connexion: " + reponse);            
            return null;
        } catch (IOException ex) {
            Logger.getLogger(TraitementDemande.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}