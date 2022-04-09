package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.*;

public class CarteCommune extends Carte{
    final static private int maxExemplaire = 1000;
    final static private float coefficient = 1F;

    private CarteCommune(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
    }

    public static Carte creerCarte(Joueur joueur) throws ExceptionRareteDepasse {
        if (joueur.compteurCommune < maxExemplaire) {
            Carte nouvelleCarte;
            joueur.compteurCommune++;
            nouvelleCarte = new CarteCommune(joueur, joueur.compteurCommune);
            return nouvelleCarte;
        }
        throw new ExceptionRareteDepasse("Nombre de carte de cette rareté du joueur depassé");
    }



    @Override
    public String toString() {
        return super.toString() + ";C";
    }

}
