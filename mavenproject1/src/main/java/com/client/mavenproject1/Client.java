/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.mavenproject1;

import java.net.Socket;

/**
 *
 * @author guigui
 */
public class Client {
    private final Socket port;
    
    public Client(Socket port){
        this.port=port;
    }
    
    private void emission(){}
    
    private void reception(){}
    
    /**
     * afficher le menu des choix possibles au client
     */
    private void menu(){
        System.out.println("***menu***");
        System.out.println("1) Creation du compte"
                + "2) modifier informations"
                +"0) quitter") ;
    }
    /**
     * connexion au serveur
     */
    private void connexion(){}
    
    
}
