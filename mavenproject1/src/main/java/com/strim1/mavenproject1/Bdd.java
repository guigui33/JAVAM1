
package com.strim1.mavenproject1;

import java.sql.*;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 * Classe gÃ©rant la BDD
 *
 * @author STRI_JAVA
 */

public class Bdd {

    public Statement st;
    /* Les enum */

    enum visibiliter {

        Prive,
        Public,
        UtilisateurCo;
    };

    enum niveau {

        Moyen,
        Bon,
        Tresbon,
        NULL;
    }
    /**
     * Le nom pour la BDD
     */
    private final String host;
    /**
     * Le mot de passe
     */
    private final String pwd;
    /**
     * L'url du serveur
     */
    private final String url;
    private Connection co;

    /**
     * constructeur par défaut de la classe SQL
     */
    public Bdd() {
        this.host = "javaguigui";
        this.pwd = "azerty";
        this.url = "jdbc:mysql://kriissss.fr:3306/javaguigui?zeroDateTimeBehavior=convertToNull";
    }

    /**
     * Methode Concernant les connexions
     * connexion() -> Connexion du serveur a la BDD 
     * connexionClient(String mail, String mdp) -> Connexion d'un utilisateur avec verification Addrmail , mdp. 
     * On retourne l'id utilisateur ainsi que son prénom.
     */
    public boolean connexion() {
        try {
            // Pas besoins sous JDK 1.8 Class.forName("com.mysql.jdbc");
            //System.out.println("Driver O.K.");
            co = DriverManager.getConnection(url, host, pwd);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public String connexionClient(String mail, String mdp) {
        try {
            ResultSet resultat1;
            int idUtil;
            String prenom;

            st = co.createStatement();
            resultat1 = st.executeQuery("SELECT Id, Prenom FROM Utilisateurs WHERE AddrMail='" + mail + "' AND Mdp='" + mdp + "'");
            if (resultat1.next()) {
                idUtil = resultat1.getInt("Id");
                prenom = resultat1.getString("Prenom");
                return "OK" + "#" + idUtil + "#" + prenom;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR";
        }
        return "ERROR#Informations incorrecte";
    }
    //Verification des entrées:
     
     /* Méthode permettand de récuperer le type du visiteur afin de voir les droits qu'il a */
    public String typeVisiteur(int idcourant, int idprofil) {

        if (idcourant != 0 && idcourant != 1) {
            if (idcourant == idprofil) {
                return "MonProfil";
            }
            return "Utilisateur";
        } else if (idcourant == 1) {
            return "Admin";
        }
        return "Visiteur";

    }

    /*Visibiliter de l'utilisateur en fonction de l'enum  */
    
    public visibiliter parseVisibiliter(String v) {

        switch (v) {
            case "Public":
                return visibiliter.Public;
            case "UtilisateurCo":
                return visibiliter.UtilisateurCo;
            default:
                return visibiliter.Prive;
        }
    }

    /*Niveau de la compétence en fonction de l'enum */
    public niveau parseNiveau(String n) {

        switch (n) {
            case "Bon":
                return niveau.Bon;
            case "Tres bon":
                return niveau.Tresbon;
            case "Moyen":
                return niveau.Moyen;
            default:
                return niveau.NULL;
        }
    }
    
    /*Vérifie si la requete retourne 1 ou plusieurs lignes (surement inutiles a vérifier)*/
    public boolean verifierRequete(String requete) {
        ResultSet verif = null;
        try {
            st = co.createStatement();
            verif = st.executeQuery(requete);
            if (verif != null) {
                int nbLignes = 0;
                while (verif.next()) {
                    nbLignes++;
                }
                if (nbLignes == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
        }
        return false;
    }
    
    public int verifierMdp(String mdp) {
        int count = mdp.codePointCount(0, mdp.length());
        if (count < 6) {
            return 1;
        } else if (count > 16) {
            return 2;
        } else {
            return 3;
        }
    }
    /*L'adresse mail etant une clé primaire, on verifie si elle n'est pas deja présente dans la Base*/

    public boolean verifierMail(String mail) {
        ResultSet verif;
        try {
            st = co.createStatement();
            verif = st.executeQuery("SELECT AddrMail FROM Utilisateurs WHERE AddrMail='" + mail + "'");
                while (verif.next()) {
                    return false;
                }
                return true;
        } catch (SQLException e) {
            return true;
        }
    }


    public String visibiliterInfo(int id) {
        try {
            ResultSet ri;
            String visi;
            st = co.createStatement();
            ri = st.executeQuery("SELECT VisibleInf FROM Utilisateurs WHERE Id=" + id + "");
            ri.next();
            visi = ri.getString(1);
            switch (visi) {
                case "Public":
                    return "Public";
                case "UtilisateurCo":
                    return "UtilisateurCo";
                default:
                    return "Prive";
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new GestionRetourBDD().valeurRetour("Erreur BDD");
    }

    /*Ajouts-modofications d'information à la BDD.*/
    
    public String creerUtilisateur(String nom, String prenom, String addrmail, String date, String mdp, visibiliter v) {
        ResultSet resultat1;
        String retour;
        int idMax = 0;
        boolean VerifMail;

        try {
            int VerifMdp = 0;
            st = co.createStatement();
            VerifMail = verifierMail(addrmail);
            VerifMdp = verifierMdp(mdp);
            if (VerifMail != true) {
                return new GestionRetourBDD().valeurRetour("Mail double") + " " + addrmail;
            } else if (VerifMdp == 1) {
                return new GestionRetourBDD().valeurRetour("Mdp court");
            } else if (VerifMdp == 2) {
                return new GestionRetourBDD().valeurRetour("Mdp long");
            } else {
                resultat1 = st.executeQuery("SELECT  max(Id)  FROM Utilisateurs;");
                while (resultat1.next()) {
                    idMax = resultat1.getInt("max(Id)");
                    idMax += 1;
                }
                String sql = "INSERT INTO Utilisateurs(`Id`, `Nom`, `Prenom`, `AddrMail`,`AnneeN`, `Mdp`, `VisibleInf`) VALUES (" + idMax + ",'" + nom + "','" + prenom + "','" + addrmail + "','" + date + "','" + mdp + "', '" + v + "');";
                st.executeUpdate(sql);
                return new GestionRetourBDD().valeurRetour("Inscription ok");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Erreur creation");
        }
    }

    public String ajouterCompetence(int id, String matiere, niveau n, visibiliter v) {
        try {
            st = co.createStatement();
            String sql = "INSERT INTO Competences VALUES (" + id + ",'" + matiere + "','" + n + "','" + v + "')";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Ajout competence ok");
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Doublon competence");
        }
    }

    public String ajouterDiplome(int id, String dateobtention, String diplome, String etabli, visibiliter v) {
        try {
            st = co.createStatement();
            String sql = "INSERT INTO Diplomes VALUES (" + id + ",'" + dateobtention + "','" + diplome + "','" + etabli + "','" + v + "')";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Diplome ok");

        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Doublon diplome");
        }
    }

    public String modifierInformation(int id, String mdp, String tel, String addrmail, visibiliter vi,String d) {

        if(d.equals("Oui")){
            d="TRUE";
        }else{
            d="FALSE";
        }
        
        try {
            int i;
            int val = 0;
            ResultSet resultat1;
            st = co.createStatement();
            boolean verifMail;
            verifMail = verifierMail(addrmail);
            resultat1 = st.executeQuery("SELECT  COUNT(*) FROM Utilisateurs WHERE Id=" + id + " AND AddrMail='" + addrmail + "'");

            if (verifMail != true) {

                for (i = 0; resultat1.next(); i++) {
                    val = ((Number) resultat1.getObject(1)).intValue();
                }
                if (val == 1) {
                    String sql = "UPDATE Utilisateurs SET AddrMail='" + addrmail + "', Tel='" + tel + "', Mdp='" + mdp + "',VisibleInf='" + vi + "', Disponible= "+d+" WHERE Id=" + id + "";
                    st.executeUpdate(sql);
                    return new GestionRetourBDD().valeurRetour("Information modif ok");
                } else {
                    return new GestionRetourBDD().valeurRetour("Mail double")+addrmail;
                }
            } else {
                String sql = "UPDATE Utilisateurs SET AddrMail='" + addrmail + "', Tel='" + tel + "', Mdp='" + mdp + "',VisibleInf='" + vi + "'WHERE Id=" + id + "";
                st.executeUpdate(sql);
                return new GestionRetourBDD().valeurRetour("Information modif ok");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Erreur BDD");
        }

    }

    public String modifierCompetence(int id, String matiere, niveau n, visibiliter v) {

        try {
            st = co.createStatement();
            String sql = "UPDATE Competences SET Niveau='" + n + "', VisibleComp='" + v + "' WHERE IdUtilisateur=" + id + " AND Matiere='" + matiere + "'";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Modif competence ok");
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("");
        }
    }

    public String modifierDiplome(int id, String diplome, visibiliter v) {

        try {
            st = co.createStatement();
            String sql = "UPDATE Diplomes SET  VisibleDip='" + v + "' WHERE IdUtilisateur=" + id + " AND Diplome='" + diplome + "'";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Modif diplome ok");
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("");
        }
    }

    public String supprimerDiplome(int id, String diplome) {

        try {
            st = co.createStatement();
            String sql = "DELETE FROM Diplomes WHERE IdUtilisateur=" + id + " AND Diplome='" + diplome + "'";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Suppression diplome");
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Erreur suppression diplome");
        }
    }

    public String supprimerCompetence(int id, String matiere) {
        try {
            st = co.createStatement();
            String sql2 = "DELETE FROM Liker WHERE IdRecepteur=" + id + " AND Matiere='" + matiere + "'";
            st.executeUpdate(sql2);
            String sql = "DELETE FROM Competences WHERE IdUtilisateur=" + id + " AND Matiere='" + matiere + "'";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Suppression competence");
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Erreur suppression competence");
        }
    }
    
    /*
    Méthode recherche: 
        Cette méthode est complexe, c'est à dire qu'en fonction des parametres recus, on doit envoyer une requete spécifique à la BDD.
        On test d'abord les valeurs des champs, si NULL alors rien si !NULL alors on insere le parametre dans la requete
        Il faut concatener les requetes en fonctions des parametres fournis afin d'obtenir uen requete MySQL valide.
    */


    public String recherche(int idcourant, String nom, String prenom, String diplome, String matiere, niveau n) {

        ResultSet r1;
        int idvisite = 0;
        String util = typeVisiteur(idcourant, idvisite);
        String nomRetour, prenomRetour;
        int idRetour;
        String retour = "", requeteFINAL;
        boolean vr;
        int verif = 0;
        String requeteSELECT = "SELECT Id, Nom , Prenom ",
                requeteFROM = "FROM Utilisateurs u ",
                requeteWHERE = "WHERE",
                whereMatiere = "",
                whereDiplome = "",
                whereNiveau = "";

        if (!nom.equals("NULL")) {
            requeteWHERE += " u.Nom LIKE '" + nom + "'";
        }

        if (!prenom.equals("NULL")) {
            if (!requeteWHERE.equals("WHERE")) {
                requeteWHERE += " AND ";
            }
            requeteWHERE += " u.Prenom LIKE '" + prenom + "'";
        }

        if (!matiere.equals("NULL")) {
            if (!requeteWHERE.equals("WHERE")) {
                whereMatiere = " AND ";
            }
            whereMatiere += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' ORDER BY NbrLike ";
        }

        if (n == Bdd.niveau.Moyen) {
            if (!requeteWHERE.equals("WHERE")) {
                whereNiveau = " AND ";
            }
            whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Niveau='Moyen' OR  Niveau='Bon' OR Niveau='TresBon' ";
        }

        if (n == Bdd.niveau.Bon) {
            if (!requeteWHERE.equals("WHERE")) {
                whereNiveau = " AND ";
            }
            whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Niveau='Bon' OR Niveau='TresBon' ";
        }

        if (n == Bdd.niveau.Tresbon) {
            if (!requeteWHERE.equals("WHERE")) {
                whereNiveau = " AND ";
            }
            whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Niveau='TresBon' ";
        }

        if (!diplome.equals("NULL")) {
            if (!requeteWHERE.equals("WHERE")) {
                whereDiplome = " AND ";
            }
            whereDiplome += " u.Id IN (SELECT IdUtilisateur FROM Diplomes WHERE Diplome LIKE '" + diplome + "' ";
        }
        
        /* On verifie si le champs niveau et matiere sont nul ou non. */
        if (n != niveau.NULL && !matiere.equals("NULL")) {
            verif = 1;
        } else if (n != niveau.NULL) {
            verif = 2;
        } else if (!matiere.equals("NULL")) {
            verif = 3;
        }

        try {
            /*Quelque soit la visibilité, si tous les parametres sont a NULL alors on retourne les 5 derniers inscrits.*/
            st = co.createStatement();
            if(nom.equals("NULL") && prenom.equals("NULL") && matiere.equals("NULL") && diplome.equals("NULL") && n== Bdd.niveau.NULL){
                    
                    requeteFINAL="SELECT `Id`,`Nom`,`Prenom` FROM `Utilisateurs` ORDER BY Id DESC LIMIT 5";
                    r1 = st.executeQuery(requeteFINAL);
                    while (r1.next()) {
                            if (r1.isLast()) {
                                idRetour = r1.getInt("Id");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                retour += idRetour + "#" + nomRetour + "#" + prenomRetour;
                            } else {
                                idRetour = r1.getInt("Id");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                retour += idRetour + "#" + nomRetour + "#" + prenomRetour + "$";
                            }
                        }return retour;
                    }
            
            /*Le switch (util) nous permet de connaitre le type de l'utilisateur, ADMIN, UtilisateurCo ou bien visiteur.
                Le switch (verif) nous permet de faire une bonne concaténation de la requete en fonctions des parametres.
            */
            
            switch (util) {
                case "Utilisateur":
                    switch (verif) {
                        case 1:
                            whereMatiere = "";
                            whereNiveau = "";
                            if (n == Bdd.niveau.Moyen) {
                                if (!requeteWHERE.equals("WHERE")) {
                                    whereNiveau = " AND ";
                                }
                                whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' AND (Niveau='Moyen' OR  Niveau='Bon' OR Niveau='TresBon') AND VisibleComp!='Prive' ORDER BY NbrLike)";
                            }

                            if (n == Bdd.niveau.Bon) {
                                if (!requeteWHERE.equals("WHERE")) {
                                    whereNiveau = " AND ";
                                }
                                whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' AND (Niveau='Bon' OR Niveau='TresBon') AND VisibleComp!='Prive' ORDER BY NbrLike)";
                            }

                            if (n == Bdd.niveau.Tresbon) {
                                if (!requeteWHERE.equals("WHERE")) {
                                    whereNiveau = " AND ";
                                }
                                whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' AND Niveau='TresBon' AND VisibleComp!='Prive' ORDER BY NbrLike) ";
                            }
                            break;
                        case 2:

                            whereNiveau += "AND VisibleComp!='Prive')";

                            break;
                        case 3:

                            whereMatiere += "AND VisibleComp!='Prive')";

                            break;
                    }
                    if (!diplome.equals("NULL")) {

                        whereDiplome += "AND VisibleDip!='Prive')";
                    }
                    requeteFINAL = requeteSELECT + requeteFROM + requeteWHERE + whereMatiere + whereNiveau + whereDiplome;

                    r1 = st.executeQuery(requeteFINAL);
                    vr = verifierRequete(requeteFINAL);
                    if (vr == true) {
                        while (r1.next()) {
                            if (r1.isLast()) {
                                idRetour = r1.getInt("Id");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                retour += idRetour + "#" + nomRetour + "#" + prenomRetour;
                            } else {
                                idRetour = r1.getInt("Id");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                retour += idRetour + "#" + nomRetour + "#" + prenomRetour + "$";
                            }
                        }
                    } else {
                        retour = "NOBODY";
                    }
                    return retour;

                case "Admin":
                    switch (verif) {
                        case 1:
                            whereMatiere = "";
                            whereNiveau = "";
                            if (n == Bdd.niveau.Moyen) {
                                if (!requeteWHERE.equals("WHERE")) {
                                    whereNiveau = " AND ";
                                }
                                whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' AND (Niveau='Moyen' OR  Niveau='Bon' OR Niveau='TresBon') AND VisibleComp!='Prive' ORDER BY NbrLike)";
                            }

                            if (n == Bdd.niveau.Bon) {
                                if (!requeteWHERE.equals("WHERE")) {
                                    whereNiveau = " AND ";
                                }
                                whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' AND (Niveau='Bon' OR Niveau='TresBon') AND VisibleComp!='Prive' ORDER BY NbrLike)";
                            }

                            if (n == Bdd.niveau.Tresbon) {
                                if (!requeteWHERE.equals("WHERE")) {
                                    whereNiveau = " AND ";
                                }
                                whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' AND Niveau='TresBon' AND VisibleComp!='Prive' ORDER BY NbrLike) ";
                            }
                            break;
                        case 2:

                            whereNiveau += ")";

                            break;
                        case 3:

                            whereMatiere += ")";

                            break;
                    }
                    if (!diplome.equals("NULL")) {

                        whereDiplome += ")";
                    }
                    requeteFINAL = requeteSELECT + requeteFROM + requeteWHERE + whereMatiere + whereNiveau + whereDiplome;

                    r1 = st.executeQuery(requeteFINAL);
                    vr = verifierRequete(requeteFINAL);
                    if (vr == true) {
                        while (r1.next()) {
                            if (r1.isLast()) {
                                idRetour = r1.getInt("Id");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                retour += idRetour + "#" + nomRetour + "#" + prenomRetour;
                            } else {
                                idRetour = r1.getInt("Id");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                retour += idRetour + "#" + nomRetour + "#" + prenomRetour + "$";
                            }
                        }
                    } else {
                        retour = "NOBODY";
                    }
                    return retour;

                default:
                    switch (verif) {
                        case 1:
                            whereMatiere = "";
                            whereNiveau = "";
                            if (n == Bdd.niveau.Moyen) {
                                if (!requeteWHERE.equals("WHERE")) {
                                    whereNiveau = " AND ";
                                }
                                whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' AND (Niveau='Moyen' OR  Niveau='Bon' OR Niveau='TresBon') AND VisibleComp='Public' ORDER BY NbrLike)";
                            }

                            if (n == Bdd.niveau.Bon) {
                                if (!requeteWHERE.equals("WHERE")) {
                                    whereNiveau = " AND ";
                                }
                                whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' AND (Niveau='Bon' OR Niveau='TresBon') AND VisibleComp='Public' ORDER BY NbrLike)";
                            }

                            if (n == Bdd.niveau.Tresbon) {
                                if (!requeteWHERE.equals("WHERE")) {
                                    whereNiveau = " AND ";
                                }
                                whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere LIKE '" + matiere + "' AND Niveau='TresBon' AND VisibleComp='Public' ORDER BY NbrLike) ";
                            }
                            break;
                        case 2:

                            whereNiveau += "AND VisibleComp='Public')";

                            break;
                        case 3:

                            whereMatiere += "AND VisibleComp='Public')";

                            break;
                    }
                    if (!diplome.equals("NULL")) {

                        whereDiplome += "AND VisibleDip='Public')";
                    }
                    requeteFINAL = requeteSELECT + requeteFROM + requeteWHERE + whereMatiere + whereNiveau + whereDiplome;

                    r1 = st.executeQuery(requeteFINAL);
                    vr = verifierRequete(requeteFINAL);
                    if (vr == true) {
                        while (r1.next()) {
                            if (r1.isLast()) {
                                idRetour = r1.getInt("Id");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                retour += idRetour + "#" + nomRetour + "#" + prenomRetour;
                            } else {
                                idRetour = r1.getInt("Id");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                retour += idRetour + "#" + nomRetour + "#" + prenomRetour + "$";
                            }
                        }
                    } else {
                        retour = "NOBODY";
                    }
                    return retour;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Recherche Erreur");
        }

    }

    /*
    visiter profil, si nous sommes en Visiteurs alors on ne récupere que les informations de type Public de l'utilisateur.
        Si admin ou visite de son propre profil (Idcourant=Idvisite) on récupere tout meme le champs visibilité (en cas de bug l'administrateur peut alors changé la visibilité d'un utilisateur)
        Si utilisateur, on récupere toutes les informations de type Public ou UtilisateurCo (!Prive)
        
        Les champs Information, Compétences et Diplomes sont séparer a l'aide END_I#END_C#END_D
    
    */
    public String visiterProfil(int idcourant, int idvisite) {

        try {
            ResultSet resultatInfo, resultatComp, resultatDip, resultatAdmin;

            String visiInfo = visibiliterInfo(idvisite);

            String nom, prenom, mail, tel, matiere, niveau, diplome, eta, Diplome, visibiliter, retourInfo = "", retourComp = "", retourDip = "", retourAdmin = "", retourVisiteur;
            String visiteur = typeVisiteur(idcourant, idvisite);
            int nbrLike, getLike;
            boolean disponible;
            Date annee;

            String retourUtilisateur;
            String r1 = "SELECT * FROM Competences WHERE IdUtilisateur=" + idvisite + " AND (VisibleComp='Public' OR VisibleComp ='UtilisateurCo')";
            String r2 = "SELECT * FROM Diplomes WHERE IdUtilisateur=" + idvisite + " AND (VisibleDip='Public' OR VisibleDip ='UtilisateurCo')";
            String r3 = "SELECT * FROM Utilisateurs WHERE Id=" + idvisite + "";
            String r4 = "SELECT * FROM Competences WHERE IdUtilisateur=" + idvisite + "";
            String r5 = "SELECT * FROM Diplomes WHERE IdUtilisateur=" + idvisite + "";
            String r6 = "SELECT * FROM Competences WHERE IdUtilisateur=" + idvisite + " AND VisibleComp='Public'";
            String r7 = "SELECT * FROM Diplomes WHERE IdUtilisateur=" + idvisite + " AND VisibleDip='Public'";
            
            boolean vr, vr1;
            switch (visiteur) {

                case "Utilisateur":
                    st = co.createStatement();
                    resultatInfo = st.executeQuery(r3);
                    if (!visiInfo.equals("Prive")) {

                        while (resultatInfo.next()) {
                            nom = resultatInfo.getString("Nom");
                            prenom = resultatInfo.getString("Prenom");
                            mail = resultatInfo.getString("AddrMail");
                            annee = resultatInfo.getDate("AnneeN");
                            tel = resultatInfo.getString("Tel");
                            retourInfo = idvisite + "#" + nom + "#" + prenom + "#" + mail + "#" + tel + "#" + annee + "#" + "END_I#";
                        }
                    } else {
                        while (resultatInfo.next()) {
                            nom = resultatInfo.getString("Nom");
                            prenom = resultatInfo.getString("Prenom");
                            mail = resultatInfo.getString("AddrMail");
                            retourInfo = idvisite + "#" + nom + "#" + prenom + "#" + mail + "#" + "END_I#";
                        }
                    }

                    resultatComp = st.executeQuery(r1);
                    vr = verifierRequete(r1);
                    if (vr == true) {
                        while (resultatComp.next()) {
                            if (resultatComp.isLast()) {
                                matiere = resultatComp.getString("Matiere");
                                niveau = resultatComp.getString("Niveau");
                                nbrLike= resultatComp.getInt("NbrLike");
                                String r8=  "SELECT * FROM Liker WHERE IdEmetteur="+idcourant+" AND Matiere='"+matiere+"' AND IdRecepteur="+idvisite+"";
                                vr1=verifierRequete(r8);
                                if(vr1 == true){
                                    getLike=1;
                                }else{
                                    getLike=0;
                                }
                                retourComp = retourComp + matiere + "#" + niveau + "#" + nbrLike + "#" + getLike + "#END_C#";
                            } else {
                                matiere = resultatComp.getString("Matiere");
                                niveau = resultatComp.getString("Niveau");
                                nbrLike= resultatComp.getInt("NbrLike");
                                String r8=  "SELECT * FROM Liker WHERE IdEmetteur="+idcourant+" AND Matiere='"+matiere+"' AND IdRecepteur="+idvisite+"";
                                vr1=verifierRequete(r8);
                                if(vr1 == true){
                                    getLike=1;
                                }else{
                                    getLike=0;
                                }
                                retourComp = retourComp + matiere + "#" + niveau + "#" + nbrLike +  "#" + getLike + "$";
                            }

                        }
                    } else {
                        retourComp = "END_C#";
                    }

                    resultatDip = st.executeQuery(r2);
                    vr = verifierRequete(r2);
                    if (vr == true) {

                        while (resultatDip.next()) {
                            if (resultatDip.isLast()) {
                                Diplome = resultatDip.getString("Diplome");
                                annee = resultatDip.getDate("AnneeObt");
                                eta = resultatDip.getString("Etablissement");
                                retourDip = retourDip + Diplome + "#" + eta + "#" + annee + "#END_D";
                            } else {
                                Diplome = resultatDip.getString("Diplome");
                                annee = resultatDip.getDate("AnneeObt");
                                eta = resultatDip.getString("Etablissement");
                                retourDip = retourDip + Diplome + "#" + eta + "#" + annee + "$";

                            }
                        }

                    } else {
                        retourDip = "END_D";
                    }

                    retourUtilisateur = retourInfo + retourComp + retourDip;
                    return retourUtilisateur;

                case "Admin":
                case "MonProfil":
                    resultatAdmin = st.executeQuery(r3);
                    
                    
                    while (resultatAdmin.next()) {
                        nom = resultatAdmin.getString("Nom");
                        prenom = resultatAdmin.getString("Prenom");
                        mail = resultatAdmin.getString("AddrMail");
                        annee = resultatAdmin.getDate("AnneeN");
                        tel = resultatAdmin.getString("Tel");
                        visibiliter = resultatAdmin.getString("VisibleInf");
                        disponible=resultatAdmin.getBoolean("Disponible");
                        retourAdmin = idvisite + "#" + nom + "#" + prenom + "#" + mail + "#" + tel + "#" + annee + "#" + visibiliter + "#" +disponible+ "#END_I#";
                    }
                    ;
                    resultatAdmin = st.executeQuery(r4);
                    vr = verifierRequete(r4);
                    
                    if(vr == true){
                    while (resultatAdmin.next()) {
                        if (resultatAdmin.isLast()) {
                            matiere = resultatAdmin.getString("Matiere");
                            niveau = resultatAdmin.getString("Niveau");
                            visibiliter = resultatAdmin.getString("VisibleComp");
                            nbrLike= resultatAdmin.getInt("NbrLike");
                            retourAdmin = retourAdmin + matiere + "#" + niveau + "#" + visibiliter + "#" + nbrLike + "#END_C#";
                        } else {
                            matiere = resultatAdmin.getString("Matiere");
                            niveau = resultatAdmin.getString("Niveau");
                            visibiliter = resultatAdmin.getString("VisibleComp");
                            nbrLike= resultatAdmin.getInt("NbrLike");
                            retourAdmin = retourAdmin + matiere + "#" + niveau + "#" + visibiliter + "#" + nbrLike + "$";
                        }

                    }
                    }else{
                        retourAdmin += "END_C#";
                    }
                    resultatAdmin = st.executeQuery(r5);
                    vr = verifierRequete(r5);
                    if(vr==true){
                    while (resultatAdmin.next()) {
                        if (resultatAdmin.isLast()) {
                            Diplome = resultatAdmin.getString("Diplome");
                            annee = resultatAdmin.getDate("AnneeObt");
                            eta = resultatAdmin.getString("Etablissement");
                            visibiliter = resultatAdmin.getString("VisibleDip");
                            retourAdmin = retourAdmin + Diplome + "#" + eta + "#" + annee + "#" + visibiliter + "#END_D";
                        } else {
                            Diplome = resultatAdmin.getString("Diplome");
                            annee = resultatAdmin.getDate("AnneeObt");
                            eta = resultatAdmin.getString("Etablissement");
                            visibiliter = resultatAdmin.getString("VisibleDip");
                            retourAdmin = retourAdmin + Diplome + "#" + eta + "#" + annee + "#" + visibiliter + "$";
                        }

                    }
                    }else{
                        retourAdmin += "END_D";
                    }

                    return retourAdmin;

                default:
                    st = co.createStatement();
                    resultatInfo = st.executeQuery(r3);
                    if (!visiInfo.equals("Public")) {
                        while (resultatInfo.next()) {
                            nom = resultatInfo.getString("Nom");
                            prenom = resultatInfo.getString("Prenom");
                            mail= resultatInfo.getString("AddrMail");
                            retourInfo = idvisite + "#" + nom + "#" + prenom + "#" + mail + "#" + "END_I#";
                        }
                    } else {
                        while (resultatInfo.next()) {
                            nom = resultatInfo.getString("Nom");
                            prenom = resultatInfo.getString("Prenom");
                            mail = resultatInfo.getString("AddrMail");
                            annee = resultatInfo.getDate("AnneeN");
                            tel = resultatInfo.getString("Tel");
                            retourInfo = idvisite + "#" + nom + "#" + prenom + "#" + mail + "#" + tel + "#" + annee + "#" + "END_I#";
                        }
                    }

                    resultatComp = st.executeQuery(r6);
                    vr = verifierRequete(r6);
                    if (vr == true) {
                        while (resultatComp.next()) {
                            if (resultatComp.isLast()) {
                                matiere = resultatComp.getString("Matiere");
                                niveau = resultatComp.getString("Niveau");
                                nbrLike= resultatComp.getInt("NbrLike");
                                retourComp = retourComp + matiere + "#" + niveau + "#" + nbrLike +"#END_C#";
                            } else {
                                matiere = resultatComp.getString("Matiere");
                                niveau = resultatComp.getString("Niveau");
                                nbrLike= resultatComp.getInt("NbrLike");
                                retourComp = retourComp + matiere + "#" + niveau + "#" + nbrLike + "$"; 
                            }

                        }
                    } else {
                        retourComp = "END_C#";
                    }

                    resultatDip = st.executeQuery(r7);
                    vr = verifierRequete(r7);
                    if (vr == true) {

                        while (resultatDip.next()) {
                            if (resultatDip.isLast()) {
                                Diplome = resultatDip.getString("Diplome");
                                annee = resultatDip.getDate("AnneeObt");
                                eta = resultatDip.getString("Etablissement");
                                retourDip = retourDip + Diplome + "#" + eta + "#" + annee + "#END_D";
                            }else{
                            Diplome = resultatDip.getString("Diplome");
                            annee = resultatDip.getDate("AnneeObt");
                            eta = resultatDip.getString("Etablissement");
                            retourDip = retourDip + Diplome + "#" + eta + "#" + annee + "$";
                            }
                            
                        }

                    } else {
                        retourDip = "END_D";
                    }
                    retourVisiteur = retourInfo + retourComp + retourDip;
                    return retourVisiteur;

            }

        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Erreur Visite");
        }

    }
        
        
        //////////////////////////********************************************V2 MESSAGERIE ******************************//////////////////////////////////
        
        public String envoyerMessage(int idcourant, int idrecepteur, String objet, String message){
        
            try {
            st = co.createStatement();
            String sql = "INSERT INTO `Message`(`IdEmetteur`, `IdRecepteur`, `Objet`, `Message`) VALUES ("+idcourant+", "+idrecepteur+", '"+objet+"' , '"+message+"') ";
            st.executeUpdate(sql);
            return "OK";
            } catch (SQLException ex) {
                Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
                return "ERROR";
            }
        }
    
        public String afficherMessage(int idMsg){
        
            String nomRetour,prenomRetour,retour="", objet , message ;
            ResultSet r1;
            try {
            st = co.createStatement();
            String sql = "SELECT  Objet, Message, Nom, Prenom FROM Message m, Utilisateurs u WHERE IdMessage="+idMsg+" AND u.Id=m.IdEmetteur";
            r1 = st.executeQuery(sql);
            while(r1.next()){
                                objet = r1.getString("objet");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                message=r1.getString("Message");
                                retour = nomRetour + "#" + prenomRetour + "#" + objet + "#" + message ;
            }
            /*on change d'etat le message*/
            String sql1 = "UPDATE Message SET Lu=TRUE WHERE IdMessage="+idMsg+"";
            st.executeUpdate(sql1);
            
            } catch (SQLException ex) {
                Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            return retour;
        }
    
        public String messagerie(int idCourant){
            String nomRetour,prenomRetour,retour="", objet, idMsg;
            boolean lu ;
            ResultSet r1;
            try {
            st = co.createStatement();
            String sql = "SELECT Nom, Prenom, objet, IdMessage, Lu FROM Message m, Utilisateurs u WHERE m.IdRecepteur="+idCourant+" AND u.Id IN (SELECT Id FROM Utilisateurs WHERE u.Id=m.IdEmetteur)";
            r1 = st.executeQuery(sql);
            while(r1.next()){
                    if(r1.isLast()){
                                objet = r1.getString("objet");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                idMsg=r1.getString("IdMessage");
                                lu=r1.getBoolean("lu");
                                retour += idMsg + "#" + nomRetour + "#" + prenomRetour + "#" + objet + "#" + lu;
                        
                    }else{
                                objet = r1.getString("objet");
                                nomRetour = r1.getString("Nom");
                                prenomRetour = r1.getString("Prenom");
                                idMsg=r1.getString("IdMessage");
                                lu=r1.getBoolean("lu");
                                retour += idMsg + "#" + nomRetour + "#" + prenomRetour + "#" + objet + "#" + lu + "$";
                    }
            }

            } catch (SQLException ex) {
                Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return retour;
        }
        
        public String utilisateurs(int idCourant){
            String nomRetour,prenomRetour,retour="";
            int id;
            ResultSet r1;
            try {
            st = co.createStatement();
            String sql = "SELECT Id, Nom, Prenom FROM Utilisateurs WHERE Id!="+idCourant+"";
            r1 = st.executeQuery(sql);
                while(r1.next()){
                        if(r1.isLast()){
                                    id=r1.getInt("Id");
                                    nomRetour = r1.getString("Nom");
                                    prenomRetour = r1.getString("Prenom");
                                    retour += id + "#" +nomRetour + "#" + prenomRetour;

                        }else{
                                    id=r1.getInt("Id");
                                    nomRetour = r1.getString("Nom");
                                    prenomRetour = r1.getString("Prenom");
                                    retour += id + "#" + nomRetour + "#" + prenomRetour +"$";
                        }
                }
            }catch (SQLException ex) {
                Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            return retour;   
        }

        
        public String getNomPrenom(int idcourant){
            
            ResultSet r1;
            String nomRetour, prenomRetour, retour="ERROR";
           try {

            st = co.createStatement();
            String sql = "SELECT Nom, Prenom FROM Utilisateurs WHERE Id="+idcourant+"";
            r1 = st.executeQuery(sql);
                while(r1.next()){
                        nomRetour = r1.getString("Nom");
                        prenomRetour = r1.getString("Prenom");
                        retour = nomRetour + "#" + prenomRetour;
                }
            }catch (SQLException ex) {
                Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            }
             return retour;
        }
        
        
        public boolean estDisponible(int idcourant){
            
            ResultSet r1;
            boolean dispo=false;
           try {
            st = co.createStatement();
            String sql = "SELECT Disponible FROM Utilisateurs WHERE Id="+idcourant+"";
            r1 = st.executeQuery(sql);
                while(r1.next()){
                        dispo=r1.getBoolean("Disponible");
                }
            if (dispo != true){
                System.out.println("False");
                return false;
            }else{
                System.out.println("True");
                return true;
            }
            }catch (SQLException ex) {
                Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return false;
        }
        
        
    public String likeComp(int idEmetteur, int idRecepteur, String matiere)  {
        ResultSet r1;
        int i=0;
       	try {
                if(idEmetteur == idRecepteur){
                    
                    return "Tu ne peux pas t'auto liker !";
                }else{
        	st = co.createStatement();
                String sql=("SELECT * FROM Liker WHERE IdEmetteur="+idEmetteur+" AND IdRecepteur="+idRecepteur+" AND Matiere='"+matiere+"' ");
                    r1 = st.executeQuery(sql);
                    if(r1.next()){
                        i=1;
                    }
                if(i==0){
        	String sql1 = "INSERT INTO Liker VALUES (" + idEmetteur + "," + idRecepteur + ",'" + matiere + "')";
                st.executeUpdate(sql1);
                String sql2 ="UPDATE `Competences` SET `NbrLike`= `NbrLike`+ 1 WHERE Matiere='"+matiere+"' AND IdUtilisateur="+idRecepteur+"";
                st.executeUpdate(sql2);
        	return new GestionRetourBDD().valeurRetour("Ajout Like ok");
                }else{
                    String sql1="DELETE FROM Liker WHERE IdEmetteur="+idEmetteur+" AND IdRecepteur="+idRecepteur+" AND  Matiere='"+matiere+"'";
                    st.executeUpdate(sql1);
                    String sql2 ="UPDATE `Competences` SET `NbrLike`= `NbrLike`- 1 WHERE Matiere='"+matiere+"' AND IdUtilisateur="+idRecepteur+"";
                    st.executeUpdate(sql2);
        	return new GestionRetourBDD().valeurRetour("Retour Like ok");
                }
              }
    	} catch (SQLException ex) {
        	Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        	return new GestionRetourBDD().valeurRetour("Erreur Inconnue");
    	}
	}
    
    
    public static void main(String[] args) {
        String test;
        Bdd bdd = new Bdd();
        bdd.connexion();
        //test=bdd.creerUtilisateur("Testconnexion", "Thomas", "accbc","1994-12-12", "123456",visibiliter.Prive);
        //bdd.verifierMdp("aajjjjjjjj");
        //bdd.ajouterCompetence(1, "Okok", Bdd.niveau.Bon,Bdd.visibiliter.UtilisateurCo);
        //test=bdd.ajouterDiplome(2, "1994-12-12" , "BTS Informatique", "bbb" ,visibiliter.Prive);
        //bdd.modifierInformation(8,"azzz", "", "accbc",visibiliter.Prive,"Oui");
        //int id, String mdp, String tel, String addrmail, visibiliter vi
        //test=bdd.modifierCompetence(1,"fr", Bdd.niveau.Tresbon, Bdd.visibiliter.Prive);
        //bdd.supprimerCompetence(1, "Rugby");
        //bdd.supprimerDiplome(1,"fr");
        //test=bdd.connexionClient("abc", "123456");
        //bdd.envoyerMessage(3, 2, "Test 5 objet", "Test 5 message");
        //test= bdd.messagerie(2);
        test=bdd.recherche(3, "NULL", "NULL", "NULL", "Java", niveau.Bon);
        //test=bdd.utilisateur();
        //test=bdd.visiterProfil(3,2);
        //test=bdd.recherche(1, "NULL", "NULL", "NULL", "NULL", niveau.NULL);
        //bdd.estDisponible(3);
        //test=bdd.getNomPrenom(2);
        //test= bdd.likeComp(3,2,"Java");
        System.out.println(test);
    }
}
