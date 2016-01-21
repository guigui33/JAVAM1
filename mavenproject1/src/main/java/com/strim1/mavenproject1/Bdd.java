/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.strim1.mavenproject1;
/**
 *
 * @author TomGui
 */
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Classe gÃ©rant la BDD
 *
 * @author STRI_JAVA
 */

public class Bdd{
    
    public Statement st;
    /* Les enum */
    enum visibiliter{
        Personne,
        Tous,
        Utitilisateur;
    };
    enum niveau{
        Moyen,
        Bon,
        Tresbon;
    };
    /**
     * Le nom
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
     * Methode pour se connecter à la BDD
     */
    public String connexion(){
        try {
            // Pas besoins sous JDK 1.8 Class.forName("com.mysql.jdbc");
            //System.out.println("Driver O.K.");
            co = DriverManager.getConnection(url, host, pwd);
            return new GestionRetourBDD().valeurRetour("Connexion BDD ok");
        } catch (SQLException e) {
            return new GestionRetourBDD().valeurRetour("Erreur Connexion");
        }
    }
    //Verification des entrées:
    
    /*public boolean estValide(int annee, int mois, int jour){
    Calendar c = Calendar.getInstance();
    c.setLenient(false);
    c.set(annee,mois,jour);
    try{
    c.getTime();
    }
    catch(IllegalArgumentException iAE){
    return false;
    }
    
    return true;
    }
    */
    public int verifierMdp(String mdp){
        int count = mdp.codePointCount(0, mdp.length());
        if (count < 6){
            return 1;
        }else if (count > 16){
            return 2;
        }else{
            return 3;
        }
    }
    
