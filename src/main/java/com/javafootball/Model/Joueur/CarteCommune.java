package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.*;
import com.javafootball.Model.Marche.Marche;

import java.util.ArrayList;
import java.util.List;

public class CarteCommune extends Carte {
    final static public int maxExemplaire = 1000;

    private CarteCommune(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
        this.coefficient = 1F;
        this.lienFondCarte = "https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_1_2.png";
        this.rareteLabel = "Commune";
    }

    public static Carte creerCarte(Joueur joueur, Marche marcheActuel, boolean considererMarge) throws ExceptionRareteDepasse {
        if (joueur.compteurCommune < maxExemplaire) {
            // verification qu'il reste au moins la carte réservée aux récompenses hebdo
            if (considererMarge || marcheActuel.resteAuMoinsCommune(2)) {
                Carte nouvelleCarte;
                joueur.compteurCommune++;
                nouvelleCarte = new CarteCommune(joueur, joueur.compteurCommune);
                return nouvelleCarte;
            } else {
                throw new ExceptionRareteDepasse("Il ne reste plus qu'une carte commune créable sur tout le set de joueur, elle est réservée a la récompense hebdo du 3ème mailleur joueur.");
            }
        }
        throw new ExceptionRareteDepasse("La limite de " + maxExemplaire + " cartes communes de ce joueur à été atteinte.");
    }

    public static Carte creerCarteAleatoire(Marche marcheActuel) throws ExceptionRareteDepasse {
        List<Joueur> listeJoueur = new ArrayList<>(List.copyOf(marcheActuel.joueursExistant));

        while (true) {
            Joueur j = Marche.getJoueurAleatoire(listeJoueur);
            try {
                return creerCarte(j, marcheActuel, false);
            } catch (ExceptionRareteDepasse e) {
                e.printStackTrace();
                listeJoueur.remove(j);
                if (listeJoueur.size() == 0) {
                    throw new ExceptionRareteDepasse("Plus aucune commune carte n'est disponible");
                }
            }
        }
    }


    @Override
    public String toString() {
        return super.toString() + ", Commune";
    }

}
