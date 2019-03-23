package com.example.projetcoo.projet_iquizz.modele;

import java.util.ArrayList;

import java.io.Serializable;

import com.example.projetcoo.projet_iquizz.modele.BDD;

public class Utilisateur implements Serializable {
    
    public static BDD bdd;
    
    private String nom;
    private int age;
    private int sexe;
    private int autoConnect;
    private ArrayList<Utilisateur> amis;
    
    public Utilisateur(String nom) {
        this.nom = nom;
        this.age = -1;
        this.sexe = -1;
        this.amis = null;
    }
    
    public Utilisateur(String nom, int age, int sexe) {
        this.nom = nom;
        this.age = age;
        this.sexe = sexe;
        this.amis = null;
    }
    
    public Utilisateur(String nom, int age, int sexe, int autoConnect) {
        this.nom = nom;
        this.age = age;
        this.sexe = sexe;
        this.autoConnect = autoConnect;
        this.amis = null;
    }

    
    public String getNom() { return this.nom; }
    public int getAge() { return this.age; }
    public int getSexe() { return this.sexe; }
    public ArrayList<Utilisateur> getAmis() {
        if (this.amis == null) {
            this.amis = BDD.getInstance().getAmis(this);
        }
        return this.amis;
    }
    public String toString() { return this.nom; }
}
