package com.coded.jmail;

/*
 * Copyright (C) 2006 Hassen Ben Tanfous
 * All right reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 	1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 	2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 	3. Neither the name of the Hassen Ben Tanfous nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * JMail.java
 * JMail permet de
 * 1) Envoyer des emails
 * 2) Envoyer des emails avec de fausses adresses d'envoyeurs
 * 3) Écrire des emails en format HTML
 * 4) Gérer les multiples contacts que vous avez
 * 5) Attacher des fichiers illimités, de nombre et de taille illimités
 * 6) Se connecter à un serveur SMTP sécure (SSL, TLS)
 * 7) Attacher des URL et leur contenu
 * 8) Attacher des URL et leur contenu DANS le message
 * 9) Je travaille sur d'autres possibilités ;p
 *
 * Merci d'envoyer tout commentaire ou bug à l'adresse suivante:
 * Date: 2005-07-12
 * @author Hassen Ben Tanfous
 * @version 1.0
 */

//imports
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import org.apache.commons.mail.*;
import com.coded.jmail.msg.*;

public class JMail {
    public static final String GMAIL_PORT = "465";
    public static final String GMAIL_SMTP_SERVER = "smtp.gmail.com";

    private JMenuBar menuBar;
    private JMenu
            menuLogin,
            menuOptions,
            menuOptionsFichier,
            menuOptionsContact,
            menuEdit;
    private JMenuItem
            itemNormal, //login
            itemSSL, //login
            itemFichierInter, //options Fichier (Interface)
            itemURL,
            itemAjouter, //options Contact
            itemRetirer, //options Contact
            itemImporter, //options Contact
            itemExporter, //options Contact
            itemCopier, //edit
            itemColler, //edit
            itemCouper, //edit
            itemRechercher; //edit
    private JCheckBoxMenuItem itemCheckRecvName;

    private JTextField
            txtTo,
            txtBcc,
            txtCc,
            txtSubject,
            txtServer,
            txtFrom,
            txtNom,
            txtReplyNom,
            txtReplyEmail,
            txtUsername,
            txtPort,
            txtContactNom,
            txtContactEmail,
            txtContactPseudo,
            txtUrlPath,
            txtUrlNom,
            txtUrlDesp,
            txtUrl2Path,
            txtUrl2Nom,
            txtUrl2Width,
            txtUrl2Height,
            txtFichierPath,
            txtFichierNom,
            txtFichierDescription;

    private JPasswordField passwd;
    private JEditorPane editor;
    private JScrollPane
            scrollEditor,
            scrollListe;
    private JButton boutonText,
            boutonHtml,
            boutonUrlInter, //interface
            boutonUrlMan, //manuel
            //partie Contact
            boutonAjouter,
            boutonRetirer,
            boutonImporter,
            boutonExporter,
            boutonConnecterNormal,
            boutonConnecterSSL,
            boutonSend,
            boutonContactAjouter,
            boutonAddTo,
            boutonBccTo,
            boutonCcTo,
            boutonUrlAjouter,
            boutonUrl2Ajouter,
            boutonFichierAttacher;

    private JList liste;
    private DefaultListModel
            modeleListeNom,
            modeleListeEmail,
            modeleListePseudo;

    private JLabel
            lblOrganisations,
            lblTo,
            lblBcc,
            lblCc,
            lblSubject,
            lblListe,
            lblServer,
            lblPort,
            lblUsername,
            lblFrom,
            lblNom,
            lblReplyName,
            lblReplyEmail,
            lblPasswd,
            lblContactNom,
            lblContactEmail,
            lblContactPseudo,
            lblUrlNom,
            lblUrlPath,
            lblUrlDescription,
            lblUrl2Path,
            lblUrl2Nom,
            lblUrl2Width,
            lblUrl2Height,
            lblFichiersPath,
            lblFichiersNom,
            lblFichiersDescription;

    private JComboBox
            comboUrl,
            comboFichiers;
    private JRadioButton
            radioNom,
            radioEmail,
            radioPseudo;
    private Container
            cp,
            cLoginNormal,
            cLoginSSL,
            cContactsAjouter,
            cURL,
            cUrl2,
            cFichiers;

    private JFrame
            frame,
            frameLoginNormal,
            frameLoginSSL,
            frameContactsAjouter,
            frameURL,
            frameUrl2,
            frameFichiers;

    private JFileChooser
            jfcImporter,
            jfcExporter,
            jfcUrl,
            jfcFichiers;

    private Login login;
    private boolean boolSSL;
    private HtmlEmail email;
    private String urlDisposition;
    private File[] tabFichiers;
    private EmailAttachment[] tabAttachements;
    private int nbFichiers;

    private ListeContacts listeContacts;

    private Editeur editeur;

    private Messagerie msg;

    public JMail() {
        msg.titre = "JMail par Hassen Ben Tanfous";
        instancierComposants();
        configurerComposants();
    }

