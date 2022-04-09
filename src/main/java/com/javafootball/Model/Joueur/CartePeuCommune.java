package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.ExceptionRareteDepasse;

public class CartePeuCommune extends Carte {

    final static public int maxExemplaire = 100;

    private CartePeuCommune(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
        this.coefficient = 1.05F;
        this.lienFondCarte = "https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_1_3.png";
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
