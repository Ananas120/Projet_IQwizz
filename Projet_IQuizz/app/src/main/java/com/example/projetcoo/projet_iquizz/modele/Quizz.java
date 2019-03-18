package com.example.projetcoo.projet_iquizz.modele;

public class Quizz {
    
    private String nom;
    private int nombreQuestions;
    private String categorie;
    
    public Quizz(String nom, String categorie) {
        this.nom = nom;
        this.nombreQuestions = 5;
        this.categorie = categorie;
    }
    
    public Quizz(String nom, int nombreQuestions, String categorie) {
        this.nom = nom;
        this.nombreQuestions = nombreQuestions;
        this.categorie = categorie;
    }
    
    public String getNom() { return this.nom; }
    public int getNbQuestions() { return this.nombreQuestions; }
    public String getCategorie() { return this.categorie; }
    
    public String toString() {
        return "Nom : " + this.nom + "\tCategorie : " + this.categorie + "\tNb questions : " + this.nombreQuestions;
    }
}
