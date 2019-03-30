package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import com.example.projetcoo.projet_iquizz.modele.BDDItem;

public class Choix extends BDDItem {
    
    private int numero;
    private int valeur;
    private String texte;
    private String imageName;
    
    public Choix(int num, int val, String texte, String img) {
        this.numero = num;
        this.valeur = val;
        this.texte = texte;
        this.imageName = img;
    }
    
    public Choix(String texte) {
        this.texte = texte;
    }
    
    public int getNumero() { return this.numero; }
    public int getValeur() { return this.valeur; }
    public String getText() { return this.texte; }
    public boolean hasImage() { return this.imageName != null; }
    public String getImageName() { return this.imageName; }
    
    public void insert(SQLiteDatabase db) {}
    public void insert(SQLiteDatabase db, int questionID) {
        if (db == null) { return; }
        ContentValues val = new ContentValues();
        val.put(TABLE_CHOIX_QUESTIONID, questionID);
        val.put(TABLE_CHOIX_NUMERO, this.numero);
        val.put(TABLE_CHOIX_INTITULE, this.texte);
        val.put(TABLE_CHOIX_IMAGE, this.imageName);
        val.put(TABLE_CHOIX_VALEUR, this.valeur);
        db.insert(TABLE_NAME_CHOIX, null, val);
    }
    public void update(SQLiteDatabase db) {}
}
