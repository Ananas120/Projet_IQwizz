package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;

import com.example.projetcoo.projet_iquizz.modele.Defi;
import com.example.projetcoo.projet_iquizz.modele.Utilisateur;
import com.example.projetcoo.projet_iquizz.vue.DefiAdapter;

public class Reprendre extends AppCompatActivity {
    private ArrayList<Defi> liste;
    private Button lancerQuizz;
    private ListView vue;
    private ImageView retour;
    private ImageView param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_quizz);
        
        liste = getDefis();
        
        vue = (ListView) findViewById(R.id.liste_quizz);

        retour = (ImageView) findViewById(R.id.retour);
        param = (ImageView) findViewById(R.id.parametres);
        
        lancerQuizz = (Button) findViewById(R.id.lancer_quizz);
        
        lancerQuizz.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent questionActivity = new Intent(Reprendre.this, QuestionActivity.class);
                //lancerQuizz.setText("Quizz n°"+vue.getCheckedItemPosition());
                startActivity(questionActivity);
            }
        });
        retour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent ancienneActivity = new Intent(Reprendre.this, Game.class);
                startActivity(ancienneActivity);
            }
        });
        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(Reprendre.this, Compte.class);
                startActivity(paramActivity);
            }
        });
        
        DefiAdapter adapteur = new DefiAdapter(liste, this);
        
        vue.setAdapter(adapteur);
    }
    
    public ArrayList<Defi> getDefis() {
        Utilisateur user1 = new Utilisateur("Ananas");
        Utilisateur user2 = new Utilisateur("Bananas");
        Utilisateur user3 = new Utilisateur("qlanglois");
        Utilisateur user4 = new Utilisateur("aknockaert");
        
        
        ArrayList<Utilisateur> liste1 = new ArrayList<Utilisateur>();
        liste1.add(user1);
        
        ArrayList<Utilisateur> liste2 = new ArrayList<Utilisateur>();
        liste2.add(user3);
        liste2.add(user1);
        liste2.add(user4);
        
        
        ArrayList<Utilisateur> liste3 = new ArrayList<Utilisateur>();
        liste3.add(user2);
        liste3.add(user1);
        
        
        ArrayList<Utilisateur> liste4 = new ArrayList<Utilisateur>();
        liste4.add(user1);
        liste4.add(user4);
        liste4.add(user2);
        liste4.add(user3);
        
        
        ArrayList<Defi> liste = new ArrayList<Defi>();
        liste.add(new Defi("Quizz complet", null, liste1));
        liste.add(new Defi("Defi n°2", null, liste2));
        liste.add(new Defi("Quizz n24", null, liste4));
        liste.add(new Defi("Defi n°5", null, liste3));
        
        return liste;
    }
}
