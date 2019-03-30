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
import com.example.projetcoo.projet_iquizz.controller.MainActivity;
import com.example.projetcoo.projet_iquizz.controller.Game;
import com.example.projetcoo.projet_iquizz.modele.*;


public class ListeJoueurs extends AppCompatActivity {

    private ImageView retour;
    private ImageView param;
    private ListView vue;
    private Button lancerDefi;
    private CheckBox defiSimultane;

    private ArrayList<Utilisateur> amis;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_joueurs);
        
        amis = BDD.getInstance().getAmis();
        
        retour = (ImageView) findViewById(R.id.retour);
        param = (ImageView) findViewById(R.id.parametres);
        vue = (ListView) findViewById(R.id.liste_joueurs);
        lancerDefi = (Button) findViewById(R.id.lancer_defi);
        defiSimultane = (CheckBox) findViewById(R.id.defi_simultane);
        
        ArrayAdapter adapteur = new ArrayAdapter(this, R.layout.ligne_item_multiple_choice, amis);
        
        vue.setAdapter(adapteur);
        
        retour.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent ancienneActivity = new Intent(ListeJoueurs.this, Game.class);
                startActivity(ancienneActivity);
            }
        });
        param.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent paramActivity = new Intent(ListeJoueurs.this, Compte.class);
                startActivity(paramActivity);
            }
        });
        lancerDefi.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ArrayList<Utilisateur> joueurs = getJoueursDefis();
                
                if (joueurs.size() == 0) { afficheErreur(-1); return; }
                BDD.getInstance().getData().setJoueursDefi(joueurs);
                
                if (defiSimultane.isChecked() && BDD.getInstance().getData().getJoueursDefiNonConnectes().size() > 0) {
                    Intent connexion = new Intent(ListeJoueurs.this, ConnexionActivity.class);
                    startActivity(connexion);
                } else {
                    Intent defiActivity = new Intent(ListeJoueurs.this, ListeQuizz.class);
                    startActivity(defiActivity);
                }
            }
        });        
    }
    
    private ArrayList<Utilisateur> getJoueursDefis() {
        ArrayList<Utilisateur> joueursSelectionnes = new ArrayList<Utilisateur>();
        SparseBooleanArray selections = vue.getCheckedItemPositions();
        
        for (int i = 0; i < amis.size(); i++) {
            if (selections.get(i)) { joueursSelectionnes.add(amis.get(i)); }
        }
        return joueursSelectionnes;
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
