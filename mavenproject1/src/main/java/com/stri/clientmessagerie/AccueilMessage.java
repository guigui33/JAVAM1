/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stri.clientmessagerie;

import com.strim1.mavenproject1.ServicePostalUDP;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author AFBD
 */
public class AccueilMessage extends Thread{

    private ServicePostalUDP sp;
    
    public AccueilMessage(int port, ServicePostalUDP sp){
        this.sp = sp;
    }
    public void run() {
        while(true){
            System.out.println(sp.recevoir());
        }

    }
}
