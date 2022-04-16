package com.javafootball.Model.Joueur;

public class JoueurGardien extends Joueur{
    public JoueurGardien(String prenom, String nom, Equipe equipe) {
        super(prenom, nom, equipe);
        this.poste = Poste.GOALKEEPER;
    }
}
