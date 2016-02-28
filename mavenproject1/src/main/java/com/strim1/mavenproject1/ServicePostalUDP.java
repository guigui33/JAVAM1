package com.strim1.mavenproject1;

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
 *permet de gerer l'emission, la reception en UDP
 */
public class ServicePostalUDP {
    /**
     * Le datagramme UDP
     */
    private DatagramSocket datagramme;
    
    /**
     * Construteur, qui definit le datagramSocket à partir d'un port déterminé
     * @param port le port  
     */
    public ServicePostalUDP(int port){
        boolean trouve = false;
        while (!trouve) {
            try {
                datagramme = new DatagramSocket(port);
                trouve = true;
                System.out.println("port " + port + " disponible.");
            } catch (SocketException ex) {
                System.out.println("port " + port + " utilisé.");
                port += 1;
            }
        }
    }
    
    /**
     * envoit d'un message en fonction d'un ip et d'un port
     * @param msg le message à envoyer 
     * @param addrDest l'adresse du destinataire
     * @param portDest  le prot du destinataire
     */
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
    
    /**
     * la reception d'un message udp 
     * @return le message réçu
     */
    public String recevoir(){
        try {
            byte[] tabDonneeEntrante = new byte[1024];
            DatagramPacket paquetEntrant=new DatagramPacket(tabDonneeEntrante,tabDonneeEntrante.length);
            datagramme.receive(paquetEntrant);
            
            ByteArrayInputStream byteEntrante=new ByteArrayInputStream(tabDonneeEntrante,0,paquetEntrant.getLength());
            DataInputStream donneeEntrante= new DataInputStream(byteEntrante);
            
            String msg=donneeEntrante.readUTF();
            
            return msg;
        } catch (IOException ex) {
            Logger.getLogger(ServicePostalUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }
}
