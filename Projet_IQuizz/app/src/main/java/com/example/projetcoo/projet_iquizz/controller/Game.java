package com.example.projetcoo.projet_iquizz.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.BDD;
import com.example.projetcoo.projet_iquizz.modele.Defi;

import java.util.ArrayList;


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

        BDD.getInstance().getData().clearJoueursDefi();
        
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
                ArrayList<Defi> listeDefis = BDD.getInstance().getDefisNonFinis();
                if (listeDefis.size() > 0) {
                    Intent mainActivity = new Intent(Game.this, Reprendre.class);
                    startActivity(mainActivity);
                } else {
                    afficheErreur(-1);
                }
            }
        });
        
        deco.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                BDD.getInstance().getData().deconnecte();
                
                Intent mainActivity = new Intent(Game.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
    
    private void afficheErreur(int code) {
        String message = "";
        if (code == -1) {
            message = getResources().getString(R.string.Game_CODE_ERREUR_1);
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
