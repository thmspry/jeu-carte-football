package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Marche;

import java.util.List;

public class CartePeuCommune extends Carte {

    final static public int maxExemplaire = 100;

    private CartePeuCommune(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
        this.coefficient = 1.05F;
        this.lienFondCarte = "https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_1_3.png";
        this.rareteLabel = "Peu commune";
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
                    throw new ExceptionRareteDepasse("Plus aucune peu commune carte n'est disponible");
                }
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + ";P";
    }
}
