package com.javafootball.Model.Joueur;

import com.javafootball.Model.Equipe;

import java.util.Objects;

abstract public class Joueur {
    public int compteurCommune = 0;
    public int compteurPeuCommune = 0;
    public int compteurRare = 0;

    public final String prenom;
    public final String nom;
    public String lienPhoto;
    public Equipe equipe;
    public Poste poste;


    protected Joueur(String prenom, String nom, Equipe equipe) {
        this.prenom = prenom;
        this.nom = nom;
        this.equipe = equipe;
        this.lienPhoto = "https://creazilla-store.fra1.digitaloceanspaces.com/emojis/46301/bust-in-silhouette-emoji-clipart-sm.png";
    }


    boolean aJoue() {
        return false;
    }

    public int getCompteurRare() {
        return this.compteurRare;
    }

    public void setLienPhoto(String lienPhoto) {
        this.lienPhoto = lienPhoto;
    }

    public String denomination() {
        return this.prenom + " " + this.nom;
    }

    @Override
    public String toString() {
        return this.nom + ";" + this.prenom + ";" + this.equipe.nom + ";" + this.poste.getAbreviation();
    }

    public String toStringVerbeux() {
        return this.prenom + " " + this.nom + ", " + this.equipe.nom + ", " + this.poste.getAbreviation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return Objects.equals(prenom, joueur.prenom) && Objects.equals(nom, joueur.nom) && equipe.equals(joueur.equipe);
    }
}
