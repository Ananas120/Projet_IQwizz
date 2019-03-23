package com.example.projetcoo.projet_iquizz.modele;

public class Choix {
    
    private int numero;
    private int valeur;
    private String texte;
    private String imageName;
    
    public Choix(int num, int val, String texte, String img) {
        this.numero = num;
        this.valeur = val;
        this.texte = texte;
        this.imageName = img;
    }
    
    public Choix(String texte) {
        this.texte = texte;
    }
    
    public int getNum() { return this.numero; }
    public int getValeur() { return this.valeur; }
    public String getText() { return this.texte; }
    public boolean hasImage() { return this.imageName != null; }
    public String getImageName() { return this.imageName; }
}
