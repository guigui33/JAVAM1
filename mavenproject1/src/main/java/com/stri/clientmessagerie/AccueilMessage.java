/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stri.clientmessagerie;

import com.strim1.mavenproject1.ServicePostalUDP;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Hashtable;

/**
 *
 * @author AFBD
 */
public class AccueilMessage extends Thread{

    private ServicePostalUDP sp;
    private Hashtable<Integer, String> message;
    
    public AccueilMessage(ServicePostalUDP sp, Hashtable message){
        this.sp = sp;
        this.message = message;
    }
    public void run() {
        while(true){
            String texte = sp.recevoir();
            System.out.println("J'ai reçu : " + texte);
            message.put(0, texte);
        }

    }
}
