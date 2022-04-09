package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.ExceptionRareteDepasse;

public class CartePeuCommune extends Carte {

    final static private int maxExemplaire = 100;
    final static private float coefficient = 1.05F;

    private CartePeuCommune(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
    }

    public static Carte creerCarte(Joueur joueur) throws ExceptionRareteDepasse {
        if (joueur.compteurPeuCommune < maxExemplaire) {
            Carte nouvelleCarte;
            joueur.compteurPeuCommune++;
            nouvelleCarte = new CartePeuCommune(joueur, joueur.compteurPeuCommune);
            return nouvelleCarte;
        }
        throw new ExceptionRareteDepasse("Nombre de carte de cette rareté du joueur depassé");
    }

    @Override
    public String toString() {
        return super.toString() + ";P";
    }
}
