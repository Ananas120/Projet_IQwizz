package com.example.projetcoo.projet_iquizz.modele;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.modele.BDD;
import com.example.projetcoo.projet_iquizz.modele.BDDItem;

public class Utilisateur extends BDDItem {
    
    private String nom;
    private String password;
    private int age;
    private int sexe;
    private int autoConnect;
    private ArrayList<Utilisateur> amis;
    private Statistique stats;
    
    public Utilisateur(String nom) {
        this.nom = nom;
        this.password = "";
        this.age = -1;
        this.sexe = -1;
        this.amis = null;
    }
    
    public Utilisateur(String nom, String password, int age, int sexe) {
        this.nom = nom;
        this.password = password;
        this.age = age;
        this.sexe = sexe;
        this.amis = null;
    }
    
    public Utilisateur(String nom, String password, int age, int sexe, int autoConnect) {
        this.nom = nom;
        this.password = password;
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
    public Statistique getStats() {
        return BDD.getInstance().getStatistiques(this);
    }
    
    public void setNom(String nom) { this.nom = nom; modifie = true; }
    public void setPassword(String ancien, String nouveau) {
        if (this.password.equals(ancien)) { this.password = nouveau; modifie = true; }
    }
    public void setAge(int age) { this.age = age; modifie = true; }
    public void setSexe(int sexe) { this.sexe = sexe; modifie = true; }
    public void setAutoConnect(int souvenir) {this.autoConnect = souvenir; modifie = true; }
    public int addAmi(String login) {
        if (this.amis == null) { 
            this.amis = BDD.getInstance().getAmis(this); 
        }
        if (login.equals(this.nom)) { return -1; }
        if (getPosAmi(login) != -1) { return -2; }
        Utilisateur nouvelAmi = BDD.getInstance().getUtilisateur(login);
        
        if (nouvelAmi != null) {
            addAmi(BDD.getInstance().getWritableDatabase(), login);
            this.amis.add(nouvelAmi);
            return 0;
        } else {
            return -3;
        }
    }
    private void addAmi(SQLiteDatabase db, String login) {
        ContentValues val1 = new ContentValues();
        val1.put(TABLE_AMIS_LOGIN, this.nom);
        val1.put(TABLE_AMIS_AMILOGIN, login);
        
        ContentValues val2 = new ContentValues();
        val2.put(TABLE_AMIS_LOGIN, login);
        val2.put(TABLE_AMIS_AMILOGIN, this.nom);
        db.insert(TABLE_NAME_AMIS, null, val1);
        db.insert(TABLE_NAME_AMIS, null, val2);
    }
    public void removeAmi(Utilisateur user) {
        if (this.amis == null) { 
            this.amis = BDD.getInstance().getAmis(this); 
        }
        removeAmi(user.getNom());
    }
    public void removeAmi(String login) {
        if (this.amis == null) { 
            this.amis = BDD.getInstance().getAmis(this); 
        }
        int pos = getPosAmi(login);
        if (pos != -1) {
            removeAmi(BDD.getInstance().getWritableDatabase(), login);
            this.amis.remove(pos);
        }
    }
    private void removeAmi(SQLiteDatabase db, String login) {
        String whereClause = TABLE_AMIS_LOGIN + " = ? AND "
            + TABLE_AMIS_AMILOGIN + " = ?";
        
        String[] whereArgs1 = {this.nom, login};
        String[] whereArgs2 = {login, this.nom};
        
        db.delete(TABLE_NAME_AMIS, whereClause, whereArgs1);
        db.delete(TABLE_NAME_AMIS, whereClause, whereArgs2); 
    }
    public int getPosAmi(String login) {
        for (int i = 0; i < amis.size(); i++) {
            if (amis.get(i).getNom().equals(login)) {
                return i;
            }
        }
        return -1;
    }
    
    public String toString() { 
        return "Nom : " + this.nom;
    }
    public boolean equals(Utilisateur user) {
        return this.nom.equals(user.getNom());
    }
    
    public void insert(SQLiteDatabase db) {
        if (db == null) { return; }
        ContentValues val = new ContentValues();
        val.put(TABLE_UTILISATEURS_LOGIN, this.nom);
        val.put(TABLE_UTILISATEURS_PASSWORD, this.password);
        val.put(TABLE_UTILISATEURS_AGE, this.age);
        val.put(TABLE_UTILISATEURS_SEXE, this.sexe);
        val.put(TABLE_UTILISATEURS_AUTOCONNECT, this.autoConnect);
        db.insert(TABLE_NAME_UTILISATEUR, null, val);
    }
    public void update(SQLiteDatabase db) {
        if (!modifie) { return; }
        ContentValues val = new ContentValues();
        val.put(TABLE_UTILISATEURS_PASSWORD, this.password);
        val.put(TABLE_UTILISATEURS_AGE, this.age);
        val.put(TABLE_UTILISATEURS_SEXE, this.sexe);
        val.put(TABLE_UTILISATEURS_AUTOCONNECT, this.autoConnect);
        
        String whereClause = TABLE_UTILISATEURS_LOGIN + " = ?";
        String[] whereArgs = {this.nom};
        
        db.update(TABLE_NAME_UTILISATEUR, val, whereClause, whereArgs);
    }
    public ArrayList<String> getCommandes(boolean withSousCommandes) { 
        return new ArrayList<String>();
    }
}
