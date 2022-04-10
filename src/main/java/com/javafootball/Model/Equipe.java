package com.javafootball.Model;

import com.javafootball.Model.Joueur.Joueur;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    public final String nom;
    public List<Joueur> compositionJoueur;
    public String urlImageLogo = "";

    public Equipe(String nom) {
        this.nom = nom;
        compositionJoueur = new ArrayList<>();
    }
}
