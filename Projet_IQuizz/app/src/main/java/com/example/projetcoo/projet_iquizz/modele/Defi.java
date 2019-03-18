package com.example.projetcoo.projet_iquizz.modele;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.modele.Utilisateur;

public class Defi {
    
    private String nom;
    private ArrayList<Question> questions;
    private ArrayList<Utilisateur> joueurs;
    
    public Defi(String nom, ArrayList<Question> questions, ArrayList<Utilisateur> joueurs) {
        this.nom = nom;
        this.questions = questions;
        this.joueurs = joueurs;
    }
    public Defi(String nom, ArrayList<Question> questions, Utilisateur joueur) {
        this.nom = nom;
        this.questions = questions;
        this.joueurs = new ArrayList<Utilisateur>();
        this.joueurs.add(joueur);
    }

    
    public String getNom() { return this.nom; }
    public ArrayList<Question> getQuestions() { return this.questions; }
    public int getNbQuestionsRestantes() { return 3; }
    public int getNbQuestions() { return this.questions.size(); }
    public ArrayList<Utilisateur> getJoueurs() { return this.joueurs; }
    public int getChoice(int numQuestion) { return 1; }
    public String toString() { return this.nom; }
}
