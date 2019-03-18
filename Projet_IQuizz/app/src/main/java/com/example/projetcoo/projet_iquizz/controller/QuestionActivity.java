package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.LinearLayout.LayoutParams;
import android.widget.*;
import android.content.Intent;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.Question;
import com.example.projetcoo.projet_iquizz.modele.Choix;


public class QuestionActivity extends AppCompatActivity implements OnClickListener {

    private ImageView retour;
    private ImageView param;
    private LinearLayout layoutQuestion;
    private LinearLayout layoutChoix;
    private TextView avancement;
    private TextView texteQuestion;
    private ArrayList<Button> boutonsChoix;
    
    private ArrayList<Question> questions;
    private int[] choixUtilisateur;
    private int numeroQuestion;
    private int nombreQuestions;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        
        retour = (ImageView) findViewById(R.id.retour);
        param = (ImageView) findViewById(R.id.parametres);
        
        layoutQuestion = (LinearLayout) findViewById(R.id.layout_question);
        layoutChoix = (LinearLayout) findViewById(R.id.layout_choix);
        avancement = (TextView) findViewById(R.id.avancement);
        texteQuestion = (TextView) findViewById(R.id.texte_question);
        
        boutonsChoix = new ArrayList<Button>();
        questions = getQuestions();
        
        numeroQuestion = 0;
        nombreQuestions = questions.size();
        choixUtilisateur = new int[nombreQuestions];
        for(int i = 0; i < choixUtilisateur.length; i++) {
            choixUtilisateur[i] = -1;
        }
        afficheQuestion(0);
        
        retour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (numeroQuestion > 0) {
                    afficheQuestion(numeroQuestion-1);
                }
            }
        });
        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(QuestionActivity.this, Compte.class);
                startActivity(paramActivity);
            }
        });
    }
    
    public void afficheQuestion(int num) {
        numeroQuestion = num;
        if (num >= nombreQuestions) {
            finQuizz();
            return;
        }
        if (num < 0) {
            num = 0;
        }
        avancement.setText((num+1) + "/" + nombreQuestions);
        Question qst = questions.get(num);
        
        if (boutonsChoix.size() != qst.getNbChoix()) {
            layoutChoix.removeAllViews();
            boutonsChoix.clear();
            for(int i = 0; i < qst.getNbChoix(); i++) {
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
                params.setMargins(0,5,0,5);
                Button bouton = new Button(this);
                bouton.setLayoutParams(params);
                bouton.setBackgroundDrawable(getResources().getDrawable(R.drawable.default_bouton_choix));
                bouton.setTextAppearance(this, R.style.DefaultText);
                bouton.setTag(""+i);
                bouton.setOnClickListener(this);
                boutonsChoix.add(bouton);
            }
        }
        
        texteQuestion.setText(qst.getText());
        for (int i = 0; i < boutonsChoix.size(); i++) {
            boutonsChoix.get(i).setText(qst.getChoix(i).getText());
            layoutChoix.addView(boutonsChoix.get(i));
        }
    }
    
    public void onClick(View v) {
        choixUtilisateur[numeroQuestion] = Integer.parseInt((String) v.getTag());
        System.out.println("Choix à la question n°"+numeroQuestion + " = " + choixUtilisateur[numeroQuestion]);
        afficheQuestion(numeroQuestion+1);
    }
    
    public void finQuizz() {
        Intent finQuizzActivity = new Intent(QuestionActivity.this, FinQuizz.class);
        //lancerQuizz.setText("Quizz n°"+vue.getCheckedItemPosition());
        startActivity(finQuizzActivity);
    }
    
    private ArrayList<Question> getQuestions() {
        ArrayList<Question> qst = new ArrayList<Question>();
        ArrayList<Choix> choix1 = new ArrayList<Choix>();
        choix1.add(new Choix("Choix 1"));
        choix1.add(new Choix("Choix 2"));
        choix1.add(new Choix("Choix 3"));
        choix1.add(new Choix("Choix 4"));
        choix1.add(new Choix("Choix 5"));
        choix1.add(new Choix("Choix 6"));
        
        ArrayList<Choix> choix2 = new ArrayList<Choix>();
        choix2.add(new Choix("Choix 1"));
        choix2.add(new Choix("Choix 2"));
        choix2.add(new Choix("Choix 3"));
        choix2.add(new Choix("Choix 4"));
        choix2.add(new Choix("Choix 5"));
        choix2.add(new Choix("Choix 6"));
        choix2.add(new Choix("Choix 7"));
        choix2.add(new Choix("Choix 8"));
        
        ArrayList<Choix> choix3 = new ArrayList<Choix>();
        choix3.add(new Choix("Choix 1"));
        choix3.add(new Choix("Choix 2"));
        choix3.add(new Choix("Choix 3"));
        choix3.add(new Choix("Choix 4"));
        
        qst.add(new Question("Question 1", choix1));
        qst.add(new Question("Question 2", choix2));
        qst.add(new Question("Question 3", choix3));
        
        return qst;
    }
}
