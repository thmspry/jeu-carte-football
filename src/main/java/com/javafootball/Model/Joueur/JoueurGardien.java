package com.javafootball.Model.Joueur;

import com.javafootball.Model.Equipe;

public class JoueurGardien extends Joueur{
    public JoueurGardien(String prenom, String nom, Equipe equipe) {
        super(prenom, nom, equipe);
        this.poste = Poste.GOALKEEPER;
    }
}
