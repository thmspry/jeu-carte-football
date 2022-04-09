package com.javafootball.Model;

import com.javafootball.Model.Joueur.Joueur;

import java.util.List;

public class Equipe {
    public final String nom;
    public List<Joueur> compositionJoueur;

    public Equipe(String nom) {
        this.nom = nom;
    }
}
