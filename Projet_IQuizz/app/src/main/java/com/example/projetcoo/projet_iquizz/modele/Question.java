package com.example.projetcoo.projet_iquizz.modele;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.modele.Choix;

public class Question {
    
    private String texte;
    private ArrayList<Choix> choix;
    private int correctChoix;
    
    public Question(String texte, ArrayList<Choix> choix) {
        this.texte = texte;
        this.choix = choix;
        this.correctChoix = 0;
    }
    public Question(String texte, ArrayList<Choix> choix, int correctChoix) {
        this.texte = texte;
        this.choix = choix;
        this.correctChoix = correctChoix;
    }

    
    public String getText() { return this.texte; }
    public Choix getChoix(int i) { return this.choix.get(i); }
    public int getNbChoix() { return this.choix.size(); }
    public boolean isCorrectChoice(int num) {
        if (num == correctChoix) {
            return true;
        } else {
            return false;
        }
    }
}
