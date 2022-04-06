package com.javafootball.Model.Joueur;


abstract public class Carte {
    public int numero;
    public int maxExemplaire;
    public float coefficient;
    public Joueur joueur;

    public Carte(int numero, int maxExemplaire, float coefficient, Joueur joueur) {
        this.numero = numero;
        this.maxExemplaire = maxExemplaire;
        this.coefficient = coefficient;
        this.joueur = joueur;
    }

    @Override
    public String toString() {
        return "" + numero;
    }


}
