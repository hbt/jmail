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
 * Editeur.java
 * permet de gérer le menuEdit de la classe JMail pour les copier, coller, recherche etc
 * Date: 15/01/2006
 * @author Hassen Ben Tanfous
 */

//imports
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import java.awt.Color;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Editeur {

    private MonSurligneur monSurligneur = new MonSurligneur(Color.RED);

    public void copier(JTextComponent txtComp) {
        txtComp.copy();
    }

    public void coller(JTextComponent txtComp) {
        txtComp.paste();
    }

    public void couper(JTextComponent txtComp) {
        txtComp.cut();
    }

    /**
     * Recherche une String dans le composant
     * @param txtComp JTextComponent
     * @param recherche String
     */
    public void rechercher(JTextComponent txtComp, String recherche) {
        enleverHighlight(txtComp);
        try {
            Highlighter surligneur = txtComp.getHighlighter();
            Document doc = txtComp.getDocument();
            String txt = doc.getText(0, doc.getLength());
            int pos = 0;

            while ((pos = txt.indexOf(recherche, pos)) >= 0) {
                surligneur.addHighlight(pos, pos + recherche.length(),
                                        monSurligneur);
                pos += recherche.length();
            }

        } catch (BadLocationException e) {}
    }

    /**
     * enleve le "surlignage"
     * @param txtComp JTextComponent
     */
    private void enleverHighlight(JTextComponent txtComp) {
        Highlighter surligneur = txtComp.getHighlighter();
        Highlighter.Highlight[] tabSurligneurs = surligneur.getHighlights();

        for (int i = 0; i < tabSurligneurs.length; i++) {
            if (tabSurligneurs[i].getPainter() instanceof MonSurligneur) {
                surligneur.removeHighlight(tabSurligneurs[i]);
            }
        }
    }

    /**
     * MonSurligneur.java
     * permet de surligner du texte lors d'une recherche
     * Date: 15/01/2006
     * @author Hassen Ben Tanfous
     */
    private class MonSurligneur extends DefaultHighlighter.
            DefaultHighlightPainter {

        public MonSurligneur(Color couleur) {
            super(couleur);
        }
    }
}
