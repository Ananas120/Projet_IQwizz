package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import com.example.projetcoo.projet_iquizz.modele.BDDItem;

public class Quizz extends BDDItem {
    
    private String nom;
    private int nombreQuestions;
    private String categorie;
    
    public Quizz(String nom, String categorie) {
        this.nom = nom;
        this.nombreQuestions = 5;
        this.categorie = categorie;
    }
    
    public Quizz(String nom, String categorie, int nombreQuestions) {
        this.nom = nom;
        this.nombreQuestions = nombreQuestions;
        this.categorie = categorie;
    }
    
    public String getNom() { return this.nom; }
    public int getNbQuestions() { return this.nombreQuestions; }
    public String getCategorie() { return this.categorie; }
    
    public String toString() {
        return "Nom : " + this.nom 
            + "\nCategorie : " + this.categorie 
            + "\nNb questions : " + this.nombreQuestions;
    }
    
    public void insert(SQLiteDatabase db) {
        ContentValues val = new ContentValues();
        val.put(TABLE_QUIZZ_NOMQUIZZ, this.nom);
        val.put(TABLE_QUIZZ_CATEGORIE, this.categorie);
        val.put(TABLE_QUIZZ_NBQUESTIONS, this.nombreQuestions);
        db.insert(TABLE_NAME_QUIZZ, null, val);
    }
    public void update(SQLiteDatabase db) {}
}