    public boolean verifierMail(String mail) {
        ResultSet verif=null;
        try {
            st = co.createStatement();
            verif = st.executeQuery("SELECT AddrMail FROM Utilisateurs WHERE AddrMail='" + mail + "'");
            if (verif != null) {
                int nbLignes = 0;
                while (verif.next()) {
                    nbLignes++;
                }
                if(nbLignes == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
            }
        } catch (SQLException e) {
        }
        return false;
    }
    
    //Ajout d'information à la BDD.
    
    public String creerUtilisateur(String nom, String prenom, String addrmail,String date,String mdp){
        ResultSet resultat1;
        String retour;
        int idMax=0;
        boolean VerifMail;
        
        try {
            int VerifMdp=0;
            st = co.createStatement();
            VerifMail = verifierMail(addrmail);
            VerifMdp = verifierMdp(mdp);
            if (VerifMail!= true) {
                return new GestionRetourBDD().valeurRetour("Mail double")+ " " +addrmail;
            } else if( VerifMdp == 1){
                return new GestionRetourBDD().valeurRetour("Mdp court");
            }else if (VerifMdp == 2){
                return new GestionRetourBDD().valeurRetour("Mdp long");
            }else{
                resultat1 = st.executeQuery("SELECT  max(Id)  FROM Utilisateurs;");
                while (resultat1.next()) {
                    idMax = resultat1.getInt("max(Id)");
                    idMax += 1;
                }
                String sql = "INSERT INTO Utilisateurs(`Id`, `Nom`, `Prenom`, `AddrMail`,`AnneeN`, `Mdp`) VALUES (" + idMax + ",'" + nom + "','" + prenom + "','" + addrmail + "','"+ date +"','" + mdp + "');";
                st.executeUpdate(sql);
                return new GestionRetourBDD().valeurRetour("Inscription ok");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new GestionRetourBDD().valeurRetour("Erreur BDD");
    }
    
    public String ajouterCompetence(int id, String matiere, niveau n)
    {
        try {
            st = co.createStatement();
            String sql="INSERT INTO Competences VALUES (" +id+ ",'"+matiere+"','"+n+"')";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Ajout competence ok");
        }catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Doublon competence");
        }
    }
    
    public String ajouterDiplome(int id, String dateobtention,String diplome ,String etabli)
    {
        try {
            st = co.createStatement();
            String sql="INSERT INTO Diplomes VALUES (" +id+ ",'"+dateobtention+"','"+diplome+"','" +etabli+"')";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Diplome ok");
            
        }catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Doublon diplome");
        }
    }
    
    public String modifierInformation(int id, String addrmail,String tel,String mdp,visibiliter vi, visibiliter vd, visibiliter vc){
        
        try {
            int i;
            int val=0;
            ResultSet resultat1;
            st = co.createStatement();
            boolean verifMail;
            verifMail =  verifierMail(addrmail);
            resultat1= st.executeQuery("SELECT  COUNT(*) FROM Utilisateurs WHERE Id="+id+" AND AddrMail='"+addrmail+"'");
 
            if(verifMail != true ){
                
                for(i=0;resultat1.next();i++){
                    val =((Number) resultat1.getObject(1)).intValue();
                }
                if(val == 1){
                    String sql="UPDATE Utilisateurs SET AddrMail='"+addrmail+"', Tel='"+tel+"', Mdp='"+mdp+"',VisibleInf='"+vi+"',VisibleComp='"+vc+"',VisibleDipl='"+vd+"' WHERE Id="+id+"";
                    st.executeUpdate(sql);
                    return new GestionRetourBDD().valeurRetour("Information modif ok");
                }else{
                     return new GestionRetourBDD().valeurRetour("Mail double");
                }
            }else{
                String sql="UPDATE Utilisateurs SET AddrMail='"+addrmail+"', Tel='"+tel+"', Mdp='"+mdp+"',VisibleInf='"+vi+"',VisibleComp='"+vc+"',VisibleDipl='"+vd+"' WHERE Id="+id+"";
                st.executeUpdate(sql);
                 return new GestionRetourBDD().valeurRetour("Information modif ok");
            }
        }catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
         return new GestionRetourBDD().valeurRetour("Erreur BDD");
    }
    
    public String modififerCompetence(int id, String matiere, niveau n){
        
        try {
            st = co.createStatement();
            String sql="UPDATE Competences SET Niveau='"+n+"' WHERE IdUtilisateur="+id+" AND Matiere='"+matiere+"'";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Modif competence ok");
        }catch (SQLException ex){
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
            return new GestionRetourBDD().valeurRetour("Erreur BDD");
    }
    
    public String supprimerDiplome(int id,String diplome){
        
        try {
            st = co.createStatement();
            String sql="DELETE FROM Diplomes WHERE IdUtilisateur="+id+" AND Type='"+diplome+"'";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Suppression diplome");
        }catch (SQLException ex){
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
             return new GestionRetourBDD().valeurRetour("Erreur suppression diplome");
        }
    }
    
    public String supprimerCompetence(int id,String matiere){
        try {
            st = co.createStatement();
            String sql="DELETE FROM Competences WHERE IdUtilisateur="+id+" AND Matiere='"+matiere+"'";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Suppression competence");
        }catch (SQLException ex){
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Erreur suppression competence");
        }
    }
    
    public String connexionClient(String mail,String mdp){
        try {
            ResultSet resultat1;
            int idUtil;
            
            st = co.createStatement();
            resultat1= st.executeQuery("SELECT Id FROM Utilisateurs WHERE AddrMail='"+mail+"' AND Mdp='"+mdp+"'");
            resultat1.next();
            idUtil=resultat1.getInt(1);
            System.out.println(idUtil);
            return new GestionRetourBDD().valeurRetour("Connexion client ok."+idUtil);
        }catch (SQLException ex){
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Erreur connexion");
        }
    }
    
    public String visiterProfil(int idcourant,int idvisite ){
        return "pas encore fait";
    }
    
    
    public static void main(String[] args){
        String test;
        Bdd bdd=new Bdd();
        bdd.connexion();
        //bdd.VerifierMail("grosse@bite.xxx");
        //bdd.CreerUtilisateur("Testconnexion", "Test", "abc","1994-12-12", "123456");
        //bdd.VerifierMdp("aajjjjjjjj");
        //bdd.AjouterCompetence(1, "fr", Bdd.niveau.Bon);
        //bdd.AjouterDiplome(1, "1994-12-12" , "fr","kkk");
        //bdd.ModifierInformation(6,"Testmodi@trrtr","","fffffffff", Bdd.visibiliter.Tous,Bdd.visibiliter.Personne,Bdd.visibiliter.Tous);
        //bdd.ModififerCompetence(1,"fr", Bdd.niveau.Tresbon);
        //bdd.SupprimerCompetence(1, "Rugby");
        //bdd.SupprimerDiplome(1,"fr");
        //test=bdd.connexionClient("aacc", "123456");
        //System.out.println(test);
    }
}

