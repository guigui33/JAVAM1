
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
import java.sql.Date;
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
        Prive,
        Public,
        UtilisateurCo;
    };
    enum niveau{
        Moyen,
        Bon,
        Tresbon, 
        NULL;
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
    public boolean connexion(){
        try {
            // Pas besoins sous JDK 1.8 Class.forName("com.mysql.jdbc");
            //System.out.println("Driver O.K.");
            co = DriverManager.getConnection(url, host, pwd);
            return true;
        } catch (SQLException e) {
            return false;
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
    
    public String typeVisiteur(int idcourant, int idprofil){
        
        if (idcourant !=0 && idcourant != 1){
            if (idcourant == idprofil){
            return "MonProfil";
        }
            return "Utilisateur";
        }else if (idcourant == 1){
            return "Admin";
        } 
        return "Visiteur";
        
    }
    
    public visibiliter parseVisibiliter(String v){
        
        switch (v) {
            case "Public":
                return visibiliter.Public;
            case "UtilisateurCo":
                return visibiliter.UtilisateurCo;
            default:
                return visibiliter.Prive;
        }
    }
    
    
    public niveau parseNiveau(String n){
        
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
    
    public boolean verifierRequete(String requete){
        ResultSet verif=null;
        try {
            st = co.createStatement();
            verif = st.executeQuery(requete);
            if (verif != null) {
                int nbLignes = 0;
                while (verif.next()) {
                    nbLignes++;
                }
                if(nbLignes == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
        }
        return false;
    }
    
    
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
    
    public String visibiliterInfo(int id){
        try{
            ResultSet ri;
            String visi;
            st = co.createStatement();
            ri= st.executeQuery("SELECT VisibleInf FROM Utilisateurs WHERE Id="+id+"");
            ri.next();
            visi=ri.getString(1);
            switch (visi) {
                case "Public":
                    return "Public";
                case "UtilisateurCo":
                    return "UtilisateurCo";
                default:
                    return "Prive";
            }
        }catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new GestionRetourBDD().valeurRetour("Erreur BDD");
    }

        //Ajout d'information à la BDD.
    public String creerUtilisateur(String nom, String prenom, String addrmail,String date,String mdp, visibiliter v){
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
                String sql = "INSERT INTO Utilisateurs(`Id`, `Nom`, `Prenom`, `AddrMail`,`AnneeN`, `Mdp`, `VisibleInf`) VALUES (" + idMax + ",'" + nom + "','" + prenom + "','" + addrmail + "','"+ date +"','" + mdp + "', '"+ v +"');";
                st.executeUpdate(sql);
                return new GestionRetourBDD().valeurRetour("Inscription ok");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new GestionRetourBDD().valeurRetour("Erreur BDD");
    }
    
    public String ajouterCompetence(int id, String matiere, niveau n, visibiliter v)
    {
        try {
            st = co.createStatement();
            String sql="INSERT INTO Competences VALUES (" +id+ ",'"+matiere+"','"+n+"','"+ v +"')";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Ajout competence ok");
        }catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Doublon competence");
        }
    }
    
    public String ajouterDiplome(int id, String dateobtention,String diplome ,String etabli,visibiliter v)
    {
        try {
            st = co.createStatement();
            String sql="INSERT INTO Diplomes VALUES (" +id+ ",'"+dateobtention+"','"+diplome+"','" +etabli+"','"+ v +"')";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Diplome ok");
            
        }catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return new GestionRetourBDD().valeurRetour("Doublon diplome");
        }
    }
    
    public String modifierInformation(int id, String mdp,String tel,String addrmail,visibiliter vi){
        
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
                    String sql="UPDATE Utilisateurs SET AddrMail='"+addrmail+"', Tel='"+tel+"', Mdp='"+mdp+"',VisibleInf='"+vi+"' WHERE Id="+id+"";
                    st.executeUpdate(sql);
                    return new GestionRetourBDD().valeurRetour("Information modif ok");
                }else{
                    return new GestionRetourBDD().valeurRetour("Mail double");
                }
            }else{
                String sql="UPDATE Utilisateurs SET AddrMail='"+addrmail+"', Tel='"+tel+"', Mdp='"+mdp+"',VisibleInf='"+vi+"'WHERE Id="+id+"";
                st.executeUpdate(sql);
                return new GestionRetourBDD().valeurRetour("Information modif ok");
            }
        }catch (SQLException ex) {
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new GestionRetourBDD().valeurRetour("Erreur BDD");
    }
    
    public String modifierCompetence(int id, String matiere, niveau n, visibiliter v){
        
        try {
            st = co.createStatement();
            String sql="UPDATE Competences SET Niveau='"+n+"', VisibleComp='"+ v +"' WHERE IdUtilisateur="+id+" AND Matiere='"+matiere+"'";
            st.executeUpdate(sql);
            return new GestionRetourBDD().valeurRetour("Modif competence ok");
        }catch (SQLException ex){
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new GestionRetourBDD().valeurRetour("Erreur BDD");
    }
    
    public String modifierDiplome(int id, String diplome, visibiliter v){
        
        try {
            st = co.createStatement();
            String sql="UPDATE Diplomes SET  VisibleDip='"+ v +"' WHERE IdUtilisateur="+id+" AND Diplome='"+diplome+"'";
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
            String sql="DELETE FROM Diplomes WHERE IdUtilisateur="+id+" AND Diplome='"+diplome+"'";
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
            String prenom;
            
            st = co.createStatement();
            resultat1= st.executeQuery("SELECT Id, Prenom FROM Utilisateurs WHERE AddrMail='"+mail+"' AND Mdp='"+mdp+"'");
            if (resultat1.next()){
            idUtil=resultat1.getInt("Id");
            prenom=resultat1.getString("Prenom");
            return  "OK" + "#" + idUtil +"#" +prenom;
            }
        }catch (SQLException ex){
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR";
        }
        return "ERROR";
    }
    

        
        public String recherche (int idcourant,String nom, String prenom, String diplome, String matiere, niveau n){

    ResultSet r1;
    int idvisite=0;
    String util=typeVisiteur(idcourant,idvisite);
    String nomRetour, prenomRetour;
    int idRetour;
    String retour="",requeteFINAL;
    boolean vr;    
    int verif=0;
    String requeteSELECT="SELECT Id, Nom , Prenom ",
    requeteFROM="FROM Utilisateurs u ",
    requeteWHERE="WHERE",
    whereMatiere="",
    whereDiplome="",
    whereNiveau="";
    
    if(nom != "NULL"){
   	 requeteWHERE += " u.Nom='"+nom+"'";    
    }

    if(prenom != "NULL"){
   	 if(requeteWHERE != "WHERE"){
   	 requeteWHERE += " AND ";   
    }
        requeteWHERE +=  " u.Prenom='"+prenom+"'";
    }
    
    if(matiere != "NULL"){
   	 if(requeteWHERE != "WHERE"){
             whereMatiere = " AND ";
    }
             whereMatiere += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' ";  
    }

    if(n == Bdd.niveau.Moyen){
   	  if(requeteWHERE != "WHERE"){
            whereNiveau = " AND ";  
    }
   	whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Niveau='Moyen' OR  Niveau='Bon' OR Niveau='TresBon' ";  
    }
    
    if(n == Bdd.niveau.Bon){
   	  if(requeteWHERE != "WHERE"){
            whereNiveau = " AND ";  
    }
   	whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Niveau='Bon' OR Niveau='TresBon' ";  
    }
    
    if(n == Bdd.niveau.Tresbon){
   	  if(requeteWHERE != "WHERE"){
            whereNiveau = " AND ";  
    }
   	whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Niveau='TresBon' ";  
    }

    if(diplome != "NULL"){
   	  if(requeteWHERE != "WHERE"){
   	 whereDiplome = " AND ";    
    }
   	 whereDiplome += " u.Id IN (SELECT IdUtilisateur FROM Diplomes WHERE Diplome='"+diplome+"' ";   
    }

    if (n != niveau.NULL && matiere != "NULL"){
                verif=1;
         }else if (n != niveau.NULL){
                 verif=2;
         }else if (matiere != "NULL"){
   		  verif=3;
         }
    
    try{
    st = co.createStatement(); 
    switch (util){
    
    case "Utilisateur":
   	 switch (verif){
                    case 1:
                        whereMatiere="";
                        whereNiveau="";
                        if(n == Bdd.niveau.Moyen){
                                    if(requeteWHERE != "WHERE"){
                                      whereNiveau = " AND ";  
                                    }
                                        whereNiveau +=  " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' AND (Niveau='Moyen' OR  Niveau='Bon' OR Niveau='TresBon') AND VisibleComp!='Prive')";  
                         }
    
                        if(n == Bdd.niveau.Bon){
                              if(requeteWHERE != "WHERE"){
                                whereNiveau = " AND ";  
                                }
                                    whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' AND (Niveau='Bon' OR Niveau='TresBon') AND VisibleComp!='Prive')";  
                        }
    
                        if(n == Bdd.niveau.Tresbon){
                              if(requeteWHERE != "WHERE"){
                                whereNiveau = " AND ";  
                                }
                                    whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' AND Niveau='TresBon' AND VisibleComp!='Prive') ";  
                        }
                        break;
                    case 2:
                        
                        whereNiveau += "AND VisibleComp!='Prive')";
                    
                        break;
                    case 3:
                        whereMatiere += "AND VisibleComp!='Prive')";
                        break;
                }
    if (diplome !="NULL"){
    
            whereDiplome += "AND VisibleDip!='Prive')";
        } 
   	 requeteFINAL = requeteSELECT + requeteFROM + requeteWHERE + whereMatiere + whereNiveau + whereDiplome;    

   	 r1=st.executeQuery(requeteFINAL);
   	 vr=verifierRequete(requeteFINAL);
   	 if (vr == true){
   	 while(r1.next()){    
   		 idRetour=r1.getInt("Id");
   		 nomRetour=r1.getString("Nom");   	 
   		 prenomRetour=r1.getString("Prenom");
   		 retour += idRetour + "#" + nomRetour + "#" + prenomRetour + "#";    
   	 }
   	 }else{
   		 retour="NOBODY";
   	 }
    return retour;

case "Admin":
switch (verif){
                    case 1:
                        whereMatiere="";
                        whereNiveau="";
                        if(n == Bdd.niveau.Moyen){
                                    if(requeteWHERE != "WHERE"){
                                      whereNiveau = " AND ";  
                                    }
                                        whereNiveau +=  " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' AND (Niveau='Moyen' OR  Niveau='Bon' OR Niveau='TresBon'))";  
                         }
    
                        if(n == Bdd.niveau.Bon){
                              if(requeteWHERE != "WHERE"){
                                whereNiveau = " AND ";  
                                }
                                    whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' AND (Niveau='Bon' OR Niveau='TresBon'))";  
                        }
    
                        if(n == Bdd.niveau.Tresbon){
                              if(requeteWHERE != "WHERE"){
                                whereNiveau = " AND ";  
                                }
                                    whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' AND Niveau='TresBon') ";  
                        }
                        break;
                    case 2:
                        
                        whereNiveau += ")";
                    
                        break;
                    case 3:
                        whereMatiere += ")";
                        break;
                }
    
    if (diplome !="NULL"){
    
            whereDiplome += ")";
        }    
        
    requeteFINAL = requeteSELECT + requeteFROM + requeteWHERE + whereMatiere + whereNiveau + whereDiplome ;    

        r1=st.executeQuery(requeteFINAL);
        vr=verifierRequete(requeteFINAL);
        if (vr == true){
        while(r1.next()){    
            idRetour=r1.getInt("Id");
            nomRetour=r1.getString("Nom");       
            prenomRetour=r1.getString("Prenom");
            retour += idRetour + "#" + nomRetour + "#" + prenomRetour + "#";    
        }
        }else{
            retour="NOBODY";
        }
    return retour;

default:
switch (verif){
                    case 1:
                        whereMatiere="";
                        whereNiveau="";
                        if(n == Bdd.niveau.Moyen){
                                    if(requeteWHERE != "WHERE"){
                                      whereNiveau = " AND ";  
                                    }
                                        whereNiveau +=  " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' AND (Niveau='Moyen' OR  Niveau='Bon' OR Niveau='TresBon') AND VisibleComp!='Public')";  
                         }
    
                        if(n == Bdd.niveau.Bon){
                              if(requeteWHERE != "WHERE"){
                                whereNiveau = " AND ";  
                                }
                                    whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' AND (Niveau='Bon' OR Niveau='TresBon') AND VisibleComp='Public')";  
                        }
    
                        if(n == Bdd.niveau.Tresbon){
                              if(requeteWHERE != "WHERE"){
                                whereNiveau = " AND ";  
                                }
                                    whereNiveau += " u.Id IN (SELECT IdUtilisateur FROM Competences WHERE Matiere='"+matiere+"' AND Niveau='TresBon' AND VisibleComp='Public') ";  
                        }
                        break;
                    case 2:
                        
                        whereNiveau += "AND VisibleComp='Public')";
                    
                        break;
                    case 3:
                        whereMatiere += "AND VisibleComp='Public')";
                        break;
                }
    
    if (diplome !="NULL"){
    
            whereDiplome += "AND VisibleDip='Public')";
        }    
        
    requeteFINAL = requeteSELECT + requeteFROM + requeteWHERE + whereMatiere + whereNiveau + whereDiplome ;    
    
        r1=st.executeQuery(requeteFINAL);
        vr=verifierRequete(requeteFINAL);
        if (vr == true){
        while(r1.next()){    
            idRetour=r1.getInt("Id");
            nomRetour=r1.getString("Nom");       
            prenomRetour=r1.getString("Prenom");
            retour += idRetour + "#" + nomRetour + "#" + prenomRetour + "#";    
        }
        }else{
            retour="NOBODY";
        }
    return retour;
    }
    }catch (SQLException ex){
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
            return "ERROR#RechercheRequete";
        }
        
}        
        
        public String visiterProfil(int idcourant, int idvisite){
            
            try {
            ResultSet resultatInfo, resultatComp, resultatDip, resultatAdmin;
            
            String visiInfo=visibiliterInfo(idvisite);
            
            String nom, prenom, mail, tel,matiere,niveau,diplome,eta,Diplome,visibiliter, retourInfo="",retourComp="",retourDip="", retourAdmin="", retourVisiteur;
            String visiteur = typeVisiteur(idcourant, idvisite);
 
            Date annee;

            String retourUtilisateur;
            String r1="SELECT * FROM Competences WHERE IdUtilisateur="+idvisite+" AND (VisibleComp='Public' OR VisibleComp ='UtilisateurCo')";
            String r2="SELECT * FROM Diplomes WHERE IdUtilisateur="+idvisite+" AND (VisibleDip='Public' OR VisibleDip ='UtilisateurCo')";
            String r3="SELECT * FROM Utilisateurs WHERE Id="+idvisite+"";
            String r4="SELECT * FROM Competences WHERE IdUtilisateur="+idvisite+"";
            String r5="SELECT * FROM Diplomes WHERE IdUtilisateur="+idvisite+"";
            String r6="SELECT * FROM Competences WHERE IdUtilisateur="+idvisite+" AND VisibleComp='Public'";
            String r7="SELECT * FROM Diplomes WHERE IdUtilisateur="+idvisite+" AND VisibleDip='Public'";
            boolean vr;
            switch (visiteur){
                
                case "Utilisateur":
                        st = co.createStatement();
                        resultatInfo= st.executeQuery(r3);
                    if(visiInfo != "Prive"){
                        
                        while(resultatInfo.next()){
                                nom = resultatInfo.getString("Nom");
                                prenom = resultatInfo.getString("Prenom");
                                mail = resultatInfo.getString("AddrMail");
                                annee = resultatInfo.getDate("AnneeN");
                                tel= resultatInfo.getString("Tel");
                                retourInfo=idvisite + "#" + nom + "#" + prenom + "#" + mail + "#" + tel + "#" + annee + "#" +"END_I#";
                            }
                    }else{
                            while(resultatInfo.next()){
                                nom = resultatInfo.getString("Nom");
                                prenom = resultatInfo.getString("Prenom");
                                mail = resultatInfo.getString("AddrMail");
                                retourInfo=idvisite + "#" + nom + "#" + prenom + "#" + mail + "END_I#";
                            }
                    }

                        
                        resultatComp=st.executeQuery(r1);
                        vr=verifierRequete(r1);
                        if (vr == true){
                            while(resultatComp.next()){
                                if(resultatComp.isLast()){
                                    matiere = resultatComp.getString("Matiere");
                                    niveau = resultatComp.getString("Niveau");
                                    retourComp=retourComp + matiere + "#" + niveau +"#"+"END_C#";
                                    }else{
                                    matiere = resultatComp.getString("Matiere");
                                    niveau = resultatComp.getString("Niveau");
                                    retourComp=retourComp + matiere + "#" + niveau +"$";
                                }
                                    
                            }
                        }else{
                                    retourComp="END_C#";
                                    }
                    
                    

                        resultatDip=st.executeQuery(r2);
                        vr=verifierRequete(r2);
                        if (vr == true){
                            
                        while(resultatDip.next()){
                                if(resultatDip.isLast()){
                                Diplome = resultatDip.getString("Diplome");
                                annee = resultatDip.getDate("AnneeObt");
                                eta = resultatDip.getString("Etablissement");
                                retourDip=retourDip + Diplome + "#" + eta +"#"+ annee +"#END_D";
                                }else{
                                Diplome = resultatDip.getString("Diplome");
                                annee = resultatDip.getDate("AnneeObt");
                                eta = resultatDip.getString("Etablissement");
                                retourDip=retourDip + Diplome + "#" + eta +"#"+ annee + "$" ;
                                
                                }
                            }
                       
                        }else{
                             retourDip="END_D";
                        }
                    
                       
                    retourUtilisateur=retourInfo+retourComp+retourDip;
                    return retourUtilisateur;

                case "Admin":
                case "MonProfil":
                    resultatAdmin=st.executeQuery(r3);
                        while(resultatAdmin.next()){
                                nom = resultatAdmin.getString("Nom");
                                prenom = resultatAdmin.getString("Prenom");
                                mail = resultatAdmin.getString("AddrMail");
                                annee = resultatAdmin.getDate("AnneeN");
                                tel= resultatAdmin.getString("Tel");
                                visibiliter=resultatAdmin.getString("VisibleInf");
                                retourAdmin=idvisite + "#" + nom + "#" + prenom + "#" + mail + "#" + tel + "#" + annee + "#" + visibiliter +"#END_I#";
                        }
                    resultatAdmin=st.executeQuery(r4);
                        while(resultatAdmin.next()){
                                    if(resultatAdmin.isLast()){
                                    matiere = resultatAdmin.getString("Matiere");
                                    niveau = resultatAdmin.getString("Niveau");
                                    visibiliter=resultatAdmin.getString("VisibleComp");
                                    retourAdmin=retourAdmin + matiere + "#" + niveau +"#"+ visibiliter +"#END_C#";
                                    }else{
                                    matiere = resultatAdmin.getString("Matiere");
                                    niveau = resultatAdmin.getString("Niveau");
                                    visibiliter=resultatAdmin.getString("VisibleComp");
                                    retourAdmin=retourAdmin + matiere + "#" + niveau +"#"+visibiliter+"$";
                                }
                                    
                            }
                        resultatAdmin=st.executeQuery(r5);
                        while(resultatAdmin.next()){
                            if(resultatAdmin.isLast()){
                                Diplome = resultatAdmin.getString("Diplome");
                                annee = resultatAdmin.getDate("AnneeObt");
                                eta = resultatAdmin.getString("Etablissement");
                                visibiliter=resultatAdmin.getString("VisibleDip");
                                retourAdmin=retourAdmin + Diplome + "#" + eta +"#"+ annee + "#"+ visibiliter +"#END_D";
                            }else{
                                Diplome = resultatAdmin.getString("Diplome");
                                annee = resultatAdmin.getDate("AnneeObt");
                                eta = resultatAdmin.getString("Etablissement");
                                visibiliter=resultatAdmin.getString("VisibleDip");
                                retourAdmin=retourAdmin +Diplome + "#" + eta +"#"+ annee +"#"+ visibiliter +"$";
                            }
                                
                            }

                        return retourAdmin;
                    
                default:
                        st = co.createStatement();
                        resultatInfo= st.executeQuery(r3);
                    if(visiInfo != "Public"){  
                        while(resultatInfo.next()){
                                nom = resultatInfo.getString("Nom");
                                prenom = resultatInfo.getString("Prenom");
                                retourInfo=idvisite + "#" + nom + "#" + prenom + "#" +"END_I#";
                            
                            }
                    }else{
                            while(resultatInfo.next()){
                                nom = resultatInfo.getString("Nom");
                                prenom = resultatInfo.getString("Prenom");
                                mail = resultatInfo.getString("AddrMail");
                                annee = resultatInfo.getDate("AnneeN");
                                tel= resultatInfo.getString("Tel");
                                retourInfo=idvisite + "#" + nom + "#" + prenom + "#" + mail + "#" + tel + "#" + annee + "#" +"END_I#";
                            }
                    }
                
                        resultatComp=st.executeQuery(r6);
                        vr=verifierRequete(r6);
                        if (vr == true){
                            while(resultatComp.next()){
                                if(resultatComp.isLast()){
                                    matiere = resultatComp.getString("Matiere");
                                    niveau = resultatComp.getString("Niveau");
                                    retourComp=retourComp + matiere + "#" + niveau +"#"+"END_C#";
                                    }else{
                                    matiere = resultatComp.getString("Matiere");
                                    niveau = resultatComp.getString("Niveau");
                                    retourComp=retourComp + matiere + "#" + niveau +"$";
                                }
                                    
                            }
                        }else{
                                    retourComp="END_C#";
                                    }
                
                        resultatDip=st.executeQuery(r7);
                        vr=verifierRequete(r7);
                        if (vr == true){
                            
                        while(resultatDip.next()){
                                Diplome = resultatDip.getString("Diplome");
                                annee = resultatDip.getDate("AnneeObt");
                                eta = resultatDip.getString("Etablissement");
                                retourDip=retourDip + Diplome + "#" + eta +"#"+ annee + "$" ;
                                if(resultatDip.isLast()){
                                Diplome = resultatDip.getString("Diplome");
                                annee = resultatDip.getDate("AnneeObt");
                                eta = resultatDip.getString("Etablissement");
                                retourDip=retourDip + Diplome + "#" + eta +"#"+ annee +"#END_D";
                                }
                            }
                       
                        }else{
                             retourDip="END_D";
                        }
                    retourVisiteur=retourInfo+retourComp+retourDip;
                    return retourVisiteur;
                
            }
            
            }catch (SQLException ex){
            Logger.getLogger(Bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
            return "test";
        }
        
        
       
        public static void main(String[] args){
            String test;
            Bdd bdd=new Bdd();
            bdd.connexion();
                //bdd.verifierMail("grosse@bite.xxx");
                //bdd.creerUtilisateur("Testconnexion", "Thomas", "accbc","1994-12-12", "123456",visibiliter.Prive);
                //bdd.verifierMdp("aajjjjjjjj");
                //bdd.ajouterCompetence(1, "Okok", Bdd.niveau.Bon,Bdd.visibiliter.UtilisateurCo);
                //bdd.ajouterDiplome(2, "1994-12-12" , "BTS Informatique", "bbb" ,visibiliter.Prive);
                //bdd.modifierInformation(6,"Testmodi@trrtr","","fffffffff", Bdd.visibiliter.Public,Bdd.visibiliter.Prive,Bdd.visibiliter.Public);
                //bdd.modifierCompetence(1,"fr", Bdd.niveau.Tresbon, Bdd.visibiliter.Prive);
                //bdd.supprimerCompetence(1, "Rugby");
                //bdd.supprimerDiplome(1,"fr");
                //test=bdd.connexionClient("abc", "123456");
                //test=bdd.visiterProfil(1,1);
                test=bdd.recherche(8, "Testconnexion", "NULL", "NULL", "NULL", niveau.NULL);
                System.out.println(test);
        }
}
