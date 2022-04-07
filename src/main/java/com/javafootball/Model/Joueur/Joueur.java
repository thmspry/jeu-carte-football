package com.javafootball.Model.Joueur;

import com.javafootball.Model.Equipe;

abstract public class Joueur {
    int compteurCommune = 0;
    int compteurPeuCommune = 0;
    int compteurRare = 0;
    public final String prenom;
    public final String nom;
    public String lienPhoto;
    public Equipe equipe;


    protected Joueur(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
    }


    boolean aJoue() {

        return false;
    }
}
