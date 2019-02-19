package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;
import com.example.projetcoo.projet_iquizz.controller.Game;

import com.example.projetcoo.projet_iquizz.R;

public class MainActivity extends AppCompatActivity {

    private Button connexion;
    private TextView nouveauCompte;
    private EditText userID;
    private EditText password;
    private EditText confirmPassword;
    private Context context;
    private boolean newCompte = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        userID = (EditText)findViewById(R.id.userID);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        connexion = (Button)findViewById(R.id.connexion);
        nouveauCompte = (TextView)findViewById(R.id.nouveauCompte);
        context = this.getBaseContext();
        
        connexion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (newCompte) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                        Intent gameActivity = new Intent(MainActivity.this, Game.class);
                        context.startActivity(gameActivity);
                    } else {
                        Toast.makeText(MainActivity.this, "Les mots de passe ne sont pas identiques !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent gameActivity = new Intent(MainActivity.this, Game.class);
                    context.startActivity(gameActivity);
                }
            }
        });
        nouveauCompte.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                confirmPassword.setVisibility(View.VISIBLE);
                nouveauCompte.setVisibility(View.INVISIBLE);
                newCompte = true;
            }
        });
            
                    
    }
}
