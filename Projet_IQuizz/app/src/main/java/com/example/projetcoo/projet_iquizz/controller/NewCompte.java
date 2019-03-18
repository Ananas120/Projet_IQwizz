package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.controller.MainActivity;
import com.example.projetcoo.projet_iquizz.controller.Game;

public class NewCompte extends AppCompatActivity {

    private Button connexion;
    private TextView existCompte;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_compte);
        
        connexion = (Button) findViewById(R.id.connexion);
        existCompte = (TextView) findViewById(R.id.existCompte);
        
        connexion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditText userID = (EditText) findViewById(R.id.get_userID);
                EditText password = (EditText) findViewById(R.id.get_password);
                EditText c_password = (EditText) findViewById(R.id.get_confirm_password);
                
                Intent gameActivity = new Intent(NewCompte.this, Game.class);
                startActivity(gameActivity);
            }
        });
        
        existCompte.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent mainActivity = new Intent(NewCompte.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}
