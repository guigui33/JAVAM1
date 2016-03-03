
package com.stri.clientmessagerie;

import com.client.mavenproject1.Client;
import com.strim1.mavenproject1.ServicePostal;
import com.strim1.mavenproject1.ServicePostalUDP;
import  javax.swing.*;
import  java.awt.*;
import  java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;


public class FenetreMessagerie extends javax.swing.JFrame{

    private final Socket s;
    private Integer id_user;
    private Integer num_session;
    private Hashtable<Integer, String> listeUsers = new Hashtable<>();
    private Hashtable<Integer, String> listeMessage = new Hashtable<>();
    private Hashtable<Integer, String> listeContact = new Hashtable<>();
    private ServicePostalUDP sp1;

    /**
     * Creates new form NewJFrame
     */
    public FenetreMessagerie(Client c, ServicePostalUDP sp) throws IOException {
        this.sp1 = sp;
        this.s = new Socket("127.0.0.1", 50006);
        this.id_user = c.getId_user();
        this.num_session = c.getNum_session();
        new ServicePostal(s).emission("HELLO#" + id_user + "#" + num_session + "#127.0.0.1#" + sp1.getPort());
        new ServicePostal(s).reception();
        initComponents();
        new ServicePostal(s).emission("UTILISATEURS#" + id_user);
        construireListeAllUsers(new ServicePostal(s).reception());
        new ServicePostal(s).emission("LISTEMSG#" + id_user);
        construireListeMessage(new ServicePostal(s).reception());
        new ServicePostal(s).emission("CONTACT#" + id_user);
        construireListeConnecte(new ServicePostal(s).reception());
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame2 = new javax.swing.JFrame();
        jLabel2 = new javax.swing.JLabel();
        jLabel_Emetteur = new javax.swing.JLabel();
        jLabel_Objet = new javax.swing.JLabel();
        jLabel_message = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jFrame1 = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton_Contacter = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jFrame2.setBounds(new java.awt.Rectangle(0, 0, 642, 600));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Message");

        jLabel_Emetteur.setText("Emetteur :");

        jLabel_Objet.setText("Objet :");

        jLabel_message.setText("jLabel5");
        jLabel_message.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel9.setText("Message :");

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addGap(332, 332, 332)
                        .addComponent(jLabel2))
                    .addGroup(jFrame2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_Objet)
                            .addComponent(jLabel_Emetteur)
                            .addComponent(jLabel_message, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(52, 52, 52)
                .addComponent(jLabel_Emetteur)
                .addGap(38, 38, 38)
                .addComponent(jLabel_Objet)
                .addGap(32, 32, 32)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_message, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jFrame1.setTitle("Envoyer un message");
        jFrame1.setBounds(new java.awt.Rectangle(0, 0, 642, 600));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Envoyer un Message");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jButton3.setText("Envoyer !");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setText("Destinataire :");

        jLabel7.setText("Objet du message :");

        jLabel8.setText("Message :");

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(108, 108, 108))
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(jLabel1))
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)))
                    .addGroup(jFrame1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboBox1.getAccessibleContext().setAccessibleParent(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Application messagerie");
        setResizable(false);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jButton_Contacter.setText("Contacter");
        jButton_Contacter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ContacterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 837, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_Contacter)
                .addGap(209, 209, 209))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_Contacter)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Liste Connectés", jPanel1);

        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jButton1.setText("Lire ce message");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Envoyer un message");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 812, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(102, 102, 102))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(36, 36, 36))
        );

        jTabbedPane1.addTab("Messages Reçus", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Tab1");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_ContacterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ContacterActionPerformed
        // TODO add your handling code here:
        int nbTab;
        Boolean tabActif = false;
        nbTab = jTabbedPane1.getTabCount();
        for (int i = 0; i < nbTab; i++) {
            if(jTabbedPane1.getTitleAt(i).equals(jList1.getSelectedValue())){
                jTabbedPane1.setSelectedIndex(i);
                tabActif = true;
            }
        }
        if(!tabActif){         
           construireTab(jList1.getSelectedValue());
        }
    }//GEN-LAST:event_jButton_ContacterActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        jFrame1.setVisible(true);
        jFrame1.setLocationRelativeTo(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private Integer getId_message(){
        String x = jList2.getSelectedValue();
        for(Integer i : listeMessage.keySet()){
            if(listeMessage.get(i).equals(x))
                return i;
        }
        return -1;
    }       
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Integer id_message = getId_message();
        new ServicePostal(s).emission("LIRE#" +id_user + "#"+ id_message);
        String message = new ServicePostal(s).reception();
        System.out.println(message);
        
        String [] decoupage = message.split("#");
        jLabel_Emetteur.setText("Emetteur : " + decoupage[1] + " " + decoupage[0]);
        jLabel_Objet.setText("Objet : " + decoupage[2]);
        jLabel_message.setText(decoupage[3]);
        jFrame2.setVisible(true);
        jFrame2.setLocationRelativeTo(null);
        
             
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Integer id_recepteur = getId_recepteur();
        String Objet = jTextField1.getText();
        String Message = jTextArea1.getText();
        new ServicePostal(s).emission("POSTER#" + id_user + "#" + id_recepteur + "#" + Objet + "#" +Message);
        new ServicePostal(s).reception();
        jTextField1.setText("");
        jTextArea1.setText("");
        jFrame1.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private Integer getId_recepteur(){
        String x = String.valueOf(jComboBox1.getSelectedItem());
        for(Integer i : listeUsers.keySet()){
            if(listeUsers.get(i).equals(x))
                return i;
        }
        return -1;
    }

    public Hashtable<Integer, String> getListeUsers() {
        return listeUsers;
    }
    
    private void construireListeConnecte(String s){
        if(s.contains("ERROR"))
        {
            DefaultListModel DLM = new DefaultListModel();
            DLM.addElement(s.split("#")[1]);
            jList1.setModel(DLM);
        }
        else {
            if(!s.equals("LISTE")){
                String [] decoupage;
                String [] decoupageContact;
                decoupage = s.split("\\$");
                for (int i = 1; i < decoupage.length; i++) {
                    decoupageContact = decoupage[i].split("#");
                    listeContact.put(Integer.valueOf(decoupageContact[0]), decoupageContact[2] + "#" + decoupageContact[1] + "#" + decoupageContact[3] + "#" + decoupageContact[4]);
                }
                DefaultListModel DLM = new DefaultListModel();
                for (int i : listeContact.keySet()) {
                    String [] nomprenom = listeContact.get(i).split("#");
                    DLM.addElement(nomprenom[0] + " " + nomprenom[1]);
                }
                jList1.setModel(DLM);
            }
            else{
                DefaultListModel DLM = new DefaultListModel();
                DLM.addElement("Aucuns contacts connectés");
                jList1.setModel(DLM);
            }
        }
    }
    private void construireListeMessage(String s){
        String [] decoupage;
        String [] decoupageMsg;
        decoupage = s.split("\\$"); 
        for (int i = 0; i < decoupage.length; i++) {
            decoupageMsg = decoupage[i].split("#");
            listeMessage.put(Integer.valueOf(decoupageMsg[0]), decoupageMsg[2] + " " + decoupageMsg[1]+ " " + " : " + decoupageMsg[3]);
        }
        DefaultListModel DLM = new DefaultListModel();
        for (int i : listeMessage.keySet()) {
            DLM.addElement(listeMessage.get(i));
        }
        jList2.setModel(DLM);
    }
    private void construireListeAllUsers(String s){
        String [] decoupage;
        String [] decoupageUsers;
        decoupage = s.split("\\$");
        for (int i = 0; i < decoupage.length; i++) {
            decoupageUsers = decoupage[i].split("#");
            listeUsers.put(Integer.valueOf(decoupageUsers[0]), decoupageUsers[2] + " " + decoupageUsers[1]);
        }
        for(Integer id : listeUsers.keySet()){
            jComboBox1.addItem(listeUsers.get(id));
        }
        
    } 

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }

    public JList<String> getjList1() {
        return jList1;
    }

    public void setjTabbedPane1(JTabbedPane jTabbedPane1) {
        this.jTabbedPane1 = jTabbedPane1;
    }

    
    public void setjList1(JList<String> jList1) {
        this.jList1 = jList1;
    }

    public Hashtable<Integer, String> getListeContact() {
        return listeContact;
    }

    public void setListeContact(Hashtable<Integer, String> listeContact) {
        this.listeContact = listeContact;
    }

    public Integer getId_user() {
        return id_user;
    }

    public ServicePostalUDP getSp1() {
        return sp1;
    }

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton_Contacter;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Emetteur;
    private javax.swing.JLabel jLabel_Objet;
    private javax.swing.JLabel jLabel_message;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables


        private void fermerMouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
            int numTab = jTabbedPane1.getSelectedIndex();
            jTabbedPane1.remove(numTab);
            jTabbedPane1.setSelectedIndex(0);
    }                                    

    public void construireTab(String titleTab){
        JPanel Jpanel = new javax.swing.JPanel();
        Jpanel.setLayout(null);
        JButton bouton = new JButton("Envoyer !");
        JEditorPane jEditor = new JEditorPane();
        jEditor.setFocusable(false);
        jEditor.setBounds(20, 20, 810, 235);
        jEditor.setContentType("text/html");
        jEditor.setEditorKit(new HTMLEditorKit());
        jEditor.setDocument(new HTMLDocument());
        jEditor.setText("");
        JTextArea jTextArea_texte = new JTextArea();
        jTextArea_texte.setBounds(20, 260, 650, 130);        
        bouton.addActionListener(new TraitementEnvoyer(sp1,id_user, listeContact, titleTab ) );        
        bouton.setBounds(680, 300, 150, 50);
        JLabel fermer = new JLabel("<html><u><i>Fermer cette conversation</i></u></html>");
        fermer.setBounds(685, 355, 150, 50);
        fermer.setForeground(new java.awt.Color(0, 102, 255));
        fermer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); 
        fermer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fermerMouseClicked(evt);
            }
        });
         Jpanel.add(bouton);
        Jpanel.add(jTextArea_texte);
        Jpanel.add(jEditor);
        Jpanel.add(fermer);
        jTabbedPane1.addTab(titleTab, Jpanel); 
        jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount()-1);
        jTabbedPane1.updateUI();     
    }
    
        public void afficherTexte(String nom, String texte) throws BadLocationException{
            int numTab = jTabbedPane1.getSelectedIndex();
            Component com = jTabbedPane1.getComponentAt(numTab);
            JEditorPane j = (JEditorPane) com.getComponentAt(23, 21);
            try {
                HTMLEditorKit kit = (HTMLEditorKit) j.getEditorKit();
                HTMLDocument doc = (HTMLDocument) j.getDocument();
                kit.insertHTML(doc, doc.getLength(), "<b> " + nom + " </b> : " + texte + " <br>", 0, 0, null);
            } catch (BadLocationException | IOException ex) {
                Logger.getLogger(FenetreMessagerie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
public  class   TraitementEnvoyer implements   ActionListener
    {
         /**
         * obligatoire car test implémente l'interface ActionListener
         */
        ServicePostalUDP sp;
        Integer id_user;
        private Hashtable<Integer, String> listeContact = new Hashtable<>();
        String titleTab;
        int port;
        String ip;

        public TraitementEnvoyer(ServicePostalUDP sp, Integer id_user, Hashtable listeContact, String titleTab) {
            this.sp = sp;
            this.id_user = id_user;
            this.listeContact = listeContact;
            this.titleTab = titleTab;
        }
        
        public  void    actionPerformed(ActionEvent e)
        {
            String texte = recupererTexte();
            try {
                for(int id : listeContact.keySet()){
                    if(listeContact.get(id).contains(titleTab.split(" ")[0])){
                        port = Integer.valueOf(listeContact.get(id).split("#")[3]);
                        ip = listeContact.get(id).split("#")[2];
                    }
                }
                afficherTexte("Moi", texte);
                sp.envoyer(id_user + "#" + texte, ip, port);
            } catch (BadLocationException ex) {
                Logger.getLogger(FenetreMessagerie.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        private String recupererTexte(){
            int numTab = jTabbedPane1.getSelectedIndex();
            Component com = jTabbedPane1.getComponentAt(numTab);
            JTextArea j = (JTextArea) com.getComponentAt(23, 261);
            String s = j.getText();  
            j.setText("");
            return s;
        }
        


    }
}




