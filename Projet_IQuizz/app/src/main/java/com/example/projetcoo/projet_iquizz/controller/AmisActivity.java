package com.example.projetcoo.projet_iquizz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.Intent;
import android.util.*;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.*;

public class AmisActivity extends AppCompatActivity {

    private ImageView retour;
    private ImageView param;
    private ListView vue;
    private Button supprimer;
    private EditText get_amiLogin;
    private Button ajouter;
    private Button go_jeu;
    
    private ArrayAdapter adapteur;
    
    private Utilisateur joueur;
    private ArrayList<Utilisateur> amis;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amis);
        
        joueur = BDD.getInstance().getData().getJoueur();
        amis = joueur.getAmis();
        
        retour = (ImageView) findViewById(R.id.retour);
        param = (ImageView) findViewById(R.id.parametres);
        vue = (ListView) findViewById(R.id.liste_amis);
        supprimer = (Button) findViewById(R.id.supprimer_amis);
        get_amiLogin = (EditText) findViewById(R.id.get_amiID);
        ajouter = (Button) findViewById(R.id.ajouter_amis);
        go_jeu = (Button) findViewById(R.id.go_jeu);
        
        adapteur = new ArrayAdapter(this, R.layout.ligne_item_multiple_choice, amis);
        
        vue.setAdapter(adapteur);
        
        retour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent ancienneActivity = new Intent(AmisActivity.this, Compte.class);
                startActivity(ancienneActivity);
            }
        });
        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(AmisActivity.this, Compte.class);
                startActivity(paramActivity);
            }
        });
        
        supprimer.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int nbSupprimes = removeAmis();
                if (nbSupprimes == 0) { afficheErreur(-4); return; }
                adapteur.notifyDataSetChanged();
            }
        });
        ajouter.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String login = get_amiLogin.getText().toString();
                int codeErreur = joueur.addAmi(login);
                if (codeErreur != 0) { afficheErreur(codeErreur); return; }
                adapteur.notifyDataSetChanged();
            }
        });
        go_jeu.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(AmisActivity.this, Game.class);
                startActivity(paramActivity);
            }
        });
    }
    
    private int removeAmis() {
        int nb = 0;
        SparseBooleanArray selections = vue.getCheckedItemPositions();
        
        for (int i = 0; i < amis.size(); i++) {
            if (selections.get(i)) { 
                joueur.removeAmi(amis.get(i));
                nb++;
            }
        }
        return nb;
    }
    
    private void afficheErreur(int code) {
        String message = "";
        if (code == 0) {
            message = getResources().getString(R.string.Ami_CODE_ERREUR_0);
        } else if (code == -1) {
            message = getResources().getString(R.string.Ami_CODE_ERREUR_1);
        } else if (code == -2) {
            message = getResources().getString(R.string.Ami_CODE_ERREUR_2);
        } else if (code == -3) {
            message = getResources().getString(R.string.Ami_CODE_ERREUR_3);        } else if (code == -4) {
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
