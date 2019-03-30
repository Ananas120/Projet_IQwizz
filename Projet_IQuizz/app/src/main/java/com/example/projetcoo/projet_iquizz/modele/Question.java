package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.modele.BDDItem;
import com.example.projetcoo.projet_iquizz.modele.Choix;

public class Question extends BDDItem {
    
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
        for (int i = 0; i < choix.size(); i++) {
            if (choix.get(i).getNumero() == num) {
                if (choix.get(i).getValeur() != 0) { return true; } else { return false; }
            }
        }
        return false;
    }
    
    public void insert(SQLiteDatabase db) {
        if (db == null) { return; }
        ContentValues val = new ContentValues();
        val.put(TABLE_QUESTIONS_QUESTIONID, this.id);
        val.put(TABLE_QUESTIONS_INTITULE, this.texte);
        val.put(TABLE_QUESTIONS_IMAGE, this.imageName);
        val.put(TABLE_QUESTIONS_EXPLICATION, this.explication);
        val.put(TABLE_QUESTIONS_CATEGORIE, this.categorie);
        db.insert(TABLE_NAME_QUESTIONS, null, val);
        
        insertChoix(db);
    }
    private void insertChoix(SQLiteDatabase db) {
        for (int i = 0; i < choix.size(); i++) {
            choix.get(i).insert(db, this.id);
        }
    }
    public void update(SQLiteDatabase db) {}
}
