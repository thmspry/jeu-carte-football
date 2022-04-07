package com.javafootball.Model;

import com.javafootball.Model.Joueur.Carte;

import java.util.List;

public class Marche {
    List<Carte> carteAVendre;

    public void ajouterCarteAVendre(Carte c) {
        carteAVendre.add(c);
    }
}

