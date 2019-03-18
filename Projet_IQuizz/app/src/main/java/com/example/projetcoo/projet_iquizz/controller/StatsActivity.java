package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;
import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.*;
import com.example.projetcoo.projet_iquizz.modele.Defi;
import com.example.projetcoo.projet_iquizz.modele.Utilisateur;



public class StatsActivity extends AppCompatActivity {

    private ImageView retour;
    private ImageView param;
    private ListView listeQuizzVue;
    private ListView listeAmisVue;
    private GraphView graph;
    
    private ArrayList<Utilisateur> listeAmis;
    private ArrayList<Defi> listeQuizz;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        
        retour = (ImageView) findViewById(R.id.retour);
        param = (ImageView) findViewById(R.id.parametres);
        
        listeQuizzVue = (ListView) findViewById(R.id.liste_quizz);
        listeAmisVue = (ListView) findViewById(R.id.liste_amis);
        
        graph = (GraphView) findViewById(R.id.graph);
        
        listeQuizz = getDefis();
        listeAmis = buildListeAmis();
        
        System.out.println("Utilisateurs : " + listeAmis);
        System.out.println("Quizz : " + listeQuizz);
        
        retour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent ancienneActivity = new Intent(StatsActivity.this, Game.class);
                startActivity(ancienneActivity);
            }
        });
        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(StatsActivity.this, Compte.class);
                startActivity(paramActivity);
            }
        });

        
        
        ArrayAdapter adapteurQuizz = new ArrayAdapter(this, R.layout.ligne_item_single_choice, listeQuizz);
        
        ArrayAdapter adapteurAmis = new ArrayAdapter(this, R.layout.ligne_item_multiple_choice, listeAmis);
        
        listeQuizzVue.setAdapter(adapteurQuizz);        
        listeAmisVue.setAdapter(adapteurAmis);        
        
        BarGraphSeries<DataPoint> points = new BarGraphSeries(new DataPoint[] {
            new DataPoint(0,1),
            new DataPoint(1,5),
            new DataPoint(2,2),
            new DataPoint(3,3),
            new DataPoint(4,8)
        });
        points.setSpacing(10);

        graph.addSeries(points);
        
    }
    
    private ArrayList<Utilisateur> buildListeAmis() {
        ArrayList<Utilisateur> liste = new ArrayList();
        
        Utilisateur user1 = new Utilisateur("Ananas");
        Utilisateur user2 = new Utilisateur("Bananas");
        Utilisateur user3 = new Utilisateur("qlanglois");
        Utilisateur user4 = new Utilisateur("aknockaert");

        liste.add(user1);
        liste.add(user2);
        liste.add(user3);
        liste.add(user4);
        
        return liste;
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
