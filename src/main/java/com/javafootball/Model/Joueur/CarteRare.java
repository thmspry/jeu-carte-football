package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.*;

public class CarteRare extends Carte {

    final static public int maxExemplaire = 10;

    private CarteRare(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
        this.coefficient = 1.1F;
        this.lienFondCarte = "https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_4_0.png";
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
