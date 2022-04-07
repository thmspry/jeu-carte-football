package com.javafootball.Model.Joueur;

public class JoueurDeChamp extends Joueur{
    public JoueurDeChamp(String prenom, String nom) {
        super(prenom, nom);
    }

    @Override
    public String toString() {
        return super.toString() + ";C";
    }
}
