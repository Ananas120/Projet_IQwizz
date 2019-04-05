package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.modele.BDDItem;

public class Quizz extends BDDItem {
    
    public static int NOMBRE_QUIZZ_CATEGORIE = 4;
    public static String[] NOMS_QUIZZ = {"Speed quizz", "Quizz examen", "Quizz long", "Quizz complet"};
    public static String[] TYPES = {"Speed", "Examen", "Long", "Complet"};
    public static int [] NOMBRE_QUESTIONS_QUIZZ = {5, 20, 40, 60};
    
    public static String[] getNoms(String categorie) {
        String[] noms = new String[NOMS_QUIZZ.length];
        for (int i = 0; i < noms.length; i++) {
            noms[i] = NOMS_QUIZZ[i] + " " + categorie;
        }
        return noms;
    }
    public static ArrayList<Quizz> BuildQuizzFromCategorie(String categorie) {
        ArrayList<Quizz> quizz = new ArrayList<>();
        String [] noms = getNoms(categorie);
        for (int i = 0; i < noms.length; i++) {
            quizz.add(new Quizz(noms[i], categorie, NOMBRE_QUESTIONS_QUIZZ[i]));
        }
        return quizz;
    }
    
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
    public ArrayList<String> getCommandes(boolean withSousCommandes) { 
        ArrayList<String> commandes = new ArrayList<String>();
        String commande = "INSERT INTO " + TABLE_NAME_QUIZZ + " ("
            + TABLE_QUIZZ_NOMQUIZZ + ", "
            + TABLE_QUIZZ_CATEGORIE + ", "
            + TABLE_QUIZZ_NBQUESTIONS + ") VALUES (\""
            + this.nom + "\", \""
            + this.categorie + "\", "
            + this.nombreQuestions + ");";
        commandes.add(commande);
        return commandes;
    }
}
