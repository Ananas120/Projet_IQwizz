package com.example.projetcoo.projet_iquizz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.*;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.BDD;
import com.example.projetcoo.projet_iquizz.modele.Utilisateur;

public class Compte extends AppCompatActivity {

    private TextView identifiant;
    private EditText get_ancienPassword;
    private EditText get_nouveauPassword;
    private EditText get_age;
    private RadioGroup get_sexe;
    
    private Button sauver_modifs;
    private Button amis;
    private Button go_stats;
    private Button go_jeu;
    
    private Utilisateur user;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);
        
        user = BDD.getInstance().getData().getJoueur();
        
        identifiant = (TextView) findViewById(R.id.userID);
        get_ancienPassword = (EditText) findViewById(R.id.get_ancienPassword);
        get_nouveauPassword = (EditText) findViewById(R.id.get_nouveauPassword);
        get_age = (EditText) findViewById(R.id.get_userAge);
        get_sexe = (RadioGroup) findViewById(R.id.sexe);
        
        sauver_modifs = (Button) findViewById(R.id.sauver_modifs);
        amis = (Button) findViewById(R.id.gerer_amis);
        go_stats = (Button) findViewById(R.id.go_stats);
        go_jeu = (Button) findViewById(R.id.go_jeu);
        
        identifiant.setText(user.getNom());
        get_age.setText(""+user.getAge());
        get_sexe.clearCheck();
        System.out.println(user.getSexe());
        if (user.getSexe() == BDD.Sexe.HOMME) {
            ((RadioButton)findViewById(R.id.sexeM)).setChecked(true);
        } else if (user.getSexe() == BDD.Sexe.FEMME) {
            get_sexe.check(get_sexe.getChildAt(2).getId());
        } else {
            get_sexe.check(R.id.sexeA);
        }
        get_sexe.check(user.getSexe());
        
        
        sauver_modifs.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String ancienPassword = get_ancienPassword.getText().toString();
                String nouveauPassword = get_nouveauPassword.getText().toString();
                int age = Integer.parseInt(get_age.getText().toString());
                int sexe = get_sexe.getCheckedRadioButtonId();
                if (sexe == R.id.sexeM) {
                    sexe = BDD.Sexe.HOMME;
                } else if (sexe == R.id.sexeF) {
                    sexe = BDD.Sexe.FEMME;
                } else {
                    sexe = BDD.Sexe.AUTRE;
                }
                
                
                user.setPassword(ancienPassword, nouveauPassword);
                user.setAge(age);
                user.setSexe(sexe);
                
                user.update();
            }
        });
        amis.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(Compte.this, AmisActivity.class);
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
