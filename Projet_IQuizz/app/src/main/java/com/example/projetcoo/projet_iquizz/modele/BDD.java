package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import android.content.Context;

public class BDD extends SQLiteOpenHelper {
    public final String CREATE_TABLE_USER = "CREATE TABLE Utilisateurs ("
        + "Identifiant TEXT PRIMARY KEY, "
        + "Password TEXT NOT NULL, "
        + "Age INTEGER NOT NULL);";
    public final String CREATE_TABLE_SCORE = "CREATE TABLE Scores ("
        + "UtilisateurID TEXT NOT NULL, "
        + "Categorie TEXT NOT NULL, "
        + "Explication TEXT, "
        + "Valeur INTEGER DEFAULT 0, "
        + "Tentative INTEGER DEFAULT 0);";
    public final String CREATE_TABLE_QUESTION = "CREATE TABLE Questions ("
        + "ID TEXT PRIMARY KEY, "
        + "Categorie TEXT NOT NULL, "
        + "Intitule TEXT NOT NULL, "
        + "FichierImage TEXT, "
        + "Age INTEGER NOT NULL, "
        + "Expliquation TEXT);";
    public final String CREATE_TABLE_CHOIX = "CREATE TABLE Choix ("
        + "Numero INTEGER NOT NULL, "
        + "QuestionID TEXT NOT NULL, "
        + "Intitule TEXT DEFAULT '', "
        + "FichierImage TEXT, "
        + "Score INTEGER DEFAULT 0);";
            
    public String NAME = null;
    public int VERSION;
    private SQLiteDatabase bdd = null;
    
    public final Map<String, ArrayList> TABLES = initMap();
    
    public BDD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        NAME = name;
        VERSION = version;
        initMap();
    }
    
    private static Map initMap() {
        ArrayList infosUsers = new ArrayList();
        infosUsers.add("Identifiant");
        infosUsers.add("Password");
        infosUsers.add("Age");
        
        ArrayList infosScores = new ArrayList();
        infosScores.add("UtilisateurID");
        infosScores.add("Categorie");
        infosScores.add("Score");
        infosScores.add("Tentative");
        
        ArrayList infosQuestions = new ArrayList();
        infosQuestions.add("ID");
        infosQuestions.add("Categorie");
        infosQuestions.add("Intitule");
        infosQuestions.add("FichierImage");
        infosQuestions.add("Age");
        infosQuestions.add("Explication");
        
        ArrayList infosChoix = new ArrayList();
        infosChoix.add("QuestionID");
        infosChoix.add("Intitule");
        infosChoix.add("Score");
        infosChoix.add("FichierImage");
        
        Map<String, ArrayList> tables = new HashMap<String, ArrayList>();
        tables.put("Utilisateurs", infosUsers);
        tables.put("Scores", infosScores);
        tables.put("Questions", infosQuestions);
        tables.put("Choix", infosChoix);
        
        return tables;
    }
    
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_SCORE);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_CHOIX);
        
        this.bdd = db;
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onCreate(db);
    }
}
