package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.controller.MainActivity;
import com.example.projetcoo.projet_iquizz.controller.Game;

public class ListeJoueurs extends AppCompatActivity {

    private ArrayList liste;
    private Button lancerDefi;
    private ListView vue;
    private ImageView retour;
    private ImageView param;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_joueurs);
        
        liste = new ArrayList();
        liste.add("Utilisateur n°1");
        liste.add("Utilisateur n°2");
        liste.add("Utilisateur n°3");
        liste.add("Utilisateur n°4");
        liste.add("Utilisateur n°5");
        liste.add("Utilisateur n°6");
        liste.add("Utilisateur n°7");
        liste.add("Utilisateur n°8");
        liste.add("Utilisateur n°9");
        liste.add("Utilisateur n°10");
        

        lancerDefi = (Button) findViewById(R.id.lancer_defi);
        retour = (ImageView) findViewById(R.id.retour);
        param = (ImageView) findViewById(R.id.parametres);
        
        
        lancerDefi.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent newCompteActivity = new Intent(ListeJoueurs.this, Game.class);
                startActivity(newCompteActivity);
            }
        });
        retour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent ancienneActivity = new Intent(ListeJoueurs.this, Game.class);
                startActivity(ancienneActivity);
            }
        });
        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(ListeJoueurs.this, Compte.class);
                startActivity(paramActivity);
            }
        });
        
        
        vue = (ListView) findViewById(R.id.liste_joueurs);
        
        ArrayAdapter adapteur = new ArrayAdapter(this, R.layout.ligne_item_multiple_choice, liste);
        
        vue.setAdapter(adapteur);

    }
}
