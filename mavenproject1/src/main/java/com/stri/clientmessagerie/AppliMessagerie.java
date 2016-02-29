/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stri.clientmessagerie;

import com.client.mavenproject1.Client;
import com.strim1.mavenproject1.ServicePostalUDP;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;

/**
 *
 * @author AFBD
 */
public class AppliMessagerie {
    
    private static ServicePostalUDP sp = new ServicePostalUDP(50000);
    private static final Hashtable<Integer, String> message = new Hashtable<>();
    private static FenetreMessagerie frame;

    
    public AppliMessagerie(final Client c) throws IOException {
        String[] args = null;
        main(args, c);
    }
    
    
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[], final Client c) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FenetreMessagerie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FenetreMessagerie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FenetreMessagerie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FenetreMessagerie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        new AccueilMessage(sp, message).start();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
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


        
        //System.out.println("Salut je suis le main");
    }


}
