package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;

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
    
    public static Choix buildFromString(int numero, String texte) {
        String[] lignes = texte.split("\n");
        
        int type_ajout = 0;
        String texteChoix = "";
        String imageChoix = null;
        int val = 0;
        
        for (int i = 0; i < lignes.length; i++) {
            if (!lignes[i].contains("|")) {
                if (lignes[i].length() < 1) { continue; 
                } else if (type_ajout == 0) { texteChoix += lignes[i] + "\n";
                } else if (type_ajout == 1) { imageChoix += lignes[i] + "\n"; }
            } else {
                String [] infos = lignes[i].split("\\|");
                if (infos[0].equals("C")) {
                    type_ajout = 0; texteChoix = infos[1] + "\n";
                    if (infos.length == 3) {
                        if (infos[2].charAt(0) == 'V') { val = 1; } else { val = 0; }
                    }
                } else if (infos[0].equals("CI")) {
                    type_ajout = 1; imageChoix = infos[1] + "\n";
                } else {
                    if (type_ajout == 0) {
                        texteChoix += infos[0];
                        if (infos[1].charAt(0) == 'V') { val = 1; } else { val = 0; }
                    }
                    System.out.println("Valeur : " + val);
                }
            }
        }
        if (imageChoix == null) { imageChoix = "NULL"; }
        return new Choix(numero, val, texteChoix.replace("\"", "\"\""), imageChoix);
    }
    
    public ArrayList<String> getCommandes(boolean withSousCommandes) { 
        return new ArrayList<String>();
    }
    public String getCommande(int questionID) {
        return "INSERT INTO " + TABLE_NAME_CHOIX + " ("
            + TABLE_CHOIX_QUESTIONID + ", "
            + TABLE_CHOIX_INTITULE + ", "
            + TABLE_CHOIX_NUMERO + ", "
            + TABLE_CHOIX_VALEUR + ", "
            + TABLE_CHOIX_IMAGE + ") VALUES ("
            + questionID+ ", \""
            + this.texte + "\", "
            + this.numero + ", "
            + this.valeur + ", \""
            + this.imageName + "\");";
    }
}