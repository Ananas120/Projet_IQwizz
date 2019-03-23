package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.controller.NewCompte;
import com.example.projetcoo.projet_iquizz.controller.Game;
import com.example.projetcoo.projet_iquizz.modele.BDD;


public class MainActivity extends AppCompatActivity {

    private Button connexion;
    private TextView nouveauCompte;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        connexion = (Button) findViewById(R.id.connexion);
        nouveauCompte = (TextView) findViewById(R.id.nouveauCompte);
        
        connexion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditText userID = (EditText)findViewById(R.id.get_userID);
                EditText password = (EditText)findViewById(R.id.get_password);
                
                Intent gameActivity = new Intent(MainActivity.this, Game.class);
                startActivity(gameActivity);
            }
        });
        
        nouveauCompte.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent newCompteActivity = new Intent(MainActivity.this, NewCompte.class);
                startActivity(newCompteActivity);
            }
        });
        //BDD test = BDD.getInstance(this, "requetes_generees.txt");
        BDD test = BDD.getInstance(this);
        
    }
}
