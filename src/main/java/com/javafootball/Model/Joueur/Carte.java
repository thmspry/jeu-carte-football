package com.javafootball.Model.Joueur;


import com.javafootball.Model.Exception.ExceptionRareteDepasse;

abstract public class Carte {
    public int numero;
    public Joueur joueur;
    public float coefficient;
    public String lienFondCarte;
    public String rareteLabel;

    public Carte(Joueur joueur) {
        this.joueur = joueur;
    }



    @Override
    public String toString() {
        return joueur.toString() + ";" + this.numero;
    }

    public String toStringVerbeux() {
        return joueur.toStringVerbeux() + " n°" + this.numero;
    }


}
