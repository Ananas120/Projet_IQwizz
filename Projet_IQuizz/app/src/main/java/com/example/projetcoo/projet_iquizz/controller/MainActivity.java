package com.example.projetcoo.projet_iquizz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.BDD;


public class MainActivity extends AppCompatActivity {

    private EditText userID;
    private EditText password;
    private Button connexion;
    private CheckBox resterConnecte;
    private TextView nouveauCompte;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        BDD.init(this, "requetes_generees.txt");
        
        userID = (EditText)findViewById(R.id.get_userID);
        password = (EditText)findViewById(R.id.get_password);
        
        connexion = (Button) findViewById(R.id.connexion);
        resterConnecte = (CheckBox) findViewById(R.id.resterConnecte);
        nouveauCompte = (TextView) findViewById(R.id.nouveauCompte);
        
        connexion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String login = userID.getText().toString();
                String mdp = password.getText().toString();
                int souvenir = 0;
                if (resterConnecte.isChecked()) { souvenir = 1; }
                
                int codeErreur = BDD.getInstance().connecte(login, mdp, souvenir);
                
                afficheErreur(codeErreur, userID.getText().toString());
                
                if (codeErreur == 0) {
                    Intent gameActivity = new Intent(MainActivity.this, Game.class);
                    startActivity(gameActivity);
                }
            }
        });
        
        nouveauCompte.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent newCompteActivity = new Intent(MainActivity.this, NewCompte.class);
                startActivity(newCompteActivity);
            }
        });        
    }
    
    private void afficheErreur(int code, String identifiant) {
        String message = "";
        if (code == 0) {
            message = getResources().getString(R.string.Connexion_CODE_ERREUR_0, identifiant);
        } else if (code == -1) {
            message = getResources().getString(R.string.Connexion_CODE_ERREUR_1);
        } else if (code == -2) {
            message = getResources().getString(R.string.Connexion_CODE_ERREUR_2);
        } else if (code == -3) {
            message = getResources().getString(R.string.Connexion_CODE_ERREUR_3);
        } else if (code == -4) {
            message = getResources().getString(R.string.Connexion_CODE_ERREUR_4);
        } else if (code == -5) {
            message = getResources().getString(R.string.Connexion_CODE_ERREUR_5);
        } else if (code == -6) {
            message = getResources().getString(R.string.Connexion_CODE_ERREUR_6);
        } else if (code == -7) {
            message = getResources().getString(R.string.Connexion_CODE_ERREUR_7);
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
