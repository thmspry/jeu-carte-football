package com.javafootball.Model.Joueur;


abstract public class Carte {
    public int numero;
    public Joueur joueur;
    protected float coefficient;
    public String lienFondCarte;

    public Carte(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public String toString() {
        return joueur.toString() + ";" + this.numero;
    }


}
