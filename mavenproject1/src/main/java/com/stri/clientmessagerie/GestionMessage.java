/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stri.clientmessagerie;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;

    
/**
 *
 * @author AFBD
 */
public class GestionMessage extends Thread{
    private FenetreMessagerie frame;
    private Hashtable<Integer, String> message;
    
    public GestionMessage(FenetreMessagerie frame, Hashtable message){
        this.frame = frame;
        this.message = message;
    }
    
    
    public void run(){
        while(true){
            if(!message.isEmpty()){
                System.out.println("La table contient :" + message.get(0));
                String[] decoupage = message.get(0).split("#");
                String titleTab = decoupage[0];
                String texte = decoupage[1];
                int nbTab;
                Boolean tabActif = false;
                JTabbedPane tab = frame.getjTabbedPane1();
                nbTab = tab.getTabCount();
                for (int i = 0; i < nbTab; i++) {
                    if(tab.getTitleAt(i).equals(titleTab)){
                        try {
                            frame.afficherTexte(titleTab.split(" ")[0], texte);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(GestionMessage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        tab.setSelectedIndex(i);
                        tabActif = true;
                    }
                }
                if(!tabActif){
                    frame.construireTab();
                    try {
                        frame.afficherTexte(titleTab.split(" ")[0], texte);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(GestionMessage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                message.remove(0);

            }
        }
        /*        JTabbedPane tab = frame.getjTabbedPane1();
            int numTab = tab.getSelectedIndex();
            System.out.println("Je suis la nouvelle classe :" +numTab);
            tab.setSelectedIndex(1);*/
    }
}
