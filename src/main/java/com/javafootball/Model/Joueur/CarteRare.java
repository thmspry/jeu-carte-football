package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.*;
import com.javafootball.Model.Marche.Marche;

import java.util.ArrayList;
import java.util.List;

public class CarteRare extends Carte {

    final static public int maxExemplaire = 10;

    private CarteRare(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
        this.coefficient = 1.1F;
        this.lienFondCarte = "https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_4_0.png";
        this.rareteLabel = "Rare";
    }

    public static Carte creerCarte(Joueur joueur, Marche marcheActuel, boolean considererMarge) throws ExceptionRareteDepasse{
        if (joueur.compteurRare < maxExemplaire) {
            // verification qu'il reste au moins la carte réservée aux récompenses hebdo
            if(!considererMarge || marcheActuel.resteAuMoinsRare(2)) {
            Carte nouvelleCarte;
            joueur.compteurRare++;
            nouvelleCarte = new CarteRare(joueur, joueur.compteurRare);
            return nouvelleCarte;
            } else {
                throw new ExceptionRareteDepasse("Il ne reste plus qu'une carte rare créable sur tout le set de joueur, elle est réservée a la récompense hebdo du 1er mailleur joueur.");
            }
        }
        throw new ExceptionRareteDepasse("La limite de " + maxExemplaire + " cartes rare de ce joueur à été atteinte.");

    }

    public static Carte creerCarteAleatoire(Marche marcheActuel) throws ExceptionRareteDepasse {
        List<Joueur> listeJoueur = new ArrayList<>(List.copyOf(marcheActuel.joueursExistant));

        while(true) {
            Joueur j = Marche.getJoueurAleatoire(listeJoueur);
            try {
                return creerCarte(j, marcheActuel, false);
            } catch (ExceptionRareteDepasse e) {
                e.printStackTrace();
                listeJoueur.remove(j);
                if(listeJoueur.size() == 0) {
                    throw new ExceptionRareteDepasse("Plus aucune carte rare n'est disponible");
                }
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Rare";
    }
}
