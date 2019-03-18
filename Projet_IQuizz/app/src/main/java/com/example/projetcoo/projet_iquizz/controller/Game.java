package com.example.projetcoo.projet_iquizz.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.controller.MainActivity;
import com.example.projetcoo.projet_iquizz.controller.ListeQuizz;
import com.example.projetcoo.projet_iquizz.controller.ListeJoueurs;


public class Game extends AppCompatActivity {

    private Button jouer;
    private Button defi;
    private Button reprendre;
    private Button deco;
    private ImageView param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        param = (ImageView) findViewById(R.id.parametres);
        
        jouer = (Button) findViewById(R.id.jouer);
        defi = (Button) findViewById(R.id.defi);
        reprendre = (Button) findViewById(R.id.reprendre);
        deco = (Button) findViewById(R.id.deconnexion);

        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(Game.this, Compte.class);
                startActivity(paramActivity);
            }
        });
        jouer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent mainActivity = new Intent(Game.this, ListeQuizz.class);
                startActivity(mainActivity);
            }
        });
        
        defi.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent listeJoueursActivity = new Intent(Game.this, ListeJoueurs.class);
                startActivity(listeJoueursActivity);
            }
        });
        
        reprendre.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent mainActivity = new Intent(Game.this, Reprendre.class);
                startActivity(mainActivity);
            }
        });
        
        deco.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent mainActivity = new Intent(Game.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

    }
}
