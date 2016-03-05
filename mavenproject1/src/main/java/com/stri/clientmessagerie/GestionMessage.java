
package com.stri.clientmessagerie;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTabbedPane;

import javax.swing.text.BadLocationException;


    
/**
 *
 * @author AFBD
 */
public class GestionMessage extends Thread{
    private FenetreMessagerie frame;
    private Hashtable<Integer, String> message;
    private Hashtable<Integer, String> listeUsers;
    
    public GestionMessage(FenetreMessagerie frame, Hashtable message){
        this.frame = frame;
        this.message = message;
        this.listeUsers = frame.getListeUsers();
    }

    private void construireMessage(String [] decoupage){
        String titleTab = listeUsers.get(Integer.valueOf(decoupage[0]));
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
            frame.construireTab(titleTab);
            try {                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GestionMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                frame.afficherTexte(titleTab.split(" ")[0], texte);
            } catch (BadLocationException ex) {
                Logger.getLogger(GestionMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void run(){
        while(true){
            if(!message.isEmpty()){
                System.out.println("La table contient :" + message.get(0));
                String[] decoupage = message.get(0).split("#");
                if(decoupage[0].equals("AJOUTCLIENT")){
                    JList list = frame.getjList1();
                    DefaultListModel DLM = (DefaultListModel) list.getModel();
                    DLM.addElement(decoupage[3]+ " " + decoupage[2]);
                    if(DLM.get(0).equals("Aucuns contacts connectés")){
                        DLM.remove(0);
                    }
                    list.setModel(DLM);
                    frame.setjList1(list);
                    Hashtable <Integer, String> listeContact = frame.getListeContact();
                    listeContact.put(Integer.valueOf(decoupage[1]), decoupage[3] + "#" + decoupage[2] + "#" + decoupage[4] + "#" + decoupage[5]);
                    frame.setListeContact(listeContact);
                }
                else if (decoupage[0].equals("SUPPCLIENT")){
                    Hashtable <Integer, String> listeContact = frame.getListeContact();
                    for(Integer id : listeContact.keySet()){
                        System.out.println("id : " + id);
                        System.out.println("Get : " + listeContact.get(id));
                    }
                    System.out.println(decoupage[1]);
                    System.out.println(listeContact.get(2));
                    String sup = listeContact.get(Integer.valueOf(decoupage[1]));
                    listeContact.remove(decoupage[1]);
                    System.out.println("Suppression de :" + sup);
                    frame.setListeContact(listeContact);
                    JList list = frame.getjList1();
                    DefaultListModel DLM = (DefaultListModel) list.getModel();
                    for (int i = 0; i < DLM.getSize(); i++) {
                        String model = (String) DLM.get(i);
                        if (model.contains(sup.split("#")[0])) {
                            DLM.remove(i);
                        }
                    }
                    if(DLM.getSize() == 0){
                        DLM.addElement("Aucuns contacts connectés");
                    }
                    list.setModel(DLM);
                    frame.setjList1(list);
                    
                }
                else {
                    construireMessage(decoupage);
                }
                message.remove(0);

            }
        }
    }
}
