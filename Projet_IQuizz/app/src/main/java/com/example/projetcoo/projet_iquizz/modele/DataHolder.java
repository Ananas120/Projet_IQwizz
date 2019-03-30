package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.modele.*;

public class DataHolder {
    
    private static final DataHolder instance = new DataHolder();
    public static DataHolder getInstance() { return instance; }
    
    private Utilisateur joueur;
    private ArrayList<Utilisateur> amis;
    private ArrayList<Utilisateur> joueursConnectes;
    private ArrayList<Utilisateur> joueursDefi;
    private ArrayList<Quizz> quizz;
    private ArrayList<Defi> defis;
    private Defi defiEnCours;
    private Statistique statistiquesJoueur;
    
    private DataHolder() {
        this.joueur = null;
        this.amis = null;
        this.joueursConnectes = null;
        this.joueursDefi = null;
        this.quizz = quizz;
        this.defis = null;
        this.defiEnCours = null;
    }
    
    public void setJoueur(Utilisateur joueur) { 
        this.joueur = joueur;
        this.joueursConnectes = new ArrayList<Utilisateur>();
        this.joueursDefi = new ArrayList<Utilisateur>();
        this.joueursConnectes.add(joueur);
        this.joueursDefi.add(joueur);
    }
    public void setAmis(ArrayList<Utilisateur> amis) { this.amis = amis; }
    public void addJoueurDefi(Utilisateur joueur) {
        this.joueursDefi.add(joueur);
    }
    public void addJoueursDefi(ArrayList<Utilisateur> joueurs) {
        for (int i = 0; i < joueurs.size(); i++) {
            if (!joueursDefi.contains(joueurs.get(i))) {
                this.joueursDefi.add(joueurs.get(i));
            }
        }
    }
    public void setJoueursDefi(ArrayList<Utilisateur> joueurs) {
        this.joueursDefi = joueurs;
        joueursDefi.add(this.joueur);
    }
    public void clearJoueursDefi() {
        this.joueursDefi = new ArrayList<Utilisateur>();
        this.joueursDefi.add(joueur);
    }
    public void addJoueurConnecte(Utilisateur joueur) {
        this.joueursConnectes.add(joueur);
    }
    public void addJoueursConnectes(ArrayList<Utilisateur> joueurs) {
        for (int i = 0; i < joueurs.size(); i++) {
            if (!joueursConnectes.contains(joueurs.get(i))) {
                this.joueursConnectes.add(joueurs.get(i));
            }
        }
    }
    public void setQuizz(ArrayList<Quizz> quizz) { this.quizz = quizz; }
    public void setDefis(ArrayList<Defi> defis) { this.defis = defis; }
    public void addDefi(Defi defi) { this.defis.add(defi); }
    public void setDefiEnCours(Defi defi) { 
        this.defiEnCours = defi; 
        this.joueursDefi = defi.getJoueurs(); 
    }
    public void setStatistiques(Statistique stats) { this.statistiquesJoueur = stats; }
    
    public Utilisateur getJoueur() { return this.joueur; }
    public ArrayList<Utilisateur> getAmis() { return amis; }
    public ArrayList<Utilisateur> getJoueursDefi() { return joueursDefi; }
    public ArrayList<Quizz> getQuizz() { return quizz; }
    public Quizz getQuizz(String nom) {
        if (this.quizz == null) { return null; }
        for (int i = 0; i < quizz.size(); i++) {
            if (quizz.get(i).getNom().equals(nom)) {
                return quizz.get(i);
            }
        }
        return null;
    }
    public ArrayList<Defi> getDefis() { return defis; }
    public Defi getDefiEnCours() { return defiEnCours; }
    public Defi getDefi(int defiID) {
        if (this.defis == null) { return null; }
        for (int i = 0; i < defis.size(); i++) {
            if (defis.get(i).getID() == defiID) {
                return defis.get(i);
            }
        }
        return null;
    }
    public Statistique getStatistiques() { return statistiquesJoueur; }
    
    public boolean hasJoueur() { return this.joueur != null; }
    public boolean hasAmis() { return this.amis != null; }
    public boolean hasJoueursDefi() { return this.joueursDefi != null; }
    public boolean hasQuizz() { return this.quizz != null; }
    public boolean hasDefis() { return this.defis != null; }
    public boolean hasDefiEnCours() { return this.defiEnCours != null; }
    public boolean hasStatistiques() { return this.statistiquesJoueur != null; }
    
    public boolean hasDefiIdentique(String nomQuizz) {
        for (int i = 0; i < defis.size(); i++) {
            if (Defi.isIdentique(defis.get(i), nomQuizz, joueursDefi)) { return true; }
        }
        return false;
    }
    public ArrayList<Defi> getDefisFinis() {
        ArrayList<Defi> finis = new ArrayList<Defi>();
        for (int i = 0; i < defis.size(); i++) {
            if (defis.get(i).isFini()) { finis.add(defis.get(i)); }
        }
        return finis;
    }
    public ArrayList<Defi> getDefisNonFinis() {
        ArrayList<Defi> nonFinis = new ArrayList<Defi>();
        for (int i = 0; i < defis.size(); i++) {
            if (!defis.get(i).isFini()) { nonFinis.add(defis.get(i)); }
        }
        return nonFinis;
    }
    public ArrayList<Utilisateur> getJoueursDefiConnectes() {
        ArrayList<Utilisateur> joueursCo = new ArrayList<Utilisateur>();
        for(int i = 0; i < joueursDefi.size(); i++) {
            boolean co = false;
            for (int j = 0; j < joueursConnectes.size(); j++) {
                if (joueursDefi.get(i).equals(joueursConnectes.get(j))) {
                    co = true;
                    break;
                }
            }
            if (co) { joueursCo.add(joueursDefi.get(i)); }
        }
        return joueursCo;
    }
    public ArrayList<Utilisateur> getJoueursDefiNonConnectes() {
        ArrayList<Utilisateur> joueursNonCo = new ArrayList<Utilisateur>();
        for(int i = 0; i < joueursDefi.size(); i++) {
            boolean co = false;
            for (int j = 0; j < joueursConnectes.size(); j++) {
                if (joueursDefi.get(i).equals(joueursConnectes.get(j))) {
                    co = true;
                    break;
                }
            }
            if (!co) { joueursNonCo.add(joueursDefi.get(i)); }
        }
        return joueursNonCo;
    }
    
    public void deconnecte() {
        this.joueur = null;
        this.amis = null;
        this.joueursConnectes = null;
        this.joueursDefi = null;
        this.defis = null;
        this.defiEnCours = null;
    }
}
