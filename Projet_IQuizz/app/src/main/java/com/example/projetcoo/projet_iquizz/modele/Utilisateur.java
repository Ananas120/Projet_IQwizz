package com.example.projetcoo.projet_iquizz.modele;

public class Utilisateur {
    
    private String nom;
    
    public Utilisateur(String nom) {
        this.nom = nom;
    }
    
    public String getNom() { return this.nom; }
    public String toString() { return this.nom; }
}
