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

 * ListeContacts.java
 * Date: 16/01/2006
 * ListeContacts gère les différents contacts
 * Merci d'envoyer tout commentaire ou bug à l'adresse suivante:
 * @author Hassen Ben Tanfous
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import com.coded.jmail.msg.*;

public class ListeContacts {
    private static ArrayList listeContacts = new ArrayList();
    private static String nom,
            email,
            pseudo;

    private Messagerie msg;

    public ListeContacts() {
        msg.titre = "JMail par Hassen Ben Tanfous";
    }

    /**
     * ajouter : ajoute un contact au container ArrayList
     * @param nom : le nom du contact
     * @param email : l'email du contact
     * @param pseudo : le pseudo du contact
     * @see Contact#Contact(String, String, String)
     * void
     */
    public void ajouter(String nom, String email, String pseudo) {
        listeContacts.add(new Contact(nom, email, pseudo));
    }

    /**
     * retirer : retire le contact du container ArrayList
     * @param i : la position du contact
     * void
     */
    public void retirer(int i) {
        listeContacts.remove(i);
    }

    /**
     * getContact : récupèle le contact
     * @param i: la position du contact dans le container ArrayList
     * @see Contact
     * @return :
     * Contact
     */
    public Contact getContact(int i) {
        return (Contact) listeContacts.get(i);
    }

    /**
     * getListe : retourne la liste contenant tous les contacts
     * @see java.util.ArrayList
     * @return :
     * ArrayList
     */
    public ArrayList getListe() {
        return listeContacts;
    }

    /**
     * importer : Importe dans le container ArrayList les noms, emails et pseudo
     * de tous les contacts écrit dans le fichier
     * @param filename : nom du fichier où se retrouvent tous les contacts
     * @see java.io.BufferedReader
     * @see java.io.FileReader
     * @see ListeContacts#ajouter(String, String, String)
     * void
     */
    public void importer(String filename) {
        try {
            BufferedReader lire = new BufferedReader(new FileReader(filename));
            String ligne = lire.readLine();
            int posi;
            while (ligne != null) {
                email = ligne.substring(0, ligne.indexOf(' '));
                ligne = ligne.substring(ligne.indexOf(' ') + 1);
                pseudo = ligne.substring(0, ligne.indexOf(' '));
                ligne = ligne.substring(ligne.indexOf(' ') + 1);
                nom = ligne;
                ajouter(nom, email, pseudo);
                ligne = lire.readLine();
            }

            lire.close();
        } catch (Exception e) {
            msg.msge("Erreur durant la lecture du fichier " + filename);
        }
    }

    /**
     * exporter : exporte la liste de tous les contacts dans un fichier
     * @param filename : nom du fichier à écrire
     * @see java.io.BufferedReader
     * @see java.io.FileWriter
     * @see ListeContacts#getContact(int)
     * void
     */
    public void exporter(String filename) {
        try {
            BufferedWriter ecrire = new BufferedWriter(new FileWriter(filename));
            String ligne = "";

            for (int i = 0; i < getListe().size(); i++) {
                ligne += getContact(i).getEmail() + " " +
                        getContact(i).getPseudo() + " " +
                        getContact(i).getNom() + "\n";
            }

            ecrire.write(ligne);
            ecrire.close();
        } catch (Exception e) {
            msg.msge("Erreur durant l'ecriture du fichier " + filename);
        }
    }
}