    private void configurerComposants() {
        txtServer.setText(GMAIL_SMTP_SERVER);
        txtPort.setText(GMAIL_PORT);

        //Modèle Container
        cp.setLayout(null);

        //Modèle JTextField
        txtUrlPath.setToolTipText("L'emplacement du lien URL");
        txtUrlNom.setToolTipText("Le nom qui doit apparaître dans le message");
        txtUrlDesp.setToolTipText("Description du lien");
        txtFichierPath.setEditable(false);

        //Modèle JComboBox
        comboUrl.setToolTipText("Inline: contenu du message"
                                + " Mixed lien vers le contenu");
        comboUrl.addActionListener(alOptions);
        comboFichiers.addActionListener(alMenuFichiers);

        //Modèle JEditorPane
        editor.setContentType("text/plain");

        //Modèle JFileChooser
        jfcImporter.setApproveButtonText("Importer");
        jfcImporter.setMultiSelectionEnabled(false);
        jfcImporter.setDialogTitle("Importer la liste de contacts");

        jfcExporter.setApproveButtonText("Exporter");
        jfcExporter.setMultiSelectionEnabled(false);
        jfcExporter.setDialogTitle("Exporter la liste de contacts");

        jfcUrl.setApproveButtonText("Ajouter");
        jfcUrl.setApproveButtonToolTipText("Ajoute un URL au text");
        jfcUrl.setMultiSelectionEnabled(false);
        jfcUrl.setDialogTitle("Ajouter un URL");

        jfcFichiers.setDialogTitle("Ajouter des attachments");
        jfcFichiers.setApproveButtonText("Attacher");
        jfcFichiers.setApproveButtonToolTipText("Attache des fichiers");
        jfcFichiers.setMultiSelectionEnabled(true);

        //Modèle item
        itemCheckRecvName.setToolTipText("Spécifie le nom du receveur"
                                         + " avec son email");

        //radio
        radioNom.setSelected(true);

        //Modèle bouton
        boutonHtml.setToolTipText("Convertit de HTML vers Text");
        boutonText.setToolTipText("Convertit de Text vers HTML");
        boutonUrlInter.setToolTipText("Ajouter un URL via interface");
        boutonUrlMan.setToolTipText("Ajouter un URL via manuel");
        boutonSend.setBackground(Color.RED);

        //écouteurs boutons
        boutonText.addActionListener(alEditor);
        boutonHtml.addActionListener(alEditor);
        boutonAjouter.addActionListener(alMenuContact);
        boutonRetirer.addActionListener(alMenuContact);
        boutonImporter.addActionListener(alMenuContact);
        boutonExporter.addActionListener(alMenuContact);
        boutonSend.addActionListener(alSend);
        boutonUrlInter.addActionListener(alEditor);
        boutonUrlMan.addActionListener(alEditor);
        boutonFichierAttacher.addActionListener(alMenuFichiers);

        boutonFichierAttacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == boutonFichierAttacher) {
                    tabAttachements[nbFichiers - 1] = new EmailAttachment();

                    if (txtFichierNom.getText().equals("")) {
                        txtFichierNom.setText(tabFichiers[nbFichiers -
                                              1].getName());
                    } else if (comboFichiers.getSelectedIndex() == 0) {
                        tabAttachements[nbFichiers - 1].setDisposition("inline");
                    } else if (comboFichiers.getSelectedIndex() == 1) {
                        tabAttachements[nbFichiers - 1].setDisposition("mixed");
                    }
                    if (txtFichierDescription.getText().equals("")) {
                        txtFichierDescription.setText(tabFichiers[nbFichiers -
                                1].getName());
                    }
                    if (nbFichiers - 1 >= 0) {
                        tabAttachements[nbFichiers -
                                1].setPath(tabFichiers[nbFichiers -
                                           1].getAbsolutePath());
                        tabAttachements[nbFichiers -
                                1].setName(txtFichierNom.getText());
                        tabAttachements[nbFichiers -
                                1].setDescription(txtFichierDescription.getText());

                        nbFichiers--;
                        if (nbFichiers == 0) {
                            frameFichiers.setVisible(false);
                            return;
                        }
                        txtFichierPath.setText(tabFichiers[nbFichiers -
                                               1].getAbsolutePath());
                        txtFichierNom.setText(tabFichiers[nbFichiers -
                                              1].getName());
                        txtFichierDescription.setText("");
                    }
                }
            }
        });

        boutonUrl2Ajouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtUrl2Path.getText().equals("")) {
                    msg.msge("Vous devez entrer un chemin pour le URL");
                } else if (txtUrl2Nom.getText().equals("")) {
                    msg.msge("Vous devez entrer un nom pour le URL");
                } else if (txtUrl2Width.getText().equals("")) {
                    txtUrl2Width.setText("100");
                } else if (txtUrl2Height.getText().equals("")) {
                    txtUrl2Height.setText("100");
                } else {
                    try {
                        editor.setText(editor.getText() +
                                       "<img src=cid:" +
                                       email.embed(new URL(txtUrl2Path.
                                getText()), txtUrl2Nom.getText())
                                       + " width=" + txtUrl2Width.getText() +
                                       " height=" + txtUrl2Height.getText() +
                                       ">");
                        email.setHtmlMsg(editor.getText());

                    } catch (MalformedURLException ex) {
                    } catch (EmailException ex) {
                    }
                    frameUrl2.setVisible(false);
                }

            }
        });

        boutonUrlAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == boutonUrlAjouter) {
                    if (txtUrlPath.getText().equals("")) {
                        msg.msge("Vous devez entrer un chemin pour le URL");
                    } else if (txtUrlNom.getText().equals("")) {
                        msg.msge("Vous devez entrer un nom pour le URL");
                    } else if (txtUrlDesp.getText().equals("")) {
                        msg.msge(
                                "Vous devez entrer une description pour le URL");
                    } else {
                        try {
                            email.attach(new URL(txtUrlPath.getText()),
                                         txtUrlNom.getText(),
                                         txtUrlDesp.getText(), urlDisposition);
                        } catch (MalformedURLException ex) {
                        } catch (EmailException ex) {
                        }
                        frameURL.setVisible(false);
                    }
                }
            }
        });

        boutonContactAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == boutonContactAjouter) {
                    if (txtContactNom.getText().equals("")) {
                        txtContactNom.setText("aucun");
                    } else if (txtContactEmail.getText().equals("")) {
                        txtContactEmail.setText("aucun");
                    } else if (txtContactPseudo.getText().equals("")) {
                        txtContactPseudo.setText("aucun");
                    }
                    listeContacts.ajouter(txtContactNom.getText(),
                                          txtContactEmail.getText(),
                                          txtContactPseudo.getText());
                    modeleListeNom.addElement(txtContactNom.getText());
                    modeleListeEmail.addElement(txtContactEmail.getText());
                    modeleListePseudo.addElement(txtContactPseudo.getText());
                    frameContactsAjouter.setVisible(false);
                }
            }
        });

        boutonConnecterNormal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == boutonConnecterNormal) {
                    if (txtServer.getText().equals("")) {
                        msg.msge("Vous devez entrer un nom de serveur");
                    } else if (Integer.parseInt(txtPort.getText()) <= 0) {
                        msg.msge(
                                "Vous devez entrer un port valide\npar défaut SMTP:25");
                    } else {
                        try {
                            Socket socket = new Socket(txtServer.getText(),
                                    Integer.parseInt(txtPort.getText()));
                            if (socket.isConnected()) {
                                msg.msgi("Connecté au server " +
                                         txtServer.getText() +
                                         "\nvia port " + txtPort.getText());
                                socket.close();
                            }
                        } catch (NumberFormatException ex) {
                        } catch (UnknownHostException ex) {
                            msg.msge("Impossible de se connecter au server " +
                                     txtServer.getText() + "\nvia port " +
                                     txtPort.getText());
                        } catch (IOException ex) {
                            msg.msge("Impossible de se connecter au server " +
                                     txtServer.getText() + "\nvia port " +
                                     txtPort.getText());
                        }

                        frameLoginNormal.setVisible(false);
                        boolSSL = false;
                    }
                }
            }
        });

        boutonConnecterSSL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == boutonConnecterSSL) {
                    if (txtServer.getText().equals("")) {
                        msg.msge("Vous devez entrer un nom de serveur");
                    } else if (Integer.parseInt(txtPort.getText()) <= 0) {
                        msg.msge(
                                "Vous devez entrer un port valide\npar défaut SMTP:25");
                    } else if (txtUsername.getText().equals("")) {
                        msg.msge(
                                "Vous devez obligatoirement entrer un nom d'utilisateur");
                    } else if (passwd.getPassword().equals("")) {
                        msg.msge(
                                "Vous devez obligatoirement entrer un password");
                    } else {
                        try {
                            //verification du hostname et du port
                            Socket temp = new Socket(txtServer.getText(),
                                    Integer.parseInt(txtPort.getText()));
                            temp.close();
                            String password = "";
                            for (int i = 0; i < passwd.getPassword().length; i++) {
                                password += passwd.getPassword()[i];
                            }
                            login = new Login(txtServer.getText(),
                                              txtPort.getText(),
                                              txtUsername.getText(),
                                              password);

                            frameLoginSSL.setVisible(false);
                            boolSSL = true;
                            msg.msgi("Connecté avec succès sur " +
                                     txtServer.getText() + "\nvia le port " +
                                     txtPort.getText());
                        } catch (Exception ex) {
                            msg.msge("Impossible de se connecter sur " +
                                     txtServer.getText() + "\nvia le port " +
                                     txtPort.getText());
                        }
                    }
                }
            }
        });

        //****ÉCOUTEURS RADIO***
         radioEmail.addActionListener(alRadioListe);
        radioNom.addActionListener(alRadioListe);
        radioPseudo.addActionListener(alRadioListe);

        boutonAddTo.addActionListener(alBoutonAdd);
        boutonBccTo.addActionListener(alBoutonAdd);
        boutonCcTo.addActionListener(alBoutonAdd);

        //********ÉCOUTEURS**********
         //ajout des écouteurs pour les items du menuLogin
         itemNormal.addActionListener(alLogin);
        itemSSL.addActionListener(alLogin);

        //ajout des écouteurs pour les items du menuOptions
        //partie menuOptionsFichier
        itemFichierInter.addActionListener(alMenuFichiers);

        //partie menuOptions
        itemURL.addActionListener(alOptions);
        itemCheckRecvName.addActionListener(alOptions);

        //partie menuOptionsContact
        itemAjouter.addActionListener(alMenuContact);
        itemRetirer.addActionListener(alMenuContact);
        itemImporter.addActionListener(alMenuContact);
        itemExporter.addActionListener(alMenuContact);

        //ajout des écouteurs pour les items du menuEdit
        itemCopier.addActionListener(alMenuEdit);
        itemColler.addActionListener(alMenuEdit);
        itemCouper.addActionListener(alMenuEdit);
        itemRechercher.addActionListener(alMenuEdit);

        //***************************
         //******CONSTRUCTION DES MENUS******
          //menuLogin
          menuLogin.add(itemNormal);
        menuLogin.add(itemSSL);

        //menuOptions
        menuOptionsFichier.add(itemFichierInter);

        menuOptionsContact.add(itemAjouter);
        menuOptionsContact.add(itemRetirer);
        menuOptionsContact.add(itemImporter);
        menuOptionsContact.add(itemExporter);

        menuOptions.add(menuOptionsFichier);
        menuOptions.addSeparator();
        menuOptions.add(itemURL);
        menuOptions.addSeparator();
        menuOptions.add(menuOptionsContact);
        menuOptions.addSeparator();
        menuOptions.add(itemCheckRecvName);

        //menuEdit
        menuEdit.add(itemCopier);
        menuEdit.add(itemColler);
        menuEdit.add(itemCouper);

        menuEdit.addSeparator();
        menuEdit.add(itemRechercher);

        menuBar.add(menuLogin);
        menuBar.add(menuEdit);
        menuBar.add(menuOptions);

        //****************************************
         ajouterComposant(cp, menuBar, 0, 0, 125, 25);
        //To:
        ajouterComposant(cp, lblTo, 0, 40, 30, 30);
        ajouterComposant(cp, txtTo, 50, 42, 200, 20);
        //Bcc:
        ajouterComposant(cp, lblBcc, 0, 70, 30, 30);
        ajouterComposant(cp, txtBcc, 50, 72, 200, 20);
        //Cc:
        ajouterComposant(cp, lblCc, 0, 100, 30, 30);
        ajouterComposant(cp, txtCc, 50, 102, 200, 20);
        //From:
        ajouterComposant(cp, lblNom, 300, 25, 50, 20);
        ajouterComposant(cp, txtNom, 300, 42, 150, 20);
        ajouterComposant(cp, lblFrom, 460, 25, 150, 20);
        ajouterComposant(cp, txtFrom, 460, 42, 150, 20);
        ajouterComposant(cp, lblReplyName, 300, 65, 100, 20);
        ajouterComposant(cp, txtReplyNom, 300, 82, 150, 20);
        ajouterComposant(cp, lblReplyEmail, 460, 65, 100, 20);
        ajouterComposant(cp, txtReplyEmail, 460, 82, 150, 20);

        //Subject:
        ajouterComposant(cp, lblSubject, 0, 130, 70, 30);
        ajouterComposant(cp, txtSubject, 50, 132, 200, 20);
        //editor
        ajouterComposant(cp, scrollEditor, 0, 175, 400, 400);
        //bouton
        ajouterComposant(cp, boutonText, 410, 175, 100, 25);
        ajouterComposant(cp, boutonHtml, 410, 210, 100, 25);
        ajouterComposant(cp, boutonUrlInter, 410, 245, 100, 25);
        ajouterComposant(cp, boutonUrlMan, 410, 280, 100, 25);

        //liste
        ajouterComposant(cp, lblListe, 525, 155, 100, 25);
        ajouterComposant(cp, scrollListe, 525, 175, 300, 400);
        //radio
        ajouterComposant(cp, radioNom, 730, 100, 100, 20);
        ajouterComposant(cp, radioEmail, 730, 125, 100, 20);
        ajouterComposant(cp, radioPseudo, 730, 150, 100, 20);

        //bouton contacts
        ajouterComposant(cp, boutonAjouter, 850, 175, 100, 25);
        ajouterComposant(cp, boutonRetirer, 850, 210, 100, 25);
        ajouterComposant(cp, boutonImporter, 850, 300, 100, 25);
        ajouterComposant(cp, boutonExporter, 850, 345, 100, 25);

        //radio contacts
        ajouterComposant(cp, boutonAddTo, 850, 500, 100, 25);
        ajouterComposant(cp, boutonBccTo, 850, 525, 100, 25);
        ajouterComposant(cp, boutonCcTo, 850, 550, 100, 25);

        //bouton Send
        ajouterComposant(cp, boutonSend, 850, 50, 100, 25);

        //ajouterContact
        cContactsAjouter.setLayout(null);
        ajouterComposant(cContactsAjouter, lblContactNom, 0, 10, 50, 20);
        ajouterComposant(cContactsAjouter, txtContactNom, 50, 12, 100, 20);
        ajouterComposant(cContactsAjouter, lblContactEmail, 0, 35, 50, 20);
        ajouterComposant(cContactsAjouter, txtContactEmail, 50, 37, 100, 20);
        ajouterComposant(cContactsAjouter, lblContactPseudo, 0, 60, 50, 20);
        ajouterComposant(cContactsAjouter, txtContactPseudo, 50, 62, 100, 20);
        ajouterComposant(cContactsAjouter, boutonContactAjouter, 50, 95, 100,
                         20);

        frameContactsAjouter.setLocation(300, 200);
        frameContactsAjouter.setSize(200, 200);

        //**********************************************
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.setSize(1024, 640);
        frame.setVisible(true);
        frame.show();

    }

    /**
     *  instancierComposants : instancie tous les composants
     * void
     */
    private void instancierComposants() {
        //JFrame
        frame = new JFrame("JMail par Hassen Ben Tanfous");
        frameLoginNormal = new JFrame("Login standard");
        frameLoginSSL = new JFrame("Login sécurisée");
        frameContactsAjouter = new JFrame("Ajouter contact");
        frameURL = new JFrame("Ajouter un URL");
        frameUrl2 = new JFrame("Ajouter un URL dans mail");
        frameFichiers = new JFrame("Attachement de fichiers");

        //Container
        cp = frame.getContentPane();
        cContactsAjouter = frameContactsAjouter.getContentPane();
        cLoginNormal = frameLoginNormal.getContentPane();
        cLoginSSL = frameLoginSSL.getContentPane();
        cURL = frameURL.getContentPane();
        cUrl2 = frameUrl2.getContentPane();
        cFichiers = frameFichiers.getContentPane();

        //JMenuBar
        menuBar = new JMenuBar();

        //JMenu
        menuLogin = new JMenu("Login");

        menuOptions = new JMenu("Options");
        menuOptionsFichier = new JMenu("Attacher fichiers");
        menuOptionsContact = new JMenu("Contact");

        menuEdit = new JMenu("Edit");

        //JMenuItem
        //Menu Login
        itemNormal = new JMenuItem("Normal");
        itemSSL = new JMenuItem("SSL");

        //Menu Options Fichier
        itemFichierInter = new JMenuItem("via interface");

        //Menu Options
        itemURL = new JMenuItem("Ajouter URL");
        itemCheckRecvName = new JCheckBoxMenuItem("Noms receveurs");

        //Menu Options Contacts
        itemAjouter = new JMenuItem("Ajouter contact");
        itemRetirer = new JMenuItem("Retirer contact");
        itemImporter = new JMenuItem("Importer liste");
        itemExporter = new JMenuItem("Exporter liste");

        //Menu Edit
        itemCopier = new JMenuItem("Copier");
        itemColler = new JMenuItem("Coller");
        itemCouper = new JMenuItem("Couper");
        itemRechercher = new JMenuItem("Rechercher");

        //JButton
        boutonHtml = new JButton("HTML");
        boutonText = new JButton("Text");
        boutonUrlInter = new JButton("URL 1");
        boutonUrlMan = new JButton("URL 2");
        boutonImporter = new JButton("Importer");
        boutonExporter = new JButton("Exporter");
        boutonAjouter = new JButton("Ajouter");
        boutonRetirer = new JButton("Retirer");
        boutonConnecterNormal = new JButton("Connecter");
        boutonConnecterSSL = new JButton("Connecter");
        boutonSend = new JButton("Send Email");
        boutonContactAjouter = new JButton("Ajouter");
        boutonAddTo = new JButton("Add To");
        boutonBccTo = new JButton("Bcc To");
        boutonCcTo = new JButton("Cc To");
        boutonUrlAjouter = new JButton("Ajouter");
        boutonUrl2Ajouter = new JButton("Ajouter");
        boutonFichierAttacher = new JButton("Attacher");

        //JTextField
        txtTo = new JTextField();
        txtBcc = new JTextField();
        txtCc = new JTextField();
        txtSubject = new JTextField();
        txtServer = new JTextField("smtp1.sympatico.ca");
        txtPort = new JTextField("25");
        txtUsername = new JTextField();
        txtContactNom = new JTextField();
        txtContactEmail = new JTextField();
        txtContactPseudo = new JTextField();
        txtFrom = new JTextField();
        txtNom = new JTextField();
        txtReplyNom = new JTextField();
        txtReplyEmail = new JTextField();
        txtUrlPath = new JTextField();
        txtUrlNom = new JTextField();
        txtUrlDesp = new JTextField();
        txtUrl2Nom = new JTextField();
        txtUrl2Path = new JTextField();
        txtUrl2Width = new JTextField();
        txtUrl2Height = new JTextField();
        txtFichierPath = new JTextField();
        txtFichierNom = new JTextField();
        txtFichierDescription = new JTextField();

        //JPasswordField
        passwd = new JPasswordField();

        //JEditor
        editor = new JEditorPane();

        //modèle liste
        modeleListeNom = new DefaultListModel();
        modeleListeEmail = new DefaultListModel();
        modeleListePseudo = new DefaultListModel();

        //JFileChooser
        jfcImporter = new JFileChooser();
        jfcExporter = new JFileChooser();
        jfcUrl = new JFileChooser();
        jfcFichiers = new JFileChooser();

        //JList
        liste = new JList(modeleListeNom);

        //ScrollPane
        scrollEditor = new JScrollPane(editor);
        scrollListe = new JScrollPane(liste);

        //Label
        lblOrganisations = new JLabel("Organisations");
        lblTo = new JLabel("To:");
        lblBcc = new JLabel("Bcc:");
        lblCc = new JLabel("Cc:");
        lblSubject = new JLabel("Subject:");
        lblListe = new JLabel("Liste de contacts");
        lblServer = new JLabel("Server:");
        lblPort = new JLabel("Port:");
        lblUsername = new JLabel("Username:");
        lblPasswd = new JLabel("Password:");
        lblContactNom = new JLabel("Nom:");
        lblContactEmail = new JLabel("Email:");
        lblContactPseudo = new JLabel("Pseudo:");
        lblFrom = new JLabel("From: (Email)");
        lblNom = new JLabel("Nom: ");
        lblReplyEmail = new JLabel("Reply Email:");
        lblReplyName = new JLabel("Reply Nom:");
        lblUrlPath = new JLabel("Path:");
        lblUrlNom = new JLabel("Nom:");
        lblUrlDescription = new JLabel("Description:");
        lblUrl2Nom = new JLabel("Nom:");
        lblUrl2Path = new JLabel("Path:");
        lblUrl2Width = new JLabel("Width:");
        lblUrl2Height = new JLabel("Height");
        lblFichiersPath = new JLabel("Path:");
        lblFichiersNom = new JLabel("Nom:");
        lblFichiersDescription = new JLabel("Description:");

        //JComboBox
        comboUrl = new JComboBox(new String[] {"inline", "mixed"});
        comboFichiers = new JComboBox(new String[] {"inline", "mixed"});

        //JRadioButton
        //gauche
        radioNom = new JRadioButton("par Noms");
        radioEmail = new JRadioButton("par Emails");
        radioPseudo = new JRadioButton("par Pseudos");

        //HtmlEmail
        email = new HtmlEmail();

        //File
        tabFichiers = null;

        //EmailAttachment
        tabAttachements = null;

        editeur = new Editeur();

        listeContacts = new ListeContacts();
    }

    /**
     * ajouterComposant : ajoute un composant au c
     * @param c: le container recevant le composant
     * @param comp: le composant
     * @param x1: x
     * @param y1: y
     * @param x2: largeur
     * @param y2 : hauteur
     * void
     */
    private void ajouterComposant(Container c, Component comp,
                                  int x1, int y1, int x2, int y2) {
        comp.setBounds(x1, y1, x2, y2);
        c.add(comp);
    }

    /**
     * ajouterContacts :permet d'ajouter les nouveaux contacts
     * réécriture de la méthode
     * void
     * @see org.apache.commons.mail.Email#setTo(Collection)
     */
    private void ajouterContacts() {

        String addTo = txtTo.getText();
        String bccTo = txtBcc.getText();
        String ccTo = txtCc.getText();
        ArrayList collecAdd = new ArrayList();
        ArrayList collecBcc = new ArrayList();
        ArrayList collecCc = new ArrayList();

        try {
            email.setTo(collecAdd);
            email.setBcc(collecBcc);
            email.setCc(collecCc);
        } catch (EmailException e) {}

        if (!addTo.equals("")) {
            if (addTo.indexOf(", ") == -1) {
                try {
                    email.addTo(addTo.toString());
                } catch (EmailException ex) {
                }
            } while (addTo.indexOf(", ") != -1) {
                if (modeleListeEmail.contains(addTo.substring(0,
                        addTo.indexOf(", "))) && itemCheckRecvName.isSelected()) {
                    try {
                        email.addTo(addTo.substring(0,
                                addTo.indexOf(", ")), modeleListeNom.get
                                    (modeleListeEmail.indexOf
                                     (addTo.substring(0, addTo.indexOf(", ")))).
                                    toString());
                    } catch (EmailException e1) {
                        msg.msge(
                                "Problème durant l'ajout des noms de contacts AddTo");
                    }
                } else {
                    try {
                        email.addTo(addTo.substring(0, addTo.indexOf(", ")));
                    } catch (EmailException e1) {
                        msg.msge(
                                "Problème durant l'ajout des noms de contacts BccTo",
                                "ERREUR");
                    }
                }
                addTo = addTo.substring(addTo.indexOf(", ") + 2);
            }
        }
        if (!bccTo.equals("")) {
            if (bccTo.indexOf(", ") == -1) {
                try {
                    email.addBcc(bccTo);
                } catch (EmailException ex1) {
                }
            }

            while (bccTo.indexOf(", ") != -1) {
                if (modeleListeEmail.contains(bccTo.substring(0,
                        bccTo.indexOf(", ")))
                    && itemCheckRecvName.isSelected()) {
                    try {
                        email.addBcc(bccTo.substring(0, bccTo.indexOf(", ")),
                                     modeleListeNom.get(modeleListeEmail.
                                indexOf(bccTo.substring(0, bccTo.indexOf(", ")))).
                                     toString());
                    } catch (EmailException e2) {
                        msg.msge(
                                "Problème durant l'ajout des noms de contacts BccTo");
                    }
                } else {
                    try {
                        email.addBcc(bccTo.substring(0, bccTo.indexOf(", ")));
                    } catch (EmailException e2) {
                        msg.msge(
                                "Problème durant l'ajout des noms de contacts BccTo");
                    }
                }
                bccTo = bccTo.substring(bccTo.indexOf(", ") + 2);
            }
        }
        if (!ccTo.equals("")) {
            if (ccTo.indexOf(", ") == -1) {
                try {
                    email.addCc(ccTo);
                } catch (EmailException ex2) {
                }
            } while (ccTo.indexOf(", ") != -1) {
                if (modeleListeEmail.contains(ccTo.substring(0,
                        ccTo.indexOf(", ")))
                    && itemCheckRecvName.isSelected()) {
                    try {
                        email.addCc(ccTo.substring(0, ccTo.indexOf(", ")),
                                    modeleListeEmail.get(modeleListeEmail.
                                indexOf(ccTo.substring(0, ccTo.indexOf(", ")))).
                                    toString());
                    } catch (EmailException e2) {
                        msg.msge(
                                "Problème durant l'ajout des noms de contacts CcTo");
                    }
                } else {
                    try {
                        email.addCc(ccTo.substring(0, ccTo.indexOf(", ")));
                    } catch (EmailException e2) {
                        msg.msge(
                                "Problème durant l'ajout des noms de contacts CcTo");
                    }
                }
                ccTo = ccTo.substring(ccTo.indexOf(", ") + 2);
            }
        }
    }

    /**
     * ranger : range la liste de contacts d'après les différents models
     * par ordre croissant
     * @param model :
     * @param modelSuite1 :
     * @param modelSuite2 :
     * void
     */
    private void ranger(DefaultListModel model, DefaultListModel modelSuite1,
                        DefaultListModel modelSuite2) {
        Object[] tab = model.toArray();
        Object[] tab2 = modelSuite1.toArray();
        Object[] tab3 = modelSuite2.toArray();
        Object obj,
                obj2,
                obj3;

        boolean fini = true;
        int j = 0;
        while (fini) {
            fini = false;
            for (int i = 0; i < tab.length - 1 - j; i++) {
                if (tab[i].toString().compareTo(tab[i + 1].toString()) > 0) {
                    obj = tab[i];
                    obj2 = tab2[i];
                    obj3 = tab3[i];
                    tab[i] = tab[i + 1];
                    tab2[i] = tab2[i + 1];
                    tab3[i] = tab3[i + 1];
                    tab[i + 1] = obj;
                    tab2[i + 1] = obj2;
                    tab3[i + 1] = obj3;
                    fini = true;
                }
            }
            j++;
        }
        model.removeAllElements();
        modelSuite1.removeAllElements();
        modelSuite2.removeAllElements();
        for (int i = 0; i < tab.length; i++) {
            model.add(i, tab[i]);
            modelSuite1.add(i, tab2[i]);
            modelSuite2.add(i, tab3[i]);
        }
    }

    /**
     * encodeURL: encode le paramètre url pour le rendre lisible
     * @param url String: paramètre à traduire
     * @return String
     */
    private String encodeURL(String url) {
        int index = 0;
        while (index > -1) {
            index = url.indexOf(' ');
            if (index > -1) {
                url = url.substring(0, index) + "%20" +
                      url.substring(index + 1, url.length());
            }
        }
        url = url.substring(0, url.indexOf(':') + 1) + "/" +
              url.substring(url.indexOf(':') + 1);
        url = url.replace('\\', '/');
        return url;
    }

    //alEditor: permet de gérer les évènements autour de l'éditeur
    //de mail
    private ActionListener alEditor = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String message = editor.getText();
            if (e.getSource() == boutonText) {
                boutonUrlMan.setEnabled(true);
                boutonUrlInter.setEnabled(true);
                editor.setContentType("text/plain");
                editor.setText(message);
            } else if (e.getSource() == boutonHtml) {
                boutonUrlInter.setEnabled(false);
                boutonUrlMan.setEnabled(false);
                editor.setContentType("text/html");
                editor.setText(message);
            } else if (e.getSource() == boutonUrlMan) {
                cUrl2.setLayout(null);
                ajouterComposant(cUrl2, lblUrl2Path, 0, 0, 50, 20);
                ajouterComposant(cUrl2, txtUrl2Path, 55, 2, 100, 20);
                ajouterComposant(cUrl2, lblUrl2Nom, 0, 25, 50, 20);
                ajouterComposant(cUrl2, txtUrl2Nom, 55, 27, 100, 20);
                ajouterComposant(cUrl2, lblUrl2Width, 0, 50, 50, 20);
                ajouterComposant(cUrl2, txtUrl2Width, 55, 52, 50, 20);
                ajouterComposant(cUrl2, lblUrl2Height, 0, 75, 50, 20);
                ajouterComposant(cUrl2, txtUrl2Height, 55, 77, 50, 20);
                ajouterComposant(cUrl2, boutonUrl2Ajouter, 45, 100, 100, 20);

                frameUrl2.setLocation(200, 200);
                frameUrl2.setSize(200, 200);
                frameUrl2.setVisible(true);
            } else if (e.getSource() == boutonUrlInter) {
                int choix = jfcUrl.showOpenDialog(null);
                if (choix == JFileChooser.APPROVE_OPTION) {
                    File file = jfcUrl.getSelectedFile();
                    String strFile = file.getAbsolutePath();
                    String filename = file.getName();

                    strFile = "file://localhost/" + encodeURL(strFile);

                    try {
                        editor.setText(editor.getText() +
                                       "<img src=cid:" +
                                       email.embed(new URL(
                                               strFile), filename)
                                       + " width=100" +
                                       " height=100>");
                        email.setHtmlMsg(editor.getText());
                    } catch (MalformedURLException ex) {
                    } catch (EmailException ex) {
                    }
                }
            }
        }
    };

    //alRadioAdd: permet d'écouter les evenenements sur les boutons
    //radio addTo, BccTo et CcTo pour ajouter l'adresse e-mail du contact
    private ActionListener alBoutonAdd = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == boutonAddTo) {
                int tabIndex[] = liste.getSelectedIndices();
                for (int i = 0; i < tabIndex.length; i++) {
                    txtTo.setText((String) modeleListeEmail.get(tabIndex[i]) +
                                  ", " + txtTo.getText());
                }
            } else if (e.getSource() == boutonBccTo) {
                int tabIndex[] = liste.getSelectedIndices();
                for (int i = 0; i < tabIndex.length; i++) {
                    txtBcc.setText((String) modeleListeEmail.get(tabIndex[i]) +
                                   ", " + txtBcc.getText());
                }
            } else if (e.getSource() == boutonCcTo) {
                int tabIndex[] = liste.getSelectedIndices();
                for (int i = 0; i < tabIndex.length; i++) {
                    txtCc.setText((String) modeleListeEmail.get(tabIndex[i]) +
                                  ", " + txtCc.getText());
                }
            }
        }
    };

    //alRadioListe: permet d'écouteurs les évenements sur les boutons
    //radio email, nom et pseudo pour trier la liste
    private ActionListener alRadioListe = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == radioEmail) {
                radioNom.setSelected(false);
                radioPseudo.setSelected(false);
                ranger(modeleListeEmail, modeleListeNom, modeleListePseudo);
                liste.setModel(modeleListeEmail);

            } else if (e.getSource() == radioNom) {
                radioEmail.setSelected(false);
                radioPseudo.setSelected(false);
                ranger(modeleListeNom, modeleListeEmail, modeleListePseudo);
                liste.setModel(modeleListeNom);

            } else if (e.getSource() == radioPseudo) {
                radioNom.setSelected(false);
                radioEmail.setSelected(false);
                ranger(modeleListePseudo, modeleListeEmail, modeleListeNom);
                liste.setModel(modeleListePseudo);
            }
        }
    };

    //alSend: permet d'écouter les evenements sur le boutonSend
    private ActionListener alSend = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == boutonSend) {
                new SendEmail().start();
            }
        }
    };

    //alLogin: permet d'écouter les évenements dans le menuLogin
    private ActionListener alLogin = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();

            if (item == itemNormal) {
                cLoginNormal.setLayout(null);
                ajouterComposant(cLoginNormal, lblServer, 0, 10, 50, 20);
                ajouterComposant(cLoginNormal, txtServer, 50, 12, 175, 20);
                ajouterComposant(cLoginNormal, lblPort, 0, 25, 50, 20);
                ajouterComposant(cLoginNormal, txtPort, 50, 30, 30, 20);
                ajouterComposant(cLoginNormal, boutonConnecterNormal, 10, 60,
                                 100, 20);

                frameLoginNormal.setLocation(0, 0);
                frameLoginNormal.setSize(250, 125);
                frameLoginNormal.show();
            } else if (item == itemSSL) {
                cLoginSSL.setLayout(null);
                ajouterComposant(cLoginSSL, lblServer, 0, 10, 50, 20);
                ajouterComposant(cLoginSSL, txtServer, 50, 12, 175, 20);
                ajouterComposant(cLoginSSL, lblPort, 0, 25, 50, 20);
                ajouterComposant(cLoginSSL, txtPort, 50, 30, 30, 20);
                ajouterComposant(cLoginSSL, lblUsername, 0, 50, 100, 20);
                ajouterComposant(cLoginSSL, txtUsername, 65, 52, 140, 20);
                ajouterComposant(cLoginSSL, lblPasswd, 0, 75, 75, 20);
                ajouterComposant(cLoginSSL, passwd, 65, 77, 140, 20);
                ajouterComposant(cLoginSSL, boutonConnecterSSL, 35, 115, 100,
                                 20);

                frameLoginSSL.setLocation(0, 0);
                frameLoginSSL.setSize(250, 200);
                frameLoginSSL.show();
            }
        }
    };

    //alMenuFichiers: permet d'écouter les evenements dans le menuOptionsFichier
    private ActionListener alMenuFichiers = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == itemFichierInter) {
                int choix = jfcFichiers.showOpenDialog(null);
                if (choix == JFileChooser.APPROVE_OPTION) {
                    tabFichiers = jfcFichiers.getSelectedFiles();
                    nbFichiers = tabFichiers.length;
                    tabAttachements = new EmailAttachment[nbFichiers];
                    choix = JOptionPane.showOptionDialog(null,
                            "Voulez-vous prendre l'option par défaut pour attacher les fichiers",
                            "Attachements de fichiers",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, new String[] {
                            "Oui", "Non", "Annuler"}, new String("Non"));

                    switch (choix) {
                    case 0:
                        for (int i = 0; i < tabAttachements.length; i++) {
                            tabAttachements[i] = new EmailAttachment(); ;
                            tabAttachements[i].setDescription(tabFichiers[i].
                                    getName());
                            tabAttachements[i].setName(tabFichiers[i].getName());
                            tabAttachements[i].setPath(tabFichiers[i].
                                    getAbsolutePath());
                            tabAttachements[i].setDisposition("inline");
                        }
                        break;

                    case 1:
                        cFichiers.setLayout(null);
                        ajouterComposant(cFichiers, lblFichiersPath, 0, 0, 50,
                                         20);
                        ajouterComposant(cFichiers, txtFichierPath, 55, 2, 100,
                                         20);
                        ajouterComposant(cFichiers, lblFichiersNom, 0, 25, 50,
                                         20);
                        ajouterComposant(cFichiers, txtFichierNom, 55, 27, 100,
                                         20);
                        ajouterComposant(cFichiers, lblFichiersDescription, 0,
                                         50, 50, 20);
                        ajouterComposant(cFichiers, txtFichierDescription, 55,
                                         52, 100, 20);
                        ajouterComposant(cFichiers, comboFichiers, 45, 90, 100,
                                         20);
                        ajouterComposant(cFichiers, boutonFichierAttacher, 45,
                                         120, 100, 20);

                        frameFichiers.setLocation(100, 10);
                        frameFichiers.setSize(200, 230);
                        frameFichiers.setVisible(true);

                        txtFichierPath.setText(tabFichiers[nbFichiers -
                                               1].getAbsolutePath());
                        txtFichierNom.setText(tabFichiers[nbFichiers -
                                              1].getName());

                    }
                }

            }
        }
    };

    //alOptions: permet d'écouter les evenements dans le menuOptions
    private ActionListener alOptions = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (itemCheckRecvName.isSelected()) {
                itemCheckRecvName.setSelected(true);
            } else if (e.getSource() == itemURL) {
                cURL.setLayout(null);
                ajouterComposant(cURL, lblUrlPath, 0, 0, 50, 20);
                ajouterComposant(cURL, txtUrlPath, 55, 2, 100, 20);
                ajouterComposant(cURL, lblUrlNom, 0, 25, 50, 20);
                ajouterComposant(cURL, txtUrlNom, 55, 27, 100, 20);
                ajouterComposant(cURL, lblUrlDescription, 0, 50, 50, 20);
                ajouterComposant(cURL, txtUrlDesp, 55, 52, 100, 20);
                ajouterComposant(cURL, comboUrl, 45, 95, 100, 20);
                ajouterComposant(cURL, boutonUrlAjouter, 45, 125, 100, 20);

                frameURL.setLocation(0, 0);
                frameURL.setSize(200, 200);
                frameURL.show();
            } else if (comboUrl.getSelectedIndex() == 0) {
                urlDisposition = "inline";
            } else if (comboUrl.getSelectedIndex() == 1) {
                urlDisposition = "mixed";
            }
        }
    };


    //alMenuContact: permet d'écouter les evenements dans le menuOptionsContact
    private ActionListener alMenuContact = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == itemAjouter || e.getSource() == boutonAjouter) {
                txtContactNom.setText("");
                txtContactEmail.setText("");
                txtContactPseudo.setText("");
                frameContactsAjouter.show();
            } else if (e.getSource() == itemRetirer ||
                       e.getSource() == boutonRetirer) {
                try {
                    int index = liste.getSelectedIndex();

                    modeleListeNom.remove(index);
                    modeleListeEmail.remove(index);
                    modeleListePseudo.remove(index);
                    listeContacts.retirer(index);
                } catch (Exception ee) {
                    msg.msge(
                            "Vous devez sélectionner un élément existant dans la liste de contacts");
                }
            } else if (e.getSource() == itemImporter ||
                       e.getSource() == boutonImporter) {
                int choix;
                choix = jfcImporter.showOpenDialog(null);
                if (choix == JFileChooser.APPROVE_OPTION) {
                    File fileImporter = jfcImporter.getSelectedFile();
                    listeContacts.importer(fileImporter.getAbsolutePath());
                    for (int i = 0; i < listeContacts.getListe().size(); i++) {
                        modeleListeNom.add(i,
                                           listeContacts.getContact(i).getNom());
                        modeleListeEmail.add(i,
                                             listeContacts.getContact(i).
                                             getEmail());
                        modeleListePseudo.add(i,
                                              listeContacts.getContact(i).
                                              getPseudo());
                    }
                }
            } else if (e.getSource() == itemExporter ||
                       e.getSource() == boutonExporter) {
                int choix;
                choix = jfcExporter.showOpenDialog(null);
                if (choix == JFileChooser.APPROVE_OPTION) {
                    File fileExporter = jfcExporter.getSelectedFile();
                    listeContacts.exporter(fileExporter.getAbsolutePath());
                    msg.msgi("Fichier " + fileExporter.getName() +
                             " exporté avec succès");
                }
            }
        }
    };

    //alMenuEdit: permet d'écouter les evenements dans le menuEdit
    private ActionListener alMenuEdit = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == itemCopier) {
                editeur.copier(editor);
            } else if (e.getSource() == itemColler) {
                editeur.coller(editor);
            } else if (e.getSource() == itemCouper) {
                editeur.couper(editor);
            } else if (e.getSource() == itemRechercher) {
                try {
                    String str = JOptionPane.showInputDialog(
                            "Entrez votre recherche");
                    editeur.rechercher(editor, str);
                } catch (Exception ee) {
                    msg.msge("Vous devez entrer un élément de recherche valide");
                }
            }
        }
    };

    /**
     * SendMail crée un thread pour envoyer les emails.
     */
    private class SendEmail extends Thread {

        public void run() {
            sendEmail();
        }

        /**
         * sendEmail : configure l'email et l'envoie
         * void
         */
        private void sendEmail() {
            if (boolSSL) {
                email.setMailSession(login.getSession());
            } else {
                email.setHostName(txtServer.getText());
                email.setSmtpPort(Integer.parseInt(txtPort.getText()));
            }
            ajouterContacts();
            if (tabAttachements != null) {
                for (int i = 0; i < tabAttachements.length; i++) {
                    try {
                        email.attach(tabAttachements[i]);
                    } catch (EmailException ex) {}
                }
            }
            email.setSubject(txtSubject.getText());
            try {
                if (editor.getContentType().equals("text/plain")) {
                    email.setTextMsg(editor.getText().toString());
                } else if (editor.getContentType().equals("text/html")) {
                    email.setHtmlMsg(editor.getText().toString());
                }
                if (txtNom.getText().equals("")) {
                    email.setFrom(txtFrom.getText().toString());
                } else {
                    email.setFrom(txtFrom.getText().toString(),
                                  txtNom.getText().toString());
                }
                if (!txtReplyEmail.getText().equals("")) {
                    email.addReplyTo(txtReplyEmail.getText());
                } else if (!txtReplyEmail.getText().equals("") &&
                           !txtReplyNom.getText().equals("")) {
                    email.addReplyTo(txtReplyNom.getText(),
                                     txtReplyEmail.getText());
                }
                email.send();
                msg.msgi("Email envoyé");
            } catch (EmailException e) {
                msg.msge("Erreur lors de l'envoie de l'email");
            }

            email = new HtmlEmail();
            tabAttachements = null;
            tabFichiers = null;
            nbFichiers = 0;
        }
    }
}
