package com.javafootball.Model.Joueur;

abstract public class Carte {
    public int numero;
    public int maxExemplaire;
    public float coefficient;
    public Joueur joueur;

    @Override
    public String toString() {
        return "" + numero;
    }
}
