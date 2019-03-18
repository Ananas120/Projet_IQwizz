package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;

import com.example.projetcoo.projet_iquizz.modele.Quizz;
import com.example.projetcoo.projet_iquizz.vue.QuizzAdapter;


public class ListeQuizz extends AppCompatActivity {

    private ArrayList liste;
    private Button lancerQuizz;
    private ListView vue;
    private ImageView retour;
    private ImageView param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_quizz);
        
        liste = new ArrayList();
        liste.add(new Quizz("Quizz complet", 40, "Toutes"));
        liste.add(new Quizz("Quizz n°2", "Categorie 1"));
        liste.add(new Quizz("Quizz n°3", "Categorie 1"));
        liste.add(new Quizz("Quizz n°4", "Categorie 2"));
        liste.add(new Quizz("Quizz n°5", "Categorie 2"));
        liste.add(new Quizz("Quizz n°6", "Categorie 3"));
        liste.add(new Quizz("Quizz n°7", "Categorie 3"));
        liste.add(new Quizz("Quizz n°8", "Categorie 4"));
        liste.add(new Quizz("Quizz n°9", "Categorie 4"));
        liste.add(new Quizz("Quizz n°10", "Categorie 5"));
        
        vue = (ListView) findViewById(R.id.liste_quizz);

        retour = (ImageView) findViewById(R.id.retour);
        param = (ImageView) findViewById(R.id.parametres);
        
        lancerQuizz = (Button) findViewById(R.id.lancer_quizz);
        
        lancerQuizz.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent questionActivity = new Intent(ListeQuizz.this, QuestionActivity.class);
                //lancerQuizz.setText("Quizz n°"+vue.getCheckedItemPosition());
                startActivity(questionActivity);
            }
        });
        retour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent ancienneActivity = new Intent(ListeQuizz.this, Game.class);
                startActivity(ancienneActivity);
            }
        });
        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(ListeQuizz.this, Compte.class);
                startActivity(paramActivity);
            }
        });
        
        QuizzAdapter adapteur = new QuizzAdapter(liste, this);
        
        vue.setAdapter(adapteur);
    }
}
