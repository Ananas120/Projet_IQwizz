package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.projetcoo.projet_iquizz.modele.Utilisateur;

public class Defi extends BDDItem {
    
    private int id;
    private String nom;
    private ArrayList<Utilisateur> joueurs;
    private ArrayList<Question> questions;
    private HashMap<String, HashMap> infos;
    
    public Defi(int id, String nom, 
                ArrayList<Utilisateur> joueurs,
                ArrayList<Question> questions) {
        this.id = id;
        this.nom = nom;
        this.joueurs = joueurs;
        this.questions = questions;
        this.infos = this.buildInfos();
    }
    
    public Defi(int id, String nom, 
                ArrayList<Utilisateur> joueurs,
                ArrayList<Question> questions, 
                HashMap<String, HashMap> infos) {
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

    private HashMap<String, HashMap> buildInfos() {
        HashMap infos = new HashMap<Utilisateur, HashMap>();
        for (int i = 0; i < this.joueurs.size(); i++) {
            HashMap infosJoueur = new HashMap();
            int[] choix = new int[this.questions.size()];
            for (int c = 0; c < choix.length; c++) { choix[c] = -1; }
            infosJoueur.put("choix", choix);
            infosJoueur.put("score", -1);
            infosJoueur.put("temps", 0);
            infosJoueur.put("date", "");
            infos.put(joueurs.get(i).getNom(), infosJoueur);
        }
        return infos;
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
        return this.infos.containsKey(login);
    }
    public boolean isFini(String userLogin) {
        if (getNbQuestionsRestantes(userLogin) == 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isFini(Utilisateur user) {
        if (getScore(user) == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean isFini() {
        for (int i = 0; i < joueurs.size(); i++) {
            if (!isFini(joueurs.get(i))) { return false; }
        }
        return true;
    }
    
    public int getNbQuestionsRestantes(String userLogin) {
        if (!isJoueur(userLogin)) { return -1; }
        int[] c = (int []) this.infos.get(userLogin).get("choix");
        int nombre = 0;
        for(int i = 0; i < c.length; i++) {
            if (c[i] == -1) { nombre++; }
        }
        return nombre;
    }
    public int getNbQuestionsRestantes(Utilisateur user) {
        return getNbQuestionsRestantes(user.getNom());
    }
    public int getNbQuestions() { return this.questions.size(); }

    public ArrayList<Utilisateur> getJoueurs() { return this.joueurs; }
    
    public ArrayList<Choix> getChoix(int numQuestion) { 
        return this.questions.get(numQuestion).getChoix(); 
    }
    public int getChoix(Utilisateur user, int numQuestion) {
        return getChoix(user.getNom(), numQuestion);
    }
    public int getChoix(String userLogin, int numQuestion) {
        if (isJoueur(userLogin) && numQuestion < this.questions.size()) {
            int c [] = (int []) this.infos.get(userLogin).get("choix");
            return c[numQuestion];
        } else {
            return -1;
        }
    }
    public int[] getChoix(Utilisateur user) {
        if (!this.infos.containsKey(user.getNom())) { return null; }
        return (int []) this.infos.get(user.getNom()).get("choix");
    }
    public int[] getChoix(String userLogin) {
        if (!this.infos.containsKey(userLogin)) { return null; }
        return (int []) this.infos.get(userLogin).get("choix");
    }
    
    public void setChoix(Utilisateur user, int numQuestion, int numChoix) {
        if (!joueurs.contains(user)) { return; }
        if (numQuestion > questions.size()) { return; }
        modifie = true;
        ( (int []) this.infos.get(user.getNom()).get("choix") )[numQuestion] = numChoix;
    }
    public void setChoix(String userLogin, int numQuestion, int numChoix) {
        if (!this.infos.containsKey(userLogin)) { return; }
        if (numQuestion > questions.size()) { return; }
        modifie = true;
        ( (int []) this.infos.get(userLogin).get("choix") )[numQuestion] = numChoix;
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
    public boolean isCorrectChoix(Utilisateur user, int numQuestion) {
        int choix = this.getChoix(user, numQuestion);
        if (questions.get(numQuestion).isCorrectChoix(choix)) {
            return true;
        } else {
            return false;
        }
    }
    
    public int getScore(Utilisateur user) {
        if (!this.joueurs.contains(user)) { return -1; }
        return (int) this.infos.get(user.getNom()).get("score");
    }
    public int getScore(String userLogin) {
        if (!this.infos.containsKey(userLogin)) { return -1; }
        return (int) this.infos.get(userLogin).get("score");
    }
    public int getTemps(Utilisateur user) {
        if (!this.joueurs.contains(user)) { return -1; }
        return (int) this.infos.get(user.getNom()).get("temps");
    }
    public int getTemps(String userLogin) {
        if (!this.infos.containsKey(userLogin)) { return -1; }
        return (int) this.infos.get(userLogin).get("temps");
    }
    public String getDate(Utilisateur user) {
        if (!this.joueurs.contains(user)) { return null; }
        return this.infos.get(user.getNom()).get("date").toString();
    }
    public String getDate(String userLogin) {
        if (!this.infos.containsKey(userLogin)) { return null; }
        return (String) this.infos.get(userLogin).get("date");
    }
    
    public String toString() {
        String description =  this.nom 
            + "\nQuestions restantes : " + this.getNbQuestionsRestantes(BDD.getInstance().getData().getJoueur())
            + "\nJoueurs : ";
        for (int i = 0; i < joueurs.size(); i++) {
            description += "\n" + joueurs.get(i).getNom();
        }
        return description;
    }
    public static boolean isIdentique(Defi d, String nomQuizz, ArrayList<Utilisateur> joueurs) {
        if (!d.getNom().equals(nomQuizz)) { return false; }
        if (d.getJoueurs().size() != joueurs.size()) { return false; }
        if (d.isFini()) { return false; }

        for (int i = 0; i < joueurs.size(); i++) {
            boolean trouve = false;
            for (int j = 0; j < d.getJoueurs().size(); j++) {
                if (d.getJoueurs().get(j).equals(joueurs.get(i))) {
                    trouve = true;
                    break;
                }
            }
            if (!trouve) { return false; }
        }
        return true;
    }
    
    public void fin(ArrayList<Utilisateur> joueurs) {
        for (int i = 0; i < joueurs.size(); i++) {
            fin(joueurs.get(i));
        }
    }
    public void fin(Utilisateur joueur) {
        calculeScore(joueur);
    }
    public void calculeScore(Utilisateur joueur) {
        int [] choix = getChoix(joueur);
        int score = 0;
        for (int c = 0; c < choix.length; c++) {
            score += questions.get(c).getChoix(choix[c]).getValeur();
        }
        this.infos.get(joueur.getNom()).put("score", score);
    }
    
    public void insert(SQLiteDatabase db) {
        if (db == null) { return; }
        
        ContentValues val = new ContentValues();
        val.put(TABLE_DEFIS_DEFIID, this.id);
        val.put(TABLE_DEFIS_NOMQUIZZ, this.nom);
        db.insert(TABLE_NAME_DEFIS, null, val);
        
        insertQuestions(db);
        
        for (int i = 0; i < joueurs.size(); i++) {
            Utilisateur user = joueurs.get(i);
            this.insertChoix(db, user);
            this.insertInfos(db, user);
        }
    }
    private void insertQuestions(SQLiteDatabase db) {
        for (int i = 0; i < this.questions.size(); i++) {
            ContentValues valQuestions = new ContentValues();
            valQuestions.put(TABLE_DEFIQUESTIONS_DEFIID, this.id);
            valQuestions.put(TABLE_DEFIQUESTIONS_QUESTIONID, questions.get(i).getID());
            valQuestions.put(TABLE_DEFIQUESTIONS_POSITION, i);
            db.insert(TABLE_NAME_DEFIQUESTIONS, null, valQuestions);
        }
    }
    private void insertChoix(SQLiteDatabase db, Utilisateur user) {
        int[] choix = getChoix(user);
        for (int c = 0; c < questions.size(); c++) {
            ContentValues valChoix = new ContentValues();
            valChoix.put(TABLE_CHOIXUTILISATEUR_DEFIID, this.id);
            valChoix.put(TABLE_CHOIXUTILISATEUR_QUESTIONID, this.questions.get(c).getID());
            valChoix.put(TABLE_CHOIXUTILISATEUR_LOGIN, user.getNom());
            valChoix.put(TABLE_CHOIXUTILISATEUR_NUMCHOIX, choix[c]);
            db.insert(TABLE_NAME_CHOIXUTILISATEUR, null, valChoix);
        }
    }
    private void insertInfos(SQLiteDatabase db, Utilisateur user) {
        ContentValues valInfos = new ContentValues();
        valInfos.put(TABLE_DEFIINFOS_DEFIID, this.id);
        valInfos.put(TABLE_DEFIINFOS_LOGIN, user.getNom());
        valInfos.put(TABLE_DEFIINFOS_TEMPS, this.getTemps(user));
        valInfos.put(TABLE_DEFIINFOS_SCORE, this.getScore(user));
        valInfos.put(TABLE_DEFIINFOS_DATE, this.getDate(user));
        db.insert(TABLE_NAME_DEFIINFOS, null, valInfos);
    }
    
    public void update(SQLiteDatabase db) {
        if (! modifie) { return; }
        for (int i = 0; i < joueurs.size(); i++) {
            Utilisateur user = joueurs.get(i);
            this.updateChoix(db, user);
            this.updateInfos(db, user);
        }
    }
    private void updateChoix(SQLiteDatabase db, Utilisateur user) {
        int[] choix = getChoix(user);
        for (int c = 0; c < questions.size(); c++) {
            ContentValues valChoix = new ContentValues();
            valChoix.put(TABLE_CHOIXUTILISATEUR_NUMCHOIX, choix[c]);
            
            String whereClause = TABLE_CHOIXUTILISATEUR_DEFIID + " = ? AND "
                + TABLE_CHOIXUTILISATEUR_QUESTIONID + " = ? AND "
                + TABLE_CHOIXUTILISATEUR_LOGIN + " = ?";
            String whereArgs[] = {""+this.id, ""+this.questions.get(c).getID(), user.getNom()};
            
            db.update(TABLE_NAME_CHOIXUTILISATEUR, valChoix, whereClause, whereArgs);
        }
    }
    private void updateInfos(SQLiteDatabase db, Utilisateur user) {
        ContentValues valInfos = new ContentValues();
        valInfos.put(TABLE_DEFIINFOS_TEMPS, this.getTemps(user));
        valInfos.put(TABLE_DEFIINFOS_SCORE, this.getScore(user));
        valInfos.put(TABLE_DEFIINFOS_DATE, this.getDate(user));
        
        String whereClause = TABLE_DEFIINFOS_DEFIID + " = ? AND "
            + TABLE_DEFIINFOS_LOGIN + " = ?";
        String whereArgs[] = {""+this.id, user.getNom()};
        
        db.update(TABLE_NAME_DEFIINFOS, valInfos, whereClause, whereArgs);

    }
}
