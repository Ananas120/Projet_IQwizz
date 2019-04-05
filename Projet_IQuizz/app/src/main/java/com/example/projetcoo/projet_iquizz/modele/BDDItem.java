package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.modele.BDD;

public abstract class BDDItem {
    
    public static final String TABLE_NAME_UTILISATEUR       = "Utilisateurs";
    public static final String TABLE_NAME_AMIS              = "Amis";
    public static final String TABLE_NAME_QUIZZ             = "Quizz";
    public static final String TABLE_NAME_DEFIS             = "Defis";
    public static final String TABLE_NAME_QUESTIONS         = "Questions";
    public static final String TABLE_NAME_CHOIX             = "Choix";
    public static final String TABLE_NAME_DEFIQUESTIONS     = "DefiQuestions";
    public static final String TABLE_NAME_DEFIINFOS         = "DefiInfos";
    public static final String TABLE_NAME_CHOIXUTILISATEUR  = "ChoixUtilisateur";
    public static final String TABLE_NAME_QUIZZEXPLICATION  = "QuizzExplication";
    
    public static final String TABLE_UTILISATEURS_LOGIN     = "Login";
    public static final String TABLE_UTILISATEURS_PASSWORD  = "Password";
    public static final String TABLE_UTILISATEURS_AGE       = "Age";
    public static final String TABLE_UTILISATEURS_SEXE      = "Sexe";
    public static final String TABLE_UTILISATEURS_AUTOCONNECT = "AutoConnect";
    
    public static final String TABLE_QUIZZ_NOMQUIZZ         = "NomQuizz";
    public static final String TABLE_QUIZZ_CATEGORIE        = "Categorie";
    public static final String TABLE_QUIZZ_NBQUESTIONS      = "NbQuestions";
    
    public static final String TABLE_QUESTIONS_QUESTIONID   = "QuestionID";
    public static final String TABLE_QUESTIONS_INTITULE     = "Intitule";
    public static final String TABLE_QUESTIONS_CATEGORIE    = "Categorie";
    public static final String TABLE_QUESTIONS_IMAGE        = "ImageFilename";
    public static final String TABLE_QUESTIONS_EXPLICATION  = "Explication";
    
    public static final String TABLE_CHOIX_QUESTIONID       = TABLE_QUESTIONS_QUESTIONID;
    public static final String TABLE_CHOIX_INTITULE         = "Intitule";
    public static final String TABLE_CHOIX_NUMERO           = "Numero";
    public static final String TABLE_CHOIX_VALEUR           = "Valeur";
    public static final String TABLE_CHOIX_IMAGE            = "ImageFilename";
    
    public static final String TABLE_DEFIS_DEFIID           = "DefiID";
    public static final String TABLE_DEFIS_NOMQUIZZ         = TABLE_QUIZZ_NOMQUIZZ;
    
    public static final String TABLE_DEFIINFOS_DEFIID       = TABLE_DEFIS_DEFIID;
    public static final String TABLE_DEFIINFOS_LOGIN        = TABLE_UTILISATEURS_LOGIN;
    public static final String TABLE_DEFIINFOS_SCORE        = "Score";
    public static final String TABLE_DEFIINFOS_DATE         = "Date";
    public static final String TABLE_DEFIINFOS_TEMPS        = "Temps";
    
    public static final String TABLE_CHOIXUTILISATEUR_DEFIID    = TABLE_DEFIS_DEFIID;
    public static final String TABLE_CHOIXUTILISATEUR_QUESTIONID    = TABLE_QUESTIONS_QUESTIONID;
    public static final String TABLE_CHOIXUTILISATEUR_LOGIN     = TABLE_UTILISATEURS_LOGIN;
    public static final String TABLE_CHOIXUTILISATEUR_NUMCHOIX  = "NumChoix";
    
    public static final String TABLE_DEFIQUESTIONS_DEFIID      = TABLE_DEFIS_DEFIID;
    public static final String TABLE_DEFIQUESTIONS_QUESTIONID  = TABLE_QUESTIONS_QUESTIONID;
    public static final String TABLE_DEFIQUESTIONS_POSITION    = "Position";
    
    public static final String TABLE_AMIS_LOGIN     = TABLE_UTILISATEURS_LOGIN;
    public static final String TABLE_AMIS_AMILOGIN  = "AmiLogin";
    
    public static final String TABLE_QUIZZEXPLICATION_NOMQUIZZ      = TABLE_QUIZZ_NOMQUIZZ;
    public static final String TABLE_QUIZZEXPLICATION_TRANCHESCORE  = "TrancheScore";
    public static final String TABLE_QUIZZEXPLICATION_EXPLICATION   = "Explication";
    
    
    
    protected boolean modifie = false;
    
    public boolean isModifie() { return modifie; }
    public abstract void insert(SQLiteDatabase db);
    public void update() { this.update(BDD.getInstance().getReadableDatabase()); }
    public abstract void update(SQLiteDatabase db);
    public abstract ArrayList<String> getCommandes(boolean withSousCommandes);

}
