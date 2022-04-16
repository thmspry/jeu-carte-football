package com.javafootball.Model.Joueur;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Equipe {
    public final String nom;
    public List<Joueur> compositionJoueur;
    public String urlImageLogo = "";

    public Equipe(String nom) {
        this.nom = nom;
        compositionJoueur = new ArrayList<>();
    }

    public void ajouterJoueur(Joueur joueur) {
        this.compositionJoueur.add(joueur);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipe equipe = (Equipe) o;
        return Objects.equals(nom, equipe.nom);
    }
}
