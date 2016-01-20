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
     * constructeur par dÃ©faut de la classe SQL
     */
    public Bdd() {
        this.host = "javaguigui";
        this.pwd = "azerty";
        this.url = "jdbc:mysql://kriissss.fr:3306/javaguigui?zeroDateTimeBehavior=convertToNull";
    }
    

    /**
     * Methode pour se connecter à la BDD
     */
    public void connexion(){
        try {
            // Pas besoins sous JDK 1.8 Class.forName("com.mysql.jdbc");
            //System.out.println("Driver O.K.");
            co = DriverManager.getConnection(url, host, pwd);
            System.out.println("Connexion effective !");
        } catch (SQLException e) {
            System.out.println("Erreur connexion");
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
    public int VerifierMdp(String mdp){
        int count = mdp.codePointCount(0, mdp.length());
        if (count < 6){
            return 1;
        }else if (count > 16){
            return 2;
        }else{
            return 3;
        }
    }
    
    public boolean VerifierMail(String mail) {
        ResultSet verif=null;
        Statement st;
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
                System.out.println("Erreur VerifierMail");
            }
        } catch (SQLException e) {
            System.out.println("Erreur VerifierMail");
        }
        return false;
    }
    
    //Ajout d'information à la BDD.
    
    public void AjoutUtilisateur(String nom, String prenom, String addrmail, String tel, String date,String mdp,visibiliter v){
        Statement st;
        ResultSet resultat1;
        int idMax=0;
        boolean VerifMail;
        int VerifMdp=0;
        try {
            st = co.createStatement();
            VerifMail = VerifierMail(addrmail);
            VerifMdp = VerifierMdp(mdp);
            if (VerifMail!= true) {
            System.out.println("Erreur, l'addresse Mail : " + addrmail + " est deja utilisé");
            } else if( VerifMdp == 1){
                System.out.println("Mot de passe trop court 6 car min ");
                }else if (VerifMdp == 2){
                        System.out.println("Mot de passe trop long 16 car max");
                        }else if(VerifMdp==3){
                  resultat1 = st.executeQuery("SELECT  max(Id)  FROM Utilisateurs;");
                  while (resultat1.next()) {
                    idMax = resultat1.getInt("max(Id)");
                    idMax += 1;
                    }
                  String sql = "INSERT INTO Utilisateurs VALUES (" + idMax + ",'" + nom + "','" + prenom + "','" + addrmail + "','" + tel + "','"+ date +"','" + mdp + "','"+ v +"');";
                  st.executeUpdate(sql);
                }   
          } catch (SQLException ex) {
        Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }    
       
    public void AjouterCompetence(int id, String matiere, niveau n, visibiliter v )
    {
        Statement st;
        try {
            st = co.createStatement();
            String sql="INSERT INTO Competences VALUES (" +id+ ",'"+matiere+"','"+n+"','" +v+"')";
            st.executeUpdate(sql);
            
        }catch (SQLException ex) {
        Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void AjouterDiplome(int id, String Dobtention,String Type ,String etabli, visibiliter v )
    {
        Statement st;
        try {
            st = co.createStatement();
            String sql="INSERT INTO Diplomes VALUES (" +id+ ",'"+Dobtention+"','"+Type+"','" +etabli+"','"+v+"')";
            st.executeUpdate(sql);
            
        }catch (SQLException ex) {
        Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ModifierInformation(int id, String addrmail,String tel,String mdp,visibiliter v){
        
        Statement st;
        try {
            int i;
            int val=0;
            ResultSet resultat1;
            st = co.createStatement();
            boolean verifMail;
            verifMail =  VerifierMail(addrmail);
            resultat1= st.executeQuery("SELECT  COUNT(*) FROM Utilisateurs WHERE Id="+id+" AND AddrMail='"+addrmail+"'");
            
            if(verifMail != true ){
                
                for(i=0;resultat1.next();i++){
                val =((Number) resultat1.getObject(1)).intValue(); 
                }
                if(val == 1){
                    String sql="UPDATE Utilisateurs SET AddrMail='"+addrmail+"', Tel='"+tel+"', Mdp='"+mdp+"',Visible='"+v+"' WHERE Id="+id+"";
                         st.executeUpdate(sql);    
                }else{
                    System.out.println("Erreur, l'addresse Mail : " + addrmail + " est deja utilisé");
                }   
            }else{
                String sql="UPDATE Utilisateurs SET AddrMail='"+addrmail+"', Tel='"+tel+"', Mdp='"+mdp+"',Visible='"+v+"' WHERE Id="+id+"";
                         st.executeUpdate(sql); 
            }
        }catch (SQLException ex) {
        Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    
    
    
 
    public static void main(String[] args){    
       Bdd bdd=new Bdd();
       bdd.connexion();
       //bdd.VerifierMail("grosse@bite.xxx");
       //bdd.AjoutUtilisateur("TEst", "Test", "eooto@hotmail.fr", "0564563423", "1994-12-12", "bricuuuuu",visibiliter.Tous);
       //bdd.VerifierMdp("aajjjjjjjj");
       //bdd.AjouterCompetence(1, "fr", Bdd.niveau.Bon , Bdd.visibiliter.Tous);
       //bdd.AjouterDiplome(1, "1994-12-12" , "fr","kkk", Bdd.visibiliter.Tous);
       bdd.ModifierInformation(4,"Testmodi","000000000","fffffffff", Bdd.visibiliter.Tous);
    
    
    
    }
}

