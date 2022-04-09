package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.*;

public class CarteRare extends Carte {

    final static private int maxExemplaire = 10;
    final static private float coefficient = 1.1F;

    private CarteRare(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
    }

    public static Carte creerCarte(Joueur joueur) throws ExceptionRareteDepasse{
        if (joueur.compteurRare < maxExemplaire) {
            Carte nouvelleCarte;
            joueur.compteurRare++;
            nouvelleCarte = new CarteRare(joueur, joueur.compteurRare);
            return nouvelleCarte;
        }
        throw new ExceptionRareteDepasse("Nombre de carte de cette rareté du joueur depassé");

    }

    @Override
    public String toString() {
        return super.toString() + ";R";
    }
}
