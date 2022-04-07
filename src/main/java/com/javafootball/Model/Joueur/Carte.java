package com.javafootball.Model.Joueur;


abstract public class Carte {
    public int numero;
    public Joueur joueur;

    public Carte(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public String toString() {
        return "" + numero;
    }


}
