package com.example.projetcoo.projet_iquizz.vue;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View.*;
import android.widget.*;
import android.content.Context;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.Defi;
import com.example.projetcoo.projet_iquizz.modele.Utilisateur;


public class DefiAdapter extends BaseAdapter {
    
    private static class ViewHolder {
        public TextView nom;
        public TextView nbQst;
        public CheckedTextView joueurs;
    }
    
    private Context context;
    private ArrayList<Defi> donnees;
    private LayoutInflater inflater;
    
    public DefiAdapter(ArrayList<Defi> donnees, Context context) {
        this.donnees = donnees;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    
    public int getCount() {
        return donnees.size();
    }
    
    public Object getItem(int pos) {
        if (pos >= donnees.size()) {
            return null;
        } else {
            return donnees.get(pos);
        }
    }
    
    public long getItemId(int pos) {
        return 0;
    }
    
    public View getView(int pos, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ligne_item_quizz, null);
            
            holder = new ViewHolder();
            
            holder.nom = (TextView) convertView.findViewById(R.id.nomQuizz);
            holder.nbQst = (TextView) convertView.findViewById(R.id.nbQst);
            holder.joueurs = (CheckedTextView) convertView.findViewById(R.id.categorieQuizz);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        Defi d = (Defi) this.getItem(pos);
        
        if (d != null) {
            //q = null;
            holder.nom.setText("Nom : "+d.getNom());
            holder.nbQst.setText(d.getNbQuestionsRestantes() + " questions restantes");
            String players = "Joueurs : ";
            ArrayList<Utilisateur> liste_joueurs = d.getJoueurs();
            for (int i = 0; i < liste_joueurs.size(); i++) {
                players += "\n" + liste_joueurs.get(i).getNom() + ",";
            }
            holder.joueurs.setText(players);
        }
        return convertView;
    }
}
