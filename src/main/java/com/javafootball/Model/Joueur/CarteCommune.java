package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.*;
import com.javafootball.Model.Marche;

import java.util.List;

public class CarteCommune extends Carte{
    final static public int maxExemplaire = 1000;

    private CarteCommune(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
        this.coefficient = 1F;
        this.lienFondCarte = "https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_1_2.png";
        this.rareteLabel = "Commune";
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

    public static Carte creerCarteAleatoire() throws ExceptionRareteDepasse {
        List<Joueur> listeJoueur = Marche.joueursExistant;

        while(true) {
            Joueur j = Marche.getJoueurAleatoire(listeJoueur);
            try {
                return creerCarte(j);
            } catch (ExceptionRareteDepasse e) {
                e.printStackTrace();
                listeJoueur.remove(j);
                if(listeJoueur.size() == 0) {
                    throw new ExceptionRareteDepasse("Plus aucune commune carte n'est disponible");
                }
            }
        }
    }





    @Override
    public String toString() {
        return super.toString() + ";C";
    }

}
