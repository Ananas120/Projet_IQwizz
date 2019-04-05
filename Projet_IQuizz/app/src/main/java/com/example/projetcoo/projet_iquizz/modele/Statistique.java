package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.*;

import com.example.projetcoo.projet_iquizz.modele.*;

public class Statistique {
    
    public static HashMap<String, ArrayList<String>> categorieQuizz;
    private Utilisateur joueur;
    private HashMap<String, ArrayList<Integer>> quizzScores;
    private HashMap<String, ArrayList<Defi>> quizzDefis;
    
    public Statistique() {
        this.joueur = BDD.getInstance().getData().getJoueur();
        this.build(BDD.getInstance().getData().getDefisFinis());
    }
    public Statistique(Utilisateur joueur, ArrayList<Defi> defis) {
        this.joueur = joueur;
        this.build(defis);
    }
    
    public static ArrayList<String> getCategories() {
        if (categorieQuizz == null) { initCategories(); }
        ArrayList<String> cat = new ArrayList<String>();
        for (String cle : categorieQuizz.keySet()) {
            cat.add(cle);
        }
        return cat;
    }
    public static ArrayList<String> getNomQuizz() {
        if (categorieQuizz == null) { initCategories(); }
        ArrayList<String> noms = new ArrayList<String>();
        for (String cle : categorieQuizz.keySet()) {
            noms.addAll(categorieQuizz.get(cle));
        }
        return noms;
    }
    public static ArrayList<String> getNomQuizz(String categorie) {
        return categorieQuizz.get(categorie);
    }
    public static void initCategories() {
        categorieQuizz = new HashMap<String, ArrayList<String>>();
        ArrayList<Quizz> quizz = BDD.getInstance().getQuizz();
        for (int i = 0; i < quizz.size(); i++) {
            if (!categorieQuizz.containsKey(quizz.get(i).getCategorie())) {
                categorieQuizz.put(quizz.get(i).getCategorie(), new ArrayList<String>());
            }
            categorieQuizz.get(quizz.get(i).getCategorie()).add(quizz.get(i).getNom());
        }
    }
    
    private void build(ArrayList<Defi> defis) {
        quizzScores = new HashMap<String, ArrayList<Integer>>();
        quizzDefis = new HashMap<String, ArrayList<Defi>>();
        
        if (categorieQuizz == null) { initCategories(); }
        for (String quizz : getNomQuizz()) {
            quizzScores.put(quizz, new ArrayList<Integer>());
            quizzDefis.put(quizz, new ArrayList<Defi>());
        }
        
        for (Defi defi : defis) {
            String nomQuizz = defi.getNom();
            
            double score = (double) defi.getScore(joueur);
            double nbQuestion = (double) defi.getNbQuestions();
            
            Integer scorePourcent = (int) (score / nbQuestion * 100);
            
            this.quizzScores.get(nomQuizz).add(scorePourcent);
            this.quizzDefis.get(nomQuizz).add(defi);
        }
    }
        
    public int getMoyenneFromQuizz(String nomQuizz) {
        int total = 0;
        int n = quizzScores.get(nomQuizz).size();
        if (n == 0) { return 0; }
        for (int i = 0; i < n; i++) {
            total += quizzScores.get(nomQuizz).get(i);
        }
        return total / n;
    }    
    public int getMaxFromQuizz(String nomQuizz) {
        int max = 0;
        int n = quizzScores.get(nomQuizz).size();
        if (n == 0) { return 0; }
        for (int i = 0; i < n; i++) {
            if (quizzScores.get(nomQuizz).get(i) > max) {
                max = quizzScores.get(nomQuizz).get(i);
            }
        }
        return max;
    }
    public ArrayList<Integer> getScoresFromQuizz(String nomQuizz) {
        return quizzScores.get(nomQuizz);
    }
        
    public HashMap<String, Integer[]> getScores(String categorie) {
        HashMap<String, Integer[]> scores = new HashMap<String, Integer[]>();
        for(int i = 0; i < categorieQuizz.get(categorie).size(); i++) {
            int s_moy = getMoyenneFromQuizz(categorieQuizz.get(categorie).get(i));
            int s_max = getMaxFromQuizz(categorieQuizz.get(categorie).get(i));
            scores.put(categorieQuizz.get(categorie).get(i), new Integer[] {s_moy, s_max});
        }
        return scores;
    }
}
