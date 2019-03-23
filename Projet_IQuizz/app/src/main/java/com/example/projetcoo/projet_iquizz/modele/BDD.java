package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.*;
import android.database.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.io.*;

import android.content.Context;

import com.example.projetcoo.projet_iquizz.modele.*;


public class BDD extends SQLiteOpenHelper {
    
    public static final class Sexe {
        public static final int HOMME = 0;
        public static final int FEMME = 1;
        public static final int AUTRE = 2;
    }
        
    public static final String NAME = "Database.db";
    public static final int VERSION = 0;
    
    private static BDD bdd = null;
            
    private final int NOMBRE_QUESTIONS_MAX = 60;
    private final String CREATE_DATABASE_FILENAME = "requetes_creation.txt";
    private final String DESTROY_DATABASE_FILENAME = "requetes_destruction.txt";
    
    private Context contexte;
    
    public static BDD getInstance() { return bdd; }
    public static BDD getInstance(Context context) {
        if (bdd == null) {
            bdd = new BDD(context); 
        }
        return bdd; 
    }
    public static BDD getInstance(Context context, String filename) {
        if (bdd == null) {
            bdd = new BDD(context, filename); 
        }
        return bdd; 
    }
    
    private BDD(Context context) {
        super(context, "Database.db", null, 2);
        this.contexte = context;
    }
    private BDD(Context context, String filename) {
        super(context, "Database.db", null, 2);
        this.contexte = context;
        this.remplisBDD(this.getWritableDatabase(), filename);
    }
    private void remplisBDD(SQLiteDatabase db, String filename) {
        if (filename.equals(CREATE_DATABASE_FILENAME) || filename.equals(DESTROY_DATABASE_FILENAME)) {
            return;
        } else if (filename.contains("all")) {
            String [] files = null;
            try {
                files = contexte.getAssets().list("");
            } catch (IOException e) {
                files = new String[0];
                System.err.println("Erreur lors du chargement des fichiers Assets !");
            }
            for (int i = 0; i < files.length; i++) {
                this.remplisBDD(db, files[i]);
            }
        } else if (filename.contains("requetes")) {
            this.execSQLFromFile(db, filename);
        } else if (filename.contains("quizz")) {
            String newFile = this.buildRequestFileFromQuizzFile(filename);
            this.execSQLFromFile(db, newFile);
        }
    }

    public void onCreate(SQLiteDatabase db) {
        execSQLFromFile(db, CREATE_DATABASE_FILENAME);
    }
    private void onDestroy(SQLiteDatabase db) {
        execSQLFromFile(db, DESTROY_DATABASE_FILENAME);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onDestroy(db);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onDestroy(db);
        onCreate(db);
    }
    
    public void execSQLFromFile(SQLiteDatabase db, String filename) {
        BufferedReader fichier;
        ArrayList<String> liste_commandes = null; 
        try {
            fichier =new BufferedReader(new InputStreamReader(contexte.getAssets().open(filename)));
            
            liste_commandes = new ArrayList<String>();
            
            String ligne;
            String commande = "";
            
            while ((ligne = fichier.readLine()) != null) {
                if (ligne.contains(";")) {
                    liste_commandes.add(commande + ligne);
                    commande = "";
                } else if (!ligne.equals("")) {
                    commande += ligne;
                } 
            }
            fichier.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        execSQLCommandes(db, liste_commandes);
    }
    public void execSQLCommandes(SQLiteDatabase db, ArrayList<String> commandes) {
        if (commandes != null) {
            for (int i = 0; i < commandes.size(); i++) {
                try {
                    db.execSQL(commandes.get(i));
                } catch (SQLiteException e) {
                    System.err.println("La commande " + commandes.get(i) + " a échouée !");
                }
            }
        }
    }
    public String buildRequestFileFromQuizzFile(String filename) {
        String newFilename = null;
        
        return newFilename;
    }
    
    public ArrayList<Utilisateur> getAmis(String login) {
        return this.getAmis(getReadableDatabase(), login;
    }
    public ArrayList<Utilisateur> getAmis(Utilisateur user) {
        return this.getAmis(getReadableDatabase(), user.getNom());
    }
    public Utilisateur getUtilisateur(String login) {
        return this.getUtilisateur(getReadableDatabase(), login);
    }
    public ArrayList<Quizz> getQuizz(SQLiteDatabase db) {
        return this.getQuizz(getReadableDatabase());
    }
    public Quizz getQuizz(String nom) {
        return this.getQuizz(getReadableDatabase(), nom);
    }
    public Defi getDefi(int defiID) {
        return getDefi(getReadableDatabase(), defiID);
    }
    public ArrayList<Defi> getDefis(Utilisateur user) {
        return getDefis(getReadableDatabase(), user.getNom());
    }
    public ArrayList<Defi> getDefis(String login) {
        return getDefis(getReadableDatabase(), login);
    }
    public Question getQuestion(int id) {
        return getQuestion(getReadableDatabase(), id);
    }
    public ArrayList<Question> getDefiQuestions(int defiID) {
        return getDefiQuestions(getReadableDatabase(), defiID);
    }
    public ArrayList<Question> getQuestions(Quizz quizz) {
        ArrayList<Question> questions = getQuestions(quizz.getCategorie());
        return Random.sample(questions, quizz.getNbQuestions());
    }
    public ArrayList<Question> getQuestions(String categorie) {
        return getQuestions(getReadableDatabase(), categorie);
    }
    public ArrayList<Choix> getChoix(Question question) {
        return getChoix(getReadableDatabase(), question.getID());
    }

    
    private Utilisateur getUtilisateur(SQLiteDatabase db, String login) {
        Utilisateur user = null;
        Cursor c = db.rawQuery("SELECT * FROM Utilisateurs WHERE Login = ?", new String []{login});
        c.moveToFirst();
        if (c.getCount() == 1) {
            String nom = c.getString(c.getColumnIndex("Login"));
            int age = c.getInt(c.getColumnIndex("Age"));
            int sexe = c.getInt(c.getColumnIndex("Sexe"));
            int autoConnect = c.getInt(c.getColumnIndex("AutoConnect"));
            user = new Utilisateur(nom, age, sexe, autoConnect);
        }
        c.close();
        return user;
    }
    private ArrayList<Utilisateur> getAmis(SQLiteDatabase db, String login) {
        ArrayList<Utilisateur> amis = new ArrayList<Utilisateur>();
        Cursor c = db.rawQuery("SELECT AmiLogin FROM Amis WHERE Login = ?", new String []{login});
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            this.getUtilisateur(db, c.getString(c.getColumnIndex("AmiLogin")));
            c.moveToNext();
        }
        c.close();
        return amis;
    }
    private ArrayList<Quizz> getQuizz(SQLiteDatabase db) {
        ArrayList<Quizz> quizz = new ArrayList<Quizz>();
        Cursor c = db.rawQuery("SELECT * FROM Quizz;", null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            String nom = c.getString(c.getColumnIndex("NomQuizz"));
            String cat = c.getString(c.getColumnIndex("Categorie"));
            int nombre = c.getInt(c.getColumnIndex("NbQuestions"));
            if (nombre == -1) { nombre = getNbQuestions(db, cat); }
            quizz.add(new Quizz(nom, cat, nombre));
            c.moveToNext();
        }
        c.close();
        return quizz;
    }
    private Quizz getQuizz(SQLiteDatabase db, String nom) {
        Cursor c = db.rawQuery("SELECT NomQuizz FROM Quizz WHERE NomQuizz = ?;", new String[]{nom});
        Quizz q = null;
        c.moveToFirst();
        if (c.getCount() == 1) {
            String cat = c.getString(c.getColumnIndex("Categorie"));
            int nombre = c.getInt(c.getColumnIndex("NbQuestions"));
            if (nombre == -1) { nombre = getNbQuestions(db, cat); }
            q = new Quizz(nom, cat, nombre);
        }
        c.close();
        return q;
    }    
    private int getNbQuestions(SQLiteDatabase db, String categorie) {
        Cursor c = null;
        if (!categorie.equals("Toutes")) {
            c = db.rawQuery("SELECT * FROM Questions WHERE Categorie = ?;", new String[] {categorie});
        } else {
            c = db.rawQuery("SELECT * FROM Questions;", null);
        }
        int nb = c.getCount();
        c.close();
        return Math.min(nb, NOMBRE_QUESTIONS_MAX);
    }
    private Defi getDefi(SQLiteDatabase db, int defiID) {
        Defi defi = null;
        Cursor c = db.rawQuery("SELECT * FROM Defis WHERE DefiID = ?;", new String[] {""+defiID});
        
        if (c.getCount() == 1) {
            c.moveToFirst();
            String nomQuizz = c.getString(c.getColumnIndex("NomQuizz"));
            ArrayList<Utilisateur> joueurs = getDefiJoueurs(db, defiID);
            ArrayList<Question> questions = getDefiQuestions(db, defiID);
            HashMap<Utilisateur, HashMap> infos = new HashMap<Utilisateur, HashMap>();
            for (int i = 0; i < joueurs.size(); i++) {
                System.out.println("Joueur : " + joueurs.get(i) + " defi ID : " + defiID);
                HashMap infosJoueur = getDefiInfos(db, defiID, joueurs.get(i));
                int[] choix = getDefiChoix(db, defiID, joueurs.get(i), questions);
                infosJoueur.put("choix", choix);
                
                infos.put(joueurs.get(i), infosJoueur);
            }
            defi = new Defi(defiID, nomQuizz, joueurs, questions, infos);
        }
        c.close();
        return defi;
    }
    private ArrayList<Defi> getDefis(SQLiteDatabase db, String login) {
        ArrayList<Defi> defis = new ArrayList<Defi>();
        Cursor c = db.rawQuery("SELECT * FROM DefiInfos WHERE Login = ?", new String[] {login});
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int defiID = c.getInt(c.getColumnIndex("DefiID"));
            defis.add(getDefi(db, defiID));
            c.moveToNext();
        }
        c.close();
        return defis;
    }
    private ArrayList<Utilisateur> getDefiJoueurs(SQLiteDatabase db, int defiID) {
        ArrayList<Utilisateur> joueurs = new ArrayList<Utilisateur>();
        Cursor c = db.rawQuery("SELECT Login FROM DefiInfos WHERE DefiID = ?;", new String[] {""+defiID});
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            joueurs.add(getUtilisateur(db, c.getString(c.getColumnIndex("Login"))));
            c.moveToNext();
        }
        c.close();
        return joueurs;
    }
    private int[] getDefiChoix(SQLiteDatabase db, int defiID, Utilisateur user, ArrayList<Question> questions) {
        int[] choix = new int[questions.size()];
        for (int i = 0; i < questions.size(); i++) { choix[i] = questions.get(i).getID(); }
        
        Cursor c = db.rawQuery("SELECT * FROM ChoixUtilisateur WHERE DefiID = ? AND Login = ?", new String[] {""+defiID, user.getNom()});
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int questionID = c.getInt(c.getColumnIndex("QuestionID"));
            int numChoix = c.getInt(c.getColumnIndex("NumChoix"));
            for (int j = 0; j < choix.length; j++) {
                if (choix[j] == questionID) { choix[j] = numChoix; break; }
            }
            c.moveToNext();
        }
        return choix;
    }
    private HashMap getDefiInfos(SQLiteDatabase db, int defiID, Utilisateur user) {
        HashMap infos = null;
        Cursor c = db.rawQuery("SELECT * FROM DefiInfos WHERE DefiID = ? AND Login = ?;", new String[] {""+defiID, user.getNom()});
        if (c.getCount() == 1) {
            c.moveToFirst();
            infos = new HashMap();
            
            int score = c.getInt(c.getColumnIndex("Score"));
            int temps = c.getInt(c.getColumnIndex("Temps"));
            String date = c.getString(c.getColumnIndex("Date"));
            
            infos.put("score", score);
            infos.put("temps", temps);
            infos.put("date", date);
        }
        c.close();
        return infos;
    }
    
    private ArrayList<Question> getDefiQuestions(SQLiteDatabase db, int defiID) {
        ArrayList<Question> questions = new ArrayList<Question>();
        Cursor c = db.rawQuery("SELECT * FROM DefiQuestions WHERE DefiID = ?", new String[] {""+defiID});
        
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int questionID = c.getInt(c.getColumnIndex("QuestionID"));
            questions.add(getQuestion(db, questionID));
            c.moveToNext();
        }

        c.close();
        return questions;
    }
    private Question getQuestion(SQLiteDatabase db, int id) {
        Cursor c = db.rawQuery("SELECT * FROM Questions WHERE QuestionID = ?;", new String[] {"" + id});
        Question q = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            String nom = c.getString(c.getColumnIndex("Intitule"));
            String cat = c.getString(c.getColumnIndex("Categorie"));
            String img = c.getString(c.getColumnIndex("ImageFilename"));
            String expl = c.getString(c.getColumnIndex("Explication"));
            ArrayList<Choix> choix = getChoix(db, id);
            
            q = new Question(id, nom, cat, img, expl, choix);
        }
        c.close();
        return q;
    }
    private ArrayList<Question> getQuestions(SQLiteDatabase db, String categorie) {
        ArrayList<Question> questions = new ArrayList<Question>();
        Cursor c;
        if (categorie.equals("Toutes")) {
            c = db.rawQuery("SELECT * FROM Questions;", null);
        } else {
            c = db.rawQuery("SELECT * FROM Questions WHERE Categorie = ?;", new String[] {categorie});;
        }
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            
            int id = c.getInt(c.getColumnIndex("QuestionID"));
            String nom = c.getString(c.getColumnIndex("Intitule"));
            String cat = c.getString(c.getColumnIndex("Categorie"));
            String img = c.getString(c.getColumnIndex("ImageFilename"));
            String expl = c.getString(c.getColumnIndex("Explication"));
            ArrayList<Choix> choix = getChoix(db, id);
            
            questions.add(new Question(id, nom, cat, img, expl, choix));
            c.moveToNext();
        }
        c.close();
        return questions;
    }
    private ArrayList<Choix> getChoix(SQLiteDatabase db, int questionID) {
        ArrayList<Choix> choix = new ArrayList<Choix>();
        Cursor c = db.rawQuery("SELECT * FROM Choix WHERE QuestionID = ?;", new String[] {""+questionID});;
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++) {
            int num = c.getInt(c.getColumnIndex("Numero"));
            int val = c.getInt(c.getColumnIndex("Valeur"));
            String nom = c.getString(c.getColumnIndex("Intitule"));
            String img = c.getString(c.getColumnIndex("ImageFilename"));
            choix.add(new Choix(num, val, nom, img));
            c.moveToNext();
        }
        c.close();
        return choix;
    }
    
    public Statistique getStatistiques(Utilisateur user) {
        Statistique stat = null;
        
        return stat;
    }
}
