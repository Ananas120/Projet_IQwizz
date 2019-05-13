package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import java.util.*;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.*;

public class FinQuizz extends AppCompatActivity implements OnClickListener {

    public static final String JOUEUR = "joueur";
    public static final String NUMERO = "numeroQuestion";
    
    private ImageView param;
    private TextView texte_score;
    private TableLayout tableLayout;
    private Button go_jeu;
    
    private Defi defi;
    private ArrayList<Utilisateur> joueurs; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_quizz);

        param = (ImageView) findViewById(R.id.parametres);
        texte_score = (TextView) findViewById(R.id.score_quizz);
        tableLayout = (TableLayout) findViewById(R.id.tableau_questions);
        go_jeu = (Button) findViewById(R.id.go_jeu);
        
        defi = BDD.getInstance().getData().getDefiEnCours();
        joueurs = BDD.getInstance().getData().getJoueursDefiConnectes();
        
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.YEAR);
        
        defi.fin(joueurs, date);
        
        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(FinQuizz.this, Compte.class);
                startActivity(paramActivity);
            }
        });
        go_jeu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(FinQuizz.this, Game.class);
                startActivity(paramActivity);
            }
        });
        
        for (int i = 0; i < joueurs.size(); i++) {
            afficheResultat(joueurs.get(i));
        }
    }
    
    private void afficheResultat(Utilisateur joueur) {
        ArrayList<Question> questions = defi.getQuestions();
        
        TableRow ligneNum = null;
        TableRow ligneImg = null;
        
        int hauteur = (int) getResources().getDimension(R.dimen.imageView_height);
        int largeur = (int) getResources().getDimension(R.dimen.imageView_width);
        
        TextView resultat = getTexteResultat(joueur);
        
        tableLayout.addView(resultat);
        
        for(int i = 0; i < questions.size(); i++) {
            if (i%5 == 0) {
                if (ligneNum != null) {
                    tableLayout.addView(ligneNum);
                    tableLayout.addView(ligneImg);
                }
                TableRow.LayoutParams p = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                ligneNum = new TableRow(this);
                ligneImg = new TableRow(this);
                ligneNum.setLayoutParams(p);
                ligneImg.setLayoutParams(p);
            }
            TextView numero = getTexteNumero(i+1);
            
            int choixUtilisateur = defi.getChoix(joueur, i);
            int valeur = 0;
            if (choixUtilisateur != -1) { 
                valeur = questions.get(i).getChoix(choixUtilisateur).getValeur();
            }
            
            ImageView image = getImageReponse(hauteur, largeur, valeur);
            image.setTag(joueur.getNom() + "," + i);
            
            ligneNum.addView(numero);
            ligneImg.addView(image);
        }
        tableLayout.addView(ligneNum);
        tableLayout.addView(ligneImg);
        
        String s = getResources().getString(R.string.fin_defi, defi.getNom());
        texte_score.setText(s);
    }
    
    private TextView getTexteResultat(Utilisateur joueur) {
        String resultat = getResources().getString(R.string.resultat_joueur, joueur.getNom(), defi.getScore(joueur), defi.getNbQuestions());
        
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            
        TextView texte = new TextView(this);
        texte.setText(resultat);
        texte.setLayoutParams(params);
        texte.setGravity(Gravity.CENTER);
        texte.setTextAppearance(this, R.style.DefaultTextPetit);
        return texte;
    }
    private TextView getTexteNumero(int num) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            
        TextView numero = new TextView(this);
        numero.setText("Q n°"+num);
        numero.setLayoutParams(params);
        numero.setTextAppearance(this, R.style.DefaultTextMini);
        return numero;
    }
    private ImageView getImageReponse(int hauteur, int largeur, int valeur) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
        params.height = hauteur;
        params.width = largeur;
        
        ImageView image = new ImageView(this);
        image.setLayoutParams(params);
            
        if (valeur > 0) {
            image.setImageResource(R.drawable.bon_choix);
        } else {
            image.setImageResource(R.drawable.mauvais_choix);
        }
            
        image.setOnClickListener(this);
        
        return image;
    }
    
    public void onClick(View v) {
        String tag = (String) v.getTag();
        String [] infos = tag.split(",");
        
        Intent correction = new Intent(FinQuizz.this, CorrectionQuestionActivity.class);
        correction.putExtra(JOUEUR, infos[0]);
        correction.putExtra(NUMERO, Integer.parseInt(infos[1]));
        startActivity(correction);
    }
}
