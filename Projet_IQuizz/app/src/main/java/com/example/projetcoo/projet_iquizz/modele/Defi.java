package com.example.projetcoo.projet_iquizz.modele;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.projetcoo.projet_iquizz.modele.Utilisateur;

public class Defi {
    
    private int id;
    private String nom;
    private ArrayList<Utilisateur> joueurs;
    private ArrayList<Question> questions;
    private HashMap<Utilisateur, HashMap> infos;
    
    public Defi(int id, String nom, 
                ArrayList<Utilisateur> joueurs,
                ArrayList<Question> questions, 
                HashMap<Utilisateur, HashMap> infos) {
        this.id = id;
        this.nom = nom;
        this.joueurs = joueurs;
        this.questions = questions;
        this.infos = infos;
    }
    
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


    public int getID() { return this.id; }
    public String getNom() { return this.nom; }
    public ArrayList<Question> getQuestions() { return this.questions; }
    public int getNbQuestionsRestantes() { 
        return getNbQuestionsRestantes(this.joueurs.get(0));
    }
    public Utilisateur getJoueur(String login) {
        for (int i = 0; i < this.joueurs.size(); i++) {
            if (joueurs.get(i).getNom().equals(login)) {
                return this.joueurs.get(i);
            }
        }
        return null;
    }
    public boolean isJoueur(String login) {
        return getJoueur(login) != null;
    }
    public int getNbQuestionsRestantes(String userLogin) {
        if (!isJoueur(userLogin)) { return -1; }
        return this.getNbQuestionsRestantes(getJoueur(userLogin));
    }
    public int getNbQuestionsRestantes(Utilisateur user) {
        if (!this.infos.containsKey(user)) { return -1; }
        int[] c = (int []) this.infos.get(user).get("choix");
        int nombre = 0;
        for(int i = 0; i < c.length; i++) {
            if (c[i] != -1) { nombre++; }
        }
        return nombre;
    }
    public int getNbQuestions() { return this.questions.size(); }
    public ArrayList<Utilisateur> getJoueurs() { return this.joueurs; }
    public ArrayList<Choix> getChoix(int numQuestion) { 
        return this.questions.get(numQuestion).getChoix(); 
    }
    public String toString() { return this.nom; }
    public int getChoix(Utilisateur user, int numQuestion) {
        if (this.joueurs.contains(user) && numQuestion < this.questions.size()) {
            int c [] = (int []) this.infos.get(user).get("choix");
            return c[numQuestion];
        } else {
            return -1;
        }
    }
    public boolean isCorrectChoix(int numQuestion) {
        for (int i = 0; i < this.joueurs.size(); i++) {
            int choix = this.getChoix(this.joueurs.get(i), numQuestion);
            if (!questions.get(numQuestion).isCorrectChoix(choix)) {
                return false;
            }
        }
        return true;
    }
}
