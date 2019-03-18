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
import com.example.projetcoo.projet_iquizz.modele.Question;
import com.example.projetcoo.projet_iquizz.modele.Utilisateur;

public class FinQuizz extends AppCompatActivity {

    private TableLayout tableLayout;
    private ImageView param;
    private TextView texte_score;
    
    private Defi defi;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_quizz);

        param = (ImageView) findViewById(R.id.parametres);
        
        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(FinQuizz.this, Compte.class);
                startActivity(paramActivity);
            }
        });
        
        texte_score = (TextView) findViewById(R.id.score_quizz);
        tableLayout = (TableLayout) findViewById(R.id.tableau_questions);
        
        defi = getDefi();
        
        afficheQuestions();
        
    }
    
    private void afficheQuestions() {
        int score = 0;
        ArrayList<Question> questions = defi.getQuestions();
        
        TableRow ligneNum = null;
        TableRow ligneImg = null;
        
        int hauteur = (int) getResources().getDimension(R.dimen.imageView_height);
        int largeur = (int) getResources().getDimension(R.dimen.imageView_width);
        for(int i = 0; i < questions.size(); i++) {
            if (i%4 == 0) {
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
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            
            TextView numero = new TextView(this);
            numero.setText("Qst n°"+(i+1));
            numero.setLayoutParams(params);
            numero.setTextAppearance(this, R.style.DefaultTextMini);

            
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT);
            params2.height = hauteur;
            params2.width = largeur;
            
            ImageView image = new ImageView(this);
            image.setLayoutParams(params2);
            
            if (questions.get(i).isCorrectChoice(defi.getChoice(i))) {
                score += 1;
                image.setImageResource(R.drawable.bon_choix);
            } else {
                image.setImageResource(R.drawable.mauvais_choix);
            }
            
            image.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent paramActivity = new Intent(FinQuizz.this, QuestionActivity.class);
                    startActivity(paramActivity);
                }
            });
            
            ligneNum.addView(numero);
            ligneImg.addView(image);
        }
        tableLayout.addView(ligneNum);
        tableLayout.addView(ligneImg);

        String s = "Fin du Défi " + defi.getNom();
        s += "\nScore : " + score + "/" + defi.getNbQuestions();
        texte_score.setText(s);
    }
    
    private Defi getDefi() {
        ArrayList<Question> qst = new ArrayList<Question>();
        
        for (int i = 0; i < 40; i++) {
            Question q = new Question("Question 1", null, i%3);
            qst.add(q);
        }
        Defi d = new Defi("Test Complet", qst, new Utilisateur("Ananas"));
        return d;
    }
}
