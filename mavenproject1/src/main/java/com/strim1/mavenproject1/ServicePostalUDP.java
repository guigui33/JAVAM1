/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.strim1.mavenproject1;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guigui
 */
public class ServicePostalUDP {
    private DatagramSocket datagramme;
    
    public ServicePostalUDP(int port){
        try {
            datagramme=new DatagramSocket(port);
        } catch (SocketException ex) {
            Logger.getLogger(ServicePostalUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void envoyer(String msg,String addrDest,int portDest){
        
        try {
            InetAddress destAddr=InetAddress.getByName(addrDest);
            
            ByteArrayOutputStream byteSortant=new ByteArrayOutputStream();
            
            DataOutputStream donneeSortante=new DataOutputStream(byteSortant);
            donneeSortante.writeUTF(msg);
            
            byte[] tablDonneeSortante=byteSortant.toByteArray();
            
            DatagramPacket paquetSortant= new DatagramPacket(tablDonneeSortante,tablDonneeSortante.length,destAddr,portDest);
            
            System.out.println("emission: "+msg);
            datagramme.send(paquetSortant);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServicePostalUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServicePostalUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String recevoir(){
        try {
            byte[] tabDonneeEntrante = new byte[1024];
            DatagramPacket paquetEntrant=new DatagramPacket(tabDonneeEntrante,tabDonneeEntrante.length);
            datagramme.receive(paquetEntrant);
            
            ByteArrayInputStream byteEntrante=new ByteInputStream(tabDonneeEntrante,0,paquetEntrant.getLength());
            DataInputStream donneeEntrante= new DataInputStream(byteEntrante);
            
            String msg=donneeEntrante.readUTF();
            
            return msg;
        } catch (IOException ex) {
            Logger.getLogger(ServicePostalUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }
}
