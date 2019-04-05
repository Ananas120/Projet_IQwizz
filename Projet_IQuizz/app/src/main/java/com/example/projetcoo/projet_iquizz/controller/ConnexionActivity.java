package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.*;

public class ConnexionActivity extends AppCompatActivity {

    private TextView userID;
    private EditText password;
    private Button connexion;
    private Button jouerPlusTard;
    
    private ArrayList<Utilisateur> joueursAConnecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        
        joueursAConnecter = BDD.getInstance().getData().getJoueursDefiNonConnectes();
        
        userID = (TextView) findViewById(R.id.userID);
        password = (EditText) findViewById(R.id.get_password);
        connexion = (Button) findViewById(R.id.connexion);
        jouerPlusTard = (Button) findViewById(R.id.plus_tard);
        
        String id = getResources().getString(R.string.connection_joueur, joueursAConnecter.get(0).getNom());
        userID.setText(id);
        
        connexion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String mdp = password.getText().toString();
                
                int codeErreur = BDD.getInstance().connecte(joueursAConnecter.get(0).getNom(), mdp, 0);
                
                afficheErreur(codeErreur, joueursAConnecter.get(0).getNom());
                
                if (codeErreur == 0) {
                    joueursAConnecter.remove(0);
                    if (joueursAConnecter.size() > 0) {
                        String id = getResources().getString(R.string.connection_joueur, joueursAConnecter.get(0).getNom());
                        userID.setText(id);
                    } else {
                        if (BDD.getInstance().getData().hasDefiEnCours()) {
                            Intent questionActivity = new Intent(ConnexionActivity.this, QuestionActivity.class);
                            startActivity(questionActivity);
                        } else {
                            Intent quizzActivity = new Intent(ConnexionActivity.this, ListeQuizz.class);
                            startActivity(quizzActivity);
                        }
                    }
                }
            }
        });
        
        jouerPlusTard.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                joueursAConnecter.remove(0);
                if (joueursAConnecter.size() > 0) {
                    String id = getResources().getString(R.string.connection_joueur, joueursAConnecter.get(0).getNom());
                    userID.setText(id);
                } else {
                    if (BDD.getInstance().getData().hasDefiEnCours()) {
                        Intent questionActivity = new Intent(ConnexionActivity.this, QuestionActivity.class);
                        startActivity(questionActivity);
                    } else {
                        Intent quizzActivity = new Intent(ConnexionActivity.this, ListeQuizz.class);
                        startActivity(quizzActivity);
                    }
                }
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
