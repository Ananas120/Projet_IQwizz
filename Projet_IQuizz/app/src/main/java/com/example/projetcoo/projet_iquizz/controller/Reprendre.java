package com.example.projetcoo.projet_iquizz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.BDD;
import com.example.projetcoo.projet_iquizz.modele.Defi;

import java.util.ArrayList;

public class Reprendre extends AppCompatActivity {
    
    private ImageView retour;
    private ImageView param;
    private ListView vue;
    private Button lancerQuizz;
    private CheckBox jouerSimultane;

    private ArrayList<Defi> listeDefis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprendre);
        
        listeDefis = BDD.getInstance().getDefisNonFinis();
        
        retour = (ImageView) findViewById(R.id.retour);
        param = (ImageView) findViewById(R.id.parametres);
        vue = (ListView) findViewById(R.id.liste_quizz);
        lancerQuizz = (Button) findViewById(R.id.lancer_quizz);
        jouerSimultane = (CheckBox) findViewById(R.id.defi_simultane);
        
        ArrayAdapter adapteur = new ArrayAdapter(this, R.layout.ligne_item_single_choice, listeDefis);
        
        vue.setAdapter(adapteur);
        
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
        lancerQuizz.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int pos = vue.getCheckedItemPosition();
                if (pos == -1) { afficheErreur(-1); return ; }
                
                Defi d = listeDefis.get(pos);
                
                BDD.getInstance().getData().setDefiEnCours(d);
                
                if (jouerSimultane.isChecked() && BDD.getInstance().getData().getJoueursDefiNonConnectes().size() > 0) {
                    Intent connexion = new Intent(Reprendre.this, ConnexionActivity.class);
                    startActivity(connexion);
                } else {
                    Intent questionActivity = new Intent(Reprendre.this, QuestionActivity.class);
                    startActivity(questionActivity);
                }
            }
        });
    }
    
    private void afficheErreur(int code) {
        String message = "";
        if (code == -1) {
            message = getResources().getString(R.string.NO_SELECTION_ERROR);
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