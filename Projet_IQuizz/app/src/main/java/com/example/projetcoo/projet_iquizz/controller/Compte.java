package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import com.example.projetcoo.projet_iquizz.R;

public class Compte extends AppCompatActivity {

    private EditText identifiant;
    private RadioGroup sexe;
    private Button sauver_modifs;
    private Button amis;
    private Button go_stats;
    private Button go_jeu;
    
    
    //private Utilisateur user;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);
        
        identifiant = (EditText) findViewById(R.id.userID);
        sexe = (RadioGroup) findViewById(R.id.sexe);
        sauver_modifs = (Button) findViewById(R.id.sauver_modifs);
        amis = (Button) findViewById(R.id.gerer_amis);
        go_stats = (Button) findViewById(R.id.go_stats);
        go_jeu = (Button) findViewById(R.id.go_jeu);
        
        sauver_modifs.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                System.out.println(sexe.getCheckedRadioButtonId());
            }
        });
        amis.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(Compte.this, Game.class);
                startActivity(paramActivity);
            }
        });
        go_stats.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(Compte.this, StatsActivity.class);
                startActivity(paramActivity);
            }
        });
        go_jeu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(Compte.this, Game.class);
                startActivity(paramActivity);
            }
        });
        
    }
}
