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
        public static final int HOMME = 1;
        public static final int FEMME = 2;
        public static final int AUTRE = 3;
    }
        
    public static final String NAME = "Database.db";
    public static final int VERSION = 1;
    
    private static BDD bdd = null;
            
    private final int NOMBRE_QUESTIONS_MAX = 60;
    private final String CREATE_DATABASE_FILENAME = "requetes_creation.txt";
    private final String DESTROY_DATABASE_FILENAME = "requetes_destruction.txt";
    
    private Context contexte;
    private DataHolder data;
    
    public static void init(Context context) { bdd = new BDD(context); }
    public static void init(Context context, String filename) { 
        bdd = new BDD(context, filename); 
    }
    public static BDD getInstance() { return bdd; }
    public static BDD getInstance(Context context) { return bdd; }
    public static BDD getInstance(Context context, String filename) { return bdd; }
    
    private BDD(Context context) {
        super(context, "Database.db", null, 2);
        this.contexte = context;
        this.data = DataHolder.getInstance();
    }
    private BDD(Context context, String filename) {
        super(context, "Database.db", null, 1);
        this.contexte = context;
        this.data = DataHolder.getInstance();
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
        System.out.println("Destruction de la BDD !");
        //execSQLFromFile(db, DESTROY_DATABASE_FILENAME);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onDestroy(db);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onDestroy(db);
        onCreate(db);
    }
    
    private void execSQLFromFile(SQLiteDatabase db, String filename) {
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
    private void execSQLCommandes(SQLiteDatabase db, ArrayList<String> commandes) {
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
    private String buildRequestFileFromQuizzFile(String filename) {
        String newFilename = null;
        
        return newFilename;
    }
    
    public DataHolder getData() { return this.data; }

    public boolean userExiste(String login) {
        return userExiste(this.getReadableDatabase(), login);
    }
    public int connecte(String login, String mdp, int souvenir) {
        return this.connecte(getReadableDatabase(), login, mdp, souvenir);
    }
    public int createCompte(String login, String password, String confirmPassword, int age, int sexe, int autoConnect) {
        if (!password.equals(confirmPassword)) {
            return -4;
        } else {
            if (password.length() < 5) {
                return -5;
            }
            return createCompte(getWritableDatabase(), login, password, age, sexe, autoConnect);
        }
    }
    public Defi createDefi(Quizz quizz) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Question> questions = getQuestions(db, quizz.getCategorie());
        questions = Random.sample(questions, quizz.getNbQuestions());
        ArrayList<Utilisateur> joueurs = data.getJoueursDefi();
        int identifiant = getNbDefis(db);
        Defi d = new Defi(identifiant, quizz.getNom(), joueurs, questions);
        d.insert(db);
        data.addDefi(d);
        return d;
    }
    public Utilisateur getUtilisateur(String login) {
        return this.getUtilisateur(getReadableDatabase(), login);
    }
    public ArrayList<Utilisateur> getAmis() {
        if (!data.hasAmis()) {
            this.data.setAmis(this.getAmis(getReadableDatabase(), data.getJoueur().getNom()));
        }
        return this.data.getAmis();
    }
    public ArrayList<Utilisateur> getAmis(String login) {
        if (!data.hasAmis()) {
            this.data.setAmis(this.getAmis(getReadableDatabase(), login));
        }
        return this.data.getAmis();
    }
    public ArrayList<Utilisateur> getAmis(Utilisateur user) {
        if (!data.hasAmis()) {
            this.data.setAmis(this.getAmis(getReadableDatabase(), user.getNom()));
        }
        return this.data.getAmis();
    }
    public ArrayList<Quizz> getQuizz() {
        if (!data.hasQuizz()) {
            this.data.setQuizz(this.getQuizz(getReadableDatabase()));
        }
        return this.data.getQuizz();
    }
    public Quizz getQuizz(String nom) {
        if (!data.hasQuizz()) {
            this.data.setQuizz(this.getQuizz(getReadableDatabase()));
        }
        return this.data.getQuizz(nom);

    }
    public Defi getDefi(int defiID) {
        if (!this.data.hasDefis()) {
            this.data.setDefis(getDefis(getReadableDatabase(), data.getJoueur().getNom()));
        }
        return this.data.getDefi(defiID);
    }
    public ArrayList<Defi> getDefis() {
        return getDefis(data.getJoueur().getNom());
    }
    public ArrayList<Defi> getDefis(Utilisateur user) {
        if (!this.data.hasDefis()) {
            this.data.setDefis(this.getDefis(getReadableDatabase(), user.getNom()));
        }
        return this.data.getDefis();
    }
    public ArrayList<Defi> getDefis(String login) {
        if (!this.data.hasDefis()) {
            this.data.setDefis(this.getDefis(getReadableDatabase(), login));
        }
        return this.data.getDefis();
    }
    public ArrayList<Defi> getDefisFinis() {
        return getDefisFinis(data.getJoueur().getNom());
    }
    public ArrayList<Defi> getDefisFinis(String login) {
        if (!this.data.hasDefis()) {
            this.data.setDefis(this.getDefis(getReadableDatabase(), login));
        }
        return this.data.getDefisFinis();
    }
    public ArrayList<Defi> getDefisFinis(Utilisateur user) {
        return getDefisFinis(user.getNom());
    }
    public ArrayList<Defi> getDefisNonFinis() {
        return getDefisNonFinis(data.getJoueur().getNom());
    }
    public ArrayList<Defi> getDefisNonFinis(String login) {
        if (!this.data.hasDefis()) {
            this.data.setDefis(this.getDefis(getReadableDatabase(), login));
        }
        return this.data.getDefisNonFinis();
    }
    public ArrayList<Defi> getDefisNonFinis(Utilisateur user) {
        return getDefisNonFinis(user.getNom());
    }
    public boolean hasDefiIdentique(String nomQuizz) {
        if (!this.data.hasDefis()) {
            this.data.setDefis(this.getDefis(getReadableDatabase(), data.getJoueur().getNom()));
        }
        return this.data.hasDefiIdentique(nomQuizz);
    }
    public Question getQuestion(int id) {
        return getQuestion(getReadableDatabase(), id);
    }
    public ArrayList<Question> getDefiQuestions(int defiID) {
        return this.getDefi(defiID).getQuestions();
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
    
    private boolean userExiste(SQLiteDatabase db, String login) {
        Cursor c = db.rawQuery("SELECT * FROM Utilisateurs WHERE Login = ?", new String []{login});
        if (c.getCount() == 1) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }
    private int connecte(SQLiteDatabase db, String login, String mdp, int souvenir) {
        Utilisateur user = null;
        Cursor c = db.rawQuery("SELECT * FROM Utilisateurs WHERE Login = ?", new String []{login});
        String password = null;
        if (c.getCount() == 0) {
            c.close();
            return -1;
        } else if (c.getCount() == 1) {
            c.moveToFirst();
            int autoConnect = c.getInt(c.getColumnIndex("AutoConnect"));
            password = c.getString(c.getColumnIndex("Password"));
            if (autoConnect != 1) {
                if (mdp == null || !mdp.equals(password)) {
                    c.close();
                    if (mdp == null || mdp.equals("")) {
                        return -2;
                    } else {
                        return -3;
                    }
                }
            } 
            
            String nom = c.getString(c.getColumnIndex("Login"));
            int age = c.getInt(c.getColumnIndex("Age"));
            int sexe = c.getInt(c.getColumnIndex("Sexe"));
            user = new Utilisateur(nom, password, age, sexe, autoConnect);
            if (autoConnect == 0 && souvenir == 1) { user.setAutoConnect(souvenir); }
        }
        c.close();
        if (user == null) { return -4; }
        if (this.data.hasJoueur()) {
            this.data.addJoueurConnecte(user);
        } else {
            this.data.setJoueur(user);
        }
        return 0;
    }
    private int createCompte(SQLiteDatabase db, String login, String password, int age, int sexe, int autoConnect) {
        Cursor c = db.rawQuery("SELECT * FROM "+BDDItem.TABLE_NAME_UTILISATEUR+" WHERE Login = ?", new String []{login});
        if (c.getCount() == 1) {
            c.close();
            return -6;
        }
        Utilisateur user = new Utilisateur(login, password, age, sexe, autoConnect);
        user.insert(db);
        if (this.data.hasJoueur()) {
            this.data.addJoueurConnecte(user);
        } else {
            this.data.setJoueur(user);
        }
        return 0;
    }
    private Utilisateur getUtilisateur(SQLiteDatabase db, String login) {
        Utilisateur user = null;
        Cursor c = db.rawQuery("SELECT * FROM Utilisateurs WHERE Login = ?", new String []{login});
        c.moveToFirst();
        if (c.getCount() == 1) {
            String nom = c.getString(c.getColumnIndex("Login"));
            String mdp = null; 
            int age = c.getInt(c.getColumnIndex("Age"));
            int sexe = c.getInt(c.getColumnIndex("Sexe"));
            int autoConnect = c.getInt(c.getColumnIndex("AutoConnect"));
            user = new Utilisateur(nom, mdp, age, sexe, autoConnect);
        }
        c.close();
        return user;
    }
    private ArrayList<Utilisateur> getAmis(SQLiteDatabase db, String login) {
        ArrayList<Utilisateur> amis = new ArrayList<Utilisateur>();
        Cursor c = db.rawQuery("SELECT * FROM Amis WHERE Login = ?", new String []{login});
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            String nouveauNom = c.getString(c.getColumnIndex("AmiLogin"));
            amis.add(this.getUtilisateur(db, nouveauNom));
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
            HashMap<String, HashMap> infos = new HashMap<String, HashMap>();
            for (int i = 0; i < joueurs.size(); i++) {
                HashMap infosJoueur = getDefiInfos(db, defiID, joueurs.get(i));
                int[] choix = getDefiChoix(db, defiID, joueurs.get(i), questions);
                infosJoueur.put("choix", choix);
                
                infos.put(joueurs.get(i).getNom(), infosJoueur);
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
    
    private int getNbDefis(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM Defis;", null);
        int nb = c.getCount();
        c.close();
        return nb;
    }
    private int getNbQuestions(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM Questions;", null);
        int nb = c.getCount();
        c.close();
        return nb;
    }
    
    public Statistique getStatistiques(Utilisateur user) {
        Statistique stat = null;
        
        return stat;
    }
}
