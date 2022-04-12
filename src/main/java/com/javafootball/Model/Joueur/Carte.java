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


    /**
     * Donne une dénomination de la carte, comprenant seulement le nom et prom du joueur
     * @return la string formatée
     */
    public String denominationSimplifiee() {
        return this.joueur.denomination();
    }

    @Override
    public String toString() {
        return joueur.toString() + ";" + this.numero;
    }

    public String toStringVerbeux() {
        return joueur.toStringVerbeux() + " n°" + this.numero;
    }


}
