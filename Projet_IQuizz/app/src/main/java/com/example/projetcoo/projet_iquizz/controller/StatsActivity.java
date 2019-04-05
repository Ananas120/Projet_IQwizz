package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.graphics.*;
import android.view.View;
import android.view.*;
import android.view.View.*;
import android.widget.AdapterView.*;
import android.widget.*;
import android.content.Intent;

import java.util.*;

import com.example.projetcoo.projet_iquizz.R;
import com.jjoe64.graphview.*;
import com.jjoe64.graphview.series.*;
import com.jjoe64.graphview.helper.*;
import com.example.projetcoo.projet_iquizz.modele.*;


public class StatsActivity extends AppCompatActivity {

    public static int[] COULEURS = {Color.CYAN, Color.RED, Color.GREEN, Color.BLUE, Color.WHITE};
    
    private ImageView retour;
    private ImageView param;
    private ListView listeCategoriesVue;
    private ListView listeAmisVue;
    private GraphView graph;
    
    private ArrayList<Utilisateur> listeAmis;
    private boolean[] amisSelectionnes;
    private ArrayList<String> listeCategories;
    private Utilisateur joueur;
    private int categorieIndex;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        
        retour = (ImageView) findViewById(R.id.retour);
        param = (ImageView) findViewById(R.id.parametres);
        listeCategoriesVue = (ListView) findViewById(R.id.liste_categories);
        listeAmisVue = (ListView) findViewById(R.id.liste_amis);
        graph = (GraphView) findViewById(R.id.graph);
        
        joueur = BDD.getInstance().getData().getJoueur();
        listeCategories = Statistique.getCategories();
        listeAmis = BDD.getInstance().getAmis();
        amisSelectionnes = new boolean[listeAmis.size()];
        for (int i = 0; i < amisSelectionnes.length; i++) { amisSelectionnes[i] = false; }
        categorieIndex = -1;
        
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
        listeCategoriesVue.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int pos, long id) {
                if (pos == categorieIndex) { return; }
                categorieIndex = pos;
                buildGraph(getAmisSelectionnes(), listeCategories.get(pos));
            }
        });
        listeAmisVue.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int pos, long id) {
                if (amisSelectionnes[pos]) {
                    amisSelectionnes[pos] = false;
                } else {
                    amisSelectionnes[pos] = true;
                }
                if (categorieIndex == -1) { return; }
                buildGraph(getAmisSelectionnes(), listeCategories.get(categorieIndex));
            }
        });

        ArrayAdapter adapteurQuizz = new ArrayAdapter(this, R.layout.ligne_item_single_choice, listeCategories);
        
        ArrayAdapter adapteurAmis = new ArrayAdapter(this, R.layout.ligne_item_multiple_choice, listeAmis);
        
        listeCategoriesVue.setAdapter(adapteurQuizz);        
        listeAmisVue.setAdapter(adapteurAmis);
        
        initGraph();
    }
    
    private void initGraph() {
        graph.getViewport().setYAxisBoundsManual(true);
        
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);
        
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getLegendRenderer().setTextColor(Color.WHITE);
        
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(110);
        graph.getGridLabelRenderer().setNumHorizontalLabels(4);
        graph.getGridLabelRenderer().setLabelHorizontalHeight(200);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);

        graph.setTitleColor(Color.WHITE);
    }
    
    private ArrayList<Utilisateur> getAmisSelectionnes() {
        ArrayList<Utilisateur> selectionnes = new ArrayList<>();
        selectionnes.add(joueur);
        for (int i = 0; i < amisSelectionnes.length; i++) {
            if (amisSelectionnes[i]) {
                selectionnes.add(listeAmis.get(i));
            }
        }
        return selectionnes;
    }
    private void buildGraph(ArrayList<Utilisateur> joueurs, String categorie) {
        if (categorie == null) { return; }
        graph.removeAllSeries();
        
        int nbJoueurs = joueurs.size();

        String[] types = Quizz.NOMS_QUIZZ;
        String[] nomsQuizz = Quizz.getNoms(categorie);
        
        for (int i = 0; i < nbJoueurs && i < COULEURS.length; i++) {
            HashMap<String, Integer[]> scores = joueurs.get(i).getStats().getScores(categorie);
            DataPoint[] points = new DataPoint[Quizz.NOMBRE_QUIZZ_CATEGORIE];
            for (int q = 0; q < nomsQuizz.length; q++) {
                int moyenne = scores.get(nomsQuizz[q])[0];
                int max = scores.get(nomsQuizz[q])[1];
                points[q] = new DataPoint(q, moyenne);
            }
            BarGraphSeries<DataPoint> serie = new BarGraphSeries(points);
            
            serie.setSpacing(15);
            serie.setTitle(joueurs.get(i).getNom());
            serie.setAnimated(true);
            serie.setColor(COULEURS[i]);
            
            serie.setDrawValuesOnTop(true);
            serie.setValuesOnTopColor(Color.WHITE);
            
            graph.addSeries(serie);
        }
        
        graph.setTitle("Résultats moyens pour la catégorie " + categorie);
        
        StaticLabelsFormatter labels = new StaticLabelsFormatter(graph);
        labels.setHorizontalLabels(Quizz.TYPES);

        graph.getGridLabelRenderer().setLabelFormatter(labels);
    }
}
