package com.javafootball.Model.Joueur;

public class CarteCommune extends Carte{
    final static private int maxExemplaire = 1000;
    final static private float coefficient = 1F;

    private CarteCommune(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
    }

    static Carte creerCarte(Joueur joueur) {
        if (joueur.compteurCommune < maxExemplaire) {
            Carte nouvelleCarte;
            joueur.compteurCommune++;
            nouvelleCarte = new CarteCommune(joueur, joueur.compteurCommune);
            return nouvelleCarte;
        }
        return null;
    }



    @Override
    public String toString() {
        return super.toString() + ";C";
    }

}
