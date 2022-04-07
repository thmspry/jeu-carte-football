package com.javafootball.Model.Joueur;

public class JoueurGardien extends Joueur{
    public JoueurGardien(String prenom, String nom) {
        super(prenom, nom);
    }

    @Override
    public String toString() {
        return super.toString() + ";G";
    }
}
