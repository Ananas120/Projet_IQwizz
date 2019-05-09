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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.BDD;

public class NewCompte extends AppCompatActivity {

    private EditText userID;
    private EditText userAge;
    private EditText password;
    private EditText c_password;
    private RadioGroup userSexe;
    private Button connexion;
    private CheckBox resterConnecte;
    private TextView existCompte;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_compte);
        
        userID = (EditText) findViewById(R.id.get_userID);
        userAge = (EditText) findViewById(R.id.get_userAge);
        password = (EditText) findViewById(R.id.get_password);
        c_password = (EditText) findViewById(R.id.get_confirm_password);
        userSexe = (RadioGroup) findViewById(R.id.get_userSexe);
        
        connexion = (Button) findViewById(R.id.connexion);
        resterConnecte = (CheckBox) findViewById(R.id.resterConnecte);
        existCompte = (TextView) findViewById(R.id.existCompte);
        
        connexion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (userID.getText().toString().equals("") ||
                   password.getText().toString().equals("") ||
                   c_password.getText().toString().equals("") ||
                   userAge.getText().toString().equals("")) {
                    afficheErreur(-10, null);
                    return;
                }
                String login = userID.getText().toString();
                String mdp = password.getText().toString();
                String c_mdp = c_password.getText().toString();
                int age = Integer.parseInt(userAge.getText().toString());
                int sexe = userSexe.getCheckedRadioButtonId();
                int souvenir = 0;
                if (resterConnecte.isChecked()) { souvenir = 1; }
                
                int codeErreur = BDD.getInstance().createCompte(login, mdp, c_mdp, age, sexe, souvenir);
                
                afficheErreur(codeErreur, login);
                
                if (codeErreur == 0) {
                    Intent gameActivity = new Intent(NewCompte.this, Game.class);
                    startActivity(gameActivity);
                }
            }
        });
        
        existCompte.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent mainActivity = new Intent(NewCompte.this, MainActivity.class);
                startActivity(mainActivity);
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
        } else if (code == -10) {
            message = getResources().getString(R.string.Connexion_CODE_ERREUR_10);
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
