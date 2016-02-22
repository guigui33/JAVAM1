
package com.strim1.mavenproject1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *permet de gerer l'emission, la reception et la deconnexion à travers un socket
 */
public class ServicePostal {
    /**
     * le socket de service entre deux entités
     */
    private final Socket connexionCourante;
    /**
     * le constructeur qui definit le socket de service
     * @param socket le socket de service
     */
    public ServicePostal(Socket socket){
        this.connexionCourante=socket;
    }
    /**
     * receptionne une demande
     * @return la demande reçue
     */
    public String reception(){
        String msg=null;
        InputStreamReader fluxEntree=null;
        try {
            fluxEntree = new InputStreamReader(connexionCourante.getInputStream());
            BufferedReader lecture=new BufferedReader(fluxEntree);
            
            try {
                msg=lecture.readLine();
                System.out.println("reception: "+msg);
            } catch (IOException ex) {
                Logger.getLogger(ServicePostal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServicePostal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg;
    }
    /**
     * emet une demande vers une entité
     * @param msg le message à émettre
     */
    public void emission(String msg){
        PrintStream fluxSortie;
        try {
            fluxSortie = new PrintStream(connexionCourante.getOutputStream());
            System.out.println("emission: "+msg);
            fluxSortie.println(msg);
            
        } catch (IOException ex) {
            Logger.getLogger(ServicePostal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * methode permettant de fermer le socket de service entre le client et le serveur
     */
    public void deconnexion() {
        System.err.println("deconnexion Serveur : " + connexionCourante);
        try {
            connexionCourante.close();
        } catch (IOException ex) {
            Logger.getLogger(ServicePostal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
