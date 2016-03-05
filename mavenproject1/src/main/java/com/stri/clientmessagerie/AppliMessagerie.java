
package com.stri.clientmessagerie;

import com.client.mavenproject1.Client;
import com.strim1.mavenproject1.ServicePostalUDP;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AppliMessagerie extends Thread {
    
    private final ServicePostalUDP sp = new ServicePostalUDP(50000);
    private final Hashtable<Integer, String> message = new Hashtable<>();
    private FenetreMessagerie frame;
    private final Client c;
    
    public AppliMessagerie(Client c){
        this.c=c;
    }
    
    @Override
    public void run() {
        new AccueilMessage(sp, message).start();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    frame = new FenetreMessagerie(c, sp);
                } catch (IOException ex) {
                    Logger.getLogger(AppliMessagerie.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                frame.setVisible(true);
                new GestionMessage(frame, message).start();
            }
        });
    }


}
