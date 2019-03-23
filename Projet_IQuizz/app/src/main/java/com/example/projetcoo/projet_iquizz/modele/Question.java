package com.example.projetcoo.projet_iquizz.modele;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.modele.Choix;

public class Question {
    
    private int id;
    private String texte;
    private String categorie;
    private String imageName;
    private String explication;
    private ArrayList<Choix> choix;
    private int correctChoix;
    
    public Question(int id, String texte, String categorie) {
        this.id = id;
        this.texte = texte;
        this.categorie = categorie;
        this.imageName = null;
        this.explication = null;
        this.choix = null;
    }
    
    public Question(int id, String texte, String categorie, String imageName, String expl) {
        this.id = id;
        this.texte = texte;
        this.categorie = categorie;
        this.imageName = imageName;
        this.explication = expl;
        this.choix = null;
    }

    public Question(int id, String texte, String categorie, String imageName, String explication, ArrayList<Choix> choix) {
        this.id = id;
        this.texte = texte;
        this.categorie = categorie;
        this.imageName = imageName;
        this.explication = explication;
        this.choix = choix;
    }

    
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

    public int getID() { return this.id; }
    public String getText() { return this.texte; }
    public ArrayList<Choix> getChoix() { return this.choix; }
    public Choix getChoix(int i) { return this.choix.get(i); }
    public int getNbChoix() { return this.choix.size(); }
    public boolean isCorrectChoix(int num) {
        if (num == correctChoix) {
            return true;
        } else {
            return false;
        }
    }
}
