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


public class QuestionActivity extends AppCompatActivity implements OnClickListener {

    private ImageView retour;
    private Button quitter;
    private ImageView suivant;
    private LinearLayout layoutQuestion;
    private LinearLayout layoutChoix;
    private TextView affiche_avancement;
    private TextView affiche_nomJoueur;
    private TextView affiche_texteQuestion;
    private ArrayList<Button> boutonsChoix;
    
    private ArrayList<Question> questions;
    private Defi defi;
    private ArrayList<Utilisateur> joueurs;
    private int numeroQuestion;
    private int nombreQuestions;
    private int numeroJoueur;
    private boolean validationFin = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        
        retour = (ImageView) findViewById(R.id.retour);
        quitter = (Button) findViewById(R.id.quitter);
        suivant = (ImageView) findViewById(R.id.suivant);
        
        layoutQuestion = (LinearLayout) findViewById(R.id.layout_question);
        layoutChoix = (LinearLayout) findViewById(R.id.layout_choix);
        affiche_avancement = (TextView) findViewById(R.id.avancement);
        affiche_nomJoueur = (TextView) findViewById(R.id.nom_joueur);
        affiche_texteQuestion = (TextView) findViewById(R.id.texte_question);
        
        boutonsChoix = new ArrayList<Button>();
        
        defi = BDD.getInstance().getData().getDefiEnCours();
        joueurs = BDD.getInstance().getData().getJoueursDefiConnectes();
        questions = defi.getQuestions();
                
        numeroJoueur = 0;
        numeroQuestion = 0;
        nombreQuestions = questions.size();

        afficheQuestion(0);
        
        retour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (joueurs.size() > 1) { afficheErreur(-2, null); return; }
                validationFin = false;
                if (numeroQuestion > 0) {
                    affiche(numeroQuestion-1);
                }
            }
        });
        quitter.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent quitter = new Intent(QuestionActivity.this, Game.class);
                startActivity(quitter);
            }
        });
        suivant.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (joueurs.size() > 1) { afficheErreur(-2, null); return; }
                if (numeroQuestion < nombreQuestions) {
                    affiche(numeroQuestion+1);
                }
            }
        });
    }
    
    public void affiche(int numQuestion) {
        if (joueurs.size() == 1) {
            afficheQuestion(numQuestion);
        } else if (numeroJoueur == joueurs.size() -1) {
            afficheJoueur(0);
            afficheErreur(1, joueurs.get(numeroJoueur).getNom());
            afficheQuestion(numQuestion);
        } else {
            afficheJoueur(numeroJoueur+1);
            afficheErreur(1, joueurs.get(numeroJoueur).getNom());
            afficheQuestion(numeroQuestion);
        }
    }
    
    private void afficheJoueur(int num) {
        numeroJoueur = num;
        affiche_nomJoueur.setText(getResources().getString(R.string.tour_joueur, joueurs.get(numeroJoueur).getNom()));
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
        
        if (boutonsChoix.size() != qst.getNbChoix()) {
            layoutChoix.removeAllViews();
            boutonsChoix.clear();
            for(int i = 0; i < qst.getNbChoix(); i++) {
                Button bouton = createBoutonChoix();
                boutonsChoix.add(bouton);
                layoutChoix.addView(boutonsChoix.get(i));
            }
        }
        
        int choixUtilisateur = defi.getChoix(joueurs.get(numeroJoueur), numeroQuestion);
        
        affiche_texteQuestion.setText(qst.getText());
        for (int i = 0; i < boutonsChoix.size(); i++) {
            boutonsChoix.get(i).setTag("" + qst.getChoix(i).getNumero());
            if (choixUtilisateur == qst.getChoix(i).getNumero()) {
                boutonsChoix.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.default_bouton_choix_c));
            } else {
                boutonsChoix.get(i).setBackgroundDrawable(getResources().getDrawable(R.drawable.default_bouton_choix));
            }
            boutonsChoix.get(i).setText(qst.getChoix(i).getText());
        }
    }
    
    private Button createBoutonChoix() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                                               LayoutParams.WRAP_CONTENT);
        params.setMargins(0,5,0,5);
        Button bouton = new Button(this);
        bouton.setLayoutParams(params);
        bouton.setTextAppearance(this, R.style.DefaultText);
        bouton.setOnClickListener(this);
        return bouton;
    }
    
    public void onClick(View v) {
        int numeroChoix = Integer.parseInt((String) v.getTag());
        defi.setChoix(joueurs.get(numeroJoueur), numeroQuestion, numeroChoix);
        affiche(numeroQuestion+1);
    }
    
    private void finQuizz() {
        if (validationFin) {
            Intent finQuizzActivity = new Intent(QuestionActivity.this, FinQuizz.class);
            startActivity(finQuizzActivity);
        } else {
            validationFin = true;
            if (joueurs.size() > 1 || defi.isFini(joueurs.get(0))) {
                afficheErreur(0, null);
            } else {
                afficheErreur(-1, null);
            }
        }
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
