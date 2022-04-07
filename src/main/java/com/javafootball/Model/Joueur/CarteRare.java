package com.javafootball.Model.Joueur;

public class CarteRare extends Carte {

    final static private int maxExemplaire = 10;
    final static private float coefficient = 1.1F;

    public CarteRare(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
    }

    static Carte creerCarte(Joueur joueur) {
        if (joueur.compteurRare < maxExemplaire) {
            Carte nouvelleCarte;
            joueur.compteurRare++;
            nouvelleCarte = new CarteRare(joueur, joueur.compteurRare);
            return nouvelleCarte;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + ";R";
    }
}
