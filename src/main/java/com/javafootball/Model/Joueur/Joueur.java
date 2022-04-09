package com.javafootball.Model.Joueur;

import com.javafootball.Model.Equipe;

abstract public class Joueur {
    int compteurCommune = 0;
    int compteurPeuCommune = 0;
    int compteurRare = 0;

    public final String prenom;
    public final String nom;
    public String lienPhoto = "";
    public Equipe equipe;
    public Poste poste;


    protected Joueur(String prenom, String nom, Equipe equipe) {
        this.prenom = prenom;
        this.nom = nom;
        this.equipe = equipe;
    }

    boolean aJoue() {
        return false;
    }

    public int getCompteurRare() {
        return this.compteurRare;
    }

    @Override
    public String toString() {
        return this.nom + ";" + this.prenom + ";" + this.equipe.nom + ";" + this.poste.getAbreviation();
    }

    public String toStringVerbeux() {
        return this.prenom + " " + this.nom + ", " + this.equipe.nom + ", " + this.poste.getAbreviation();
    }
}
