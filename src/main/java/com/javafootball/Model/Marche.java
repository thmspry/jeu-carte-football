package com.javafootball.Model;

import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Joueur.Joueur;

import java.util.List;

public class Marche {
    List<Carte> carteAVendre;
    List<Joueur> joueursExistant;
    List<Equipe> equipesExistante;

    public void initialisationDonnee() {

    }

    public void ajouterCarteAVendre(Carte c) {
        carteAVendre.add(c);
    }
}

