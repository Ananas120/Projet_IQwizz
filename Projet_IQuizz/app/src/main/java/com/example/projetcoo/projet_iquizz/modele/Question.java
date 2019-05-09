package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.modele.BDDItem;
import com.example.projetcoo.projet_iquizz.modele.Choix;
import com.example.projetcoo.projet_iquizz.modele.Random;

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
        this.choix = Random.shuffle(choix);;
    }

    public int getID() { return this.id; }
    public String getText() { return this.texte; }
    public ArrayList<Choix> getChoix() { return this.choix; }
    public Choix getChoix(int num) { 
        for (int i = 0; i < choix.size(); i++) {
            if (choix.get(i).getNumero() == num) {
                return choix.get(i);
            }
        }
        return null;
    }
    public Choix getChoixCorrecte() {
        for (int i = 0; i < choix.size(); i++) {
            if (choix.get(i).getValeur() == 1) {
                return choix.get(i);
            }
        }
        return null;
    }
    public int getNbChoix() { return this.choix.size(); }
    public String getExplication() { 
        if (!explication.equals("NULL")) { 
            return explication; 
        } else {
            return "Pas d'explication"; 
        }
    }
    public String getExplicationTexte() { 
        return explication; 
    }
    public String getImage() { return this.imageName; }
    public String getCategorie() { return this.categorie; }
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
    
    public static Question buildFromString(int id, String categorie, String texte) {
        ArrayList<Choix> liste_choix = new ArrayList<Choix>();
        String[] lignes = texte.split("\n");
        
        int type_ajout = 0;
        String texteQuestion = "";
        String explicationQuestion = null;
        String imageQuestion = null;
        String texteChoix = null;
        
        ArrayList<String> liste_texte_choix = new ArrayList<String>();
        
        for (int i = 0; i < lignes.length; i++) {
            if (!lignes[i].contains("|")) {
                if (lignes[i].length() < 1) { continue; 
                } else if (type_ajout == 0) { texteQuestion += lignes[i] + "\n"; 
                } else if (type_ajout == 1) { explicationQuestion += lignes[i] + "\n"; } else if (type_ajout == 2) { imageQuestion += lignes[i] + "\n"; 
                } else if (type_ajout == 3) { texteChoix += lignes[i] + "\n"; }
            } else {
                String [] infos = lignes[i].split("\\|");
                if (infos[0].equals("Q")) {
                    type_ajout = 0; texteQuestion = infos[1] + "\n";
                } else if (infos[0].equals("QE")) {
                    type_ajout = 1; explicationQuestion = infos[1] + "\n";
                } else if (infos[0].equals("QI")) {
                    type_ajout = 2; imageQuestion = infos[1] + "\n";
                } else if (infos[0].equals("C")) {
                    if (texteChoix != null) { liste_texte_choix.add(texteChoix); }
                    type_ajout = 3; texteChoix = lignes[i] + "\n";
                } else {
                    if (type_ajout == 3) { texteChoix += lignes[i] + "\n"; }
                }
            }
        }
        liste_texte_choix.add(texteChoix);
        
        for (int i = 0; i < liste_texte_choix.size(); i++) {
            liste_choix.add(Choix.buildFromString(i, liste_texte_choix.get(i)));
        }
        if (explicationQuestion == null) { explicationQuestion = "NULL"; }
        if (imageQuestion == null) { imageQuestion = "NULL"; }
        return new Question(id, texteQuestion.replace("\"", "\"\""), categorie, imageQuestion, explicationQuestion.replace("\"", "\"\""), liste_choix);
    }
    
    public ArrayList<String> getCommandes(boolean withCommandesChoix) {
        ArrayList<String> commandes = new ArrayList<String>();
        String commande = "INSERT INTO " + TABLE_NAME_QUESTIONS + " ("
            + TABLE_QUESTIONS_QUESTIONID + ", "
            + TABLE_QUESTIONS_INTITULE + ", "
            + TABLE_QUESTIONS_CATEGORIE + ", "
            + TABLE_QUESTIONS_IMAGE + ", "
            + TABLE_QUESTIONS_EXPLICATION + ") VALUES ("
            + this.id + ", \""
            + this.texte + "\", \""
            + this.categorie + "\", \""
            + this.imageName + "\", \""
            + this.explication + "\");";
        
        commandes.add(commande);
        
        if (withCommandesChoix) {
            for (int i = 0; i < choix.size(); i++) {
                commandes.add(choix.get(i).getCommande(this.id));
            }
        }
        return commandes;
    }
}