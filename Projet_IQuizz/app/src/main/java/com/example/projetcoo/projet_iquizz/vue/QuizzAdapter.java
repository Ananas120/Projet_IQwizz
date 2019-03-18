package com.example.projetcoo.projet_iquizz.vue;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View.*;
import android.widget.*;
import android.content.Context;

import java.util.ArrayList;

import com.example.projetcoo.projet_iquizz.R;
import com.example.projetcoo.projet_iquizz.modele.Quizz;

public class QuizzAdapter extends BaseAdapter {
    
    private static class ViewHolder {
        public TextView nom;
        public TextView nbQst;
        public CheckedTextView categorie;
    }
    
    private Context context;
    private ArrayList<Quizz> donnees;
    private LayoutInflater inflater;
    
    public QuizzAdapter(ArrayList<Quizz> donnees, Context context) {
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
            holder.categorie = (CheckedTextView) convertView.findViewById(R.id.categorieQuizz);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        Quizz q = (Quizz) this.getItem(pos);
        
        if (q != null) {
            //q = null;
            holder.nom.setText("Nom : "+q.getNom());
            holder.nbQst.setText(q.getNbQuestions() + " questions");
            holder.categorie.setText("Cat : " + q.getCategorie());
        }
        return convertView;
    }
}
