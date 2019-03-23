package com.example.projetcoo.projet_iquizz.modele;

import java.util.ArrayList;

public class Random {
    
    public static int[] range(int min, int max) {
        int tab[] = new int[max-min];
        int i = 0;
        for(int n = min; n < max; n++) {
            tab[i] = n;
            i++;
        }
        return tab;
    }
    
    public static int[] range(int max) {
        return range(0, max);
    }
    
    public static int randInt(int min, int max) {
        return (int) (Math.random() * (max-min)) + min;
    }
    public static int randInt(int max) {
        return (int) (Math.random() * max);
    }
    
    public static int[] sample(int[] tab, int nbSample) {
        int[] sample = new int[Math.min(tab.length, nbSample)];
        int fin = tab.length-1;
        for (int i = 0; i < sample.length; i++) {
            int rand = randInt(fin);
            sample[i] = tab[rand];
            tab[rand] = tab[fin];
            fin--;
        }
        return sample;
    }
    
    public static ArrayList sample(ArrayList liste, int nbSample) {
        int indices[] = sample(range(liste.size()), nbSample);
        ArrayList sample = new ArrayList();
        for (int i = 0; i < indices.length; i++) {
            sample.add(liste.get(indices[i]));
        }
        return sample;
    }

    public static ArrayList shuffle(ArrayList liste) {
        return sample(liste, liste.size());
    }
}
