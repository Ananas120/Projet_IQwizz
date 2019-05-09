package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.*;
import android.view.View.*;
import android.widget.LinearLayout.LayoutParams;
import android.widget.*;
import android.content.Intent;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.*;

public class CorrectionQuestionActivity extends AppCompatActivity {

    private ImageView retour;
    private Button quitter;
    private ImageView suivant;
    private LinearLayout layoutQuestion;
    private LinearLayout layoutChoix;
    private TextView affiche_avancement;
    private TextView affiche_nomJoueur;
    private TextView affiche_texteQuestion;
    private TextView affiche_choix;
    private TextView affiche_choixCorrect; 
    private TextView affiche_explication;
    
    private ArrayList<Question> questions;
    private Defi defi;
    private String joueur;
    private int numeroQuestion;
    private int nombreQuestions;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction_question);
        
        retour = (ImageView) findViewById(R.id.retour);
        quitter = (Button) findViewById(R.id.quitter);
        suivant = (ImageView) findViewById(R.id.suivant);
        
        layoutQuestion = (LinearLayout) findViewById(R.id.layout_question);
        layoutChoix = (LinearLayout) findViewById(R.id.layout_choix);
        affiche_avancement = (TextView) findViewById(R.id.avancement);
        affiche_nomJoueur = (TextView) findViewById(R.id.nom_joueur);
        affiche_texteQuestion = (TextView) findViewById(R.id.texte_question);
        affiche_choix = (TextView) findViewById(R.id.texte_choix);
        affiche_choixCorrect = (TextView) findViewById(R.id.texte_choix_correct);
        affiche_explication = (TextView) findViewById(R.id.texte_explication);
        
        Intent i = getIntent();
        
        defi = BDD.getInstance().getData().getDefiEnCours();
        joueur = i.getStringExtra(FinQuizz.JOUEUR);
        numeroQuestion = i.getIntExtra(FinQuizz.NUMERO, 0);
        questions = defi.getQuestions();

        nombreQuestions = questions.size();

        afficheQuestion(numeroQuestion);
        
        retour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (numeroQuestion > 0) {
                    afficheQuestion(numeroQuestion-1);
                }
            }
        });
        quitter.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent quitter = new Intent(CorrectionQuestionActivity.this, FinQuizz.class);
                startActivity(quitter);
            }
        });
        suivant.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (numeroQuestion < nombreQuestions) {
                    afficheQuestion(numeroQuestion+1);
                }
            }
        });
    }
    
    private void afficheQuestion(int num) {
        if (num >= nombreQuestions) {
            num = nombreQuestions-1;
            finQuizz();
            return;
        }
        if (num < 0) {
            num = 0;
        }
        numeroQuestion = num;
        
        affiche_avancement.setText((num+1) + "/" + nombreQuestions);
        
        Question qst = questions.get(num);
        
        int choixUtilisateur = defi.getChoix(joueur, numeroQuestion);
        
        affiche_texteQuestion.setText(qst.getText());
        if (qst.getText().split("\n").length > 2) {
            affiche_texteQuestion.setTextAppearance(this, R.style.DefaultTextMini);
        } else {
            affiche_texteQuestion.setTextAppearance(this, R.style.DefaultTextPetit);
        }
        
        String texteChoix = "";
        if (choixUtilisateur != -1) {
            texteChoix = qst.getChoix(choixUtilisateur).getText();
        } else {
            texteChoix = "Vous n'avez pas sélectionné de choix !";
        }
        affiche_choix.setText(texteChoix);
        if (texteChoix.contains("\n")) {
            affiche_choix.setTextAppearance(this, R.style.DefaultTextMini);
        } else {
            affiche_choix.setTextAppearance(this, R.style.DefaultTextPetit);
        }
        
        affiche_choixCorrect.setText(qst.getChoixCorrecte().getText());
        if (qst.getChoixCorrecte().getText().contains("\n")) {
            affiche_choixCorrect.setTextAppearance(this, R.style.DefaultTextMini);
        } else {
            affiche_choixCorrect.setTextAppearance(this, R.style.DefaultTextPetit);
        }
        
        
        affiche_explication.setText(qst.getExplication());
        
        if (choixUtilisateur != -1 && qst.isCorrectChoix(choixUtilisateur)) {
           affiche_choix.setBackgroundDrawable(getResources().getDrawable(R.drawable.choix_v));
        } else {
            affiche_choix.setBackgroundDrawable(getResources().getDrawable(R.drawable.choix_f));
        }
    }
            
    private void finQuizz() {
        Intent finQuizzActivity = new Intent(CorrectionQuestionActivity.this, FinQuizz.class);
        startActivity(finQuizzActivity);
    }
    
    private void afficheErreur(int code, String identifiant) {
        String message = "";
        if (code == 1) {
            message = getResources().getString(R.string.Question_TRANSITION, identifiant);
        } else if (code == 0) {
            message = getResources().getString(R.string.Question_CODE_ERREUR_0);
        } else if (code == -1) {
            message = getResources().getString(R.string.Question_CODE_ERREUR_1);
        } else if (code == -2) {
            message = getResources().getString(R.string.Question_CODE_ERREUR_2);
        }
        LinearLayout toastLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.toast_view, null);
        TextView toastText = (TextView) toastLayout.findViewById(R.id.toast_text);
        toastText.setText(message);
        
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastLayout);
        toast.show();
    }

}
