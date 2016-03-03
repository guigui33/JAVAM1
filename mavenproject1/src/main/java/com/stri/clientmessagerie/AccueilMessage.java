package com.stri.clientmessagerie;

import com.strim1.mavenproject1.ServicePostalUDP;
import java.util.Hashtable;


public class AccueilMessage extends Thread {

    private ServicePostalUDP sp;
    private Hashtable<Integer, String> message;

    public AccueilMessage(ServicePostalUDP sp, Hashtable message) {
        this.sp = sp;
        this.message = message;
    }

    @Override
    public void run() {

        while (true) {
            String texte = sp.recevoir();
            System.out.println("J'ai reçu : " + texte);
            message.put(0, texte);
        }

    }
}
