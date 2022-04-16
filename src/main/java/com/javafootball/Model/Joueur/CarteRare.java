package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.*;
import com.javafootball.Model.Marche.Marche;
import com.javafootball.Model.Utils;

import java.util.ArrayList;
import java.util.List;

public class CarteRare extends Carte {

    final static public int maxExemplaire = 10; // on aura en circulation au maximum 10 cartes rares d'un même joueur

    private CarteRare(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
        this.coefficient = 1.1F;
        // Image de fond de carte violet évoquant une rareté exceptionable
        this.lienFondCarte = "https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_4_0.png";
        this.rareteLabel = "Rare";
    }

    /**
     * Méthode static fournissant une nouvelle carte d'un joueur en particulier
     * @param joueur : le joueur concerné par la carte
     * @param marcheActuel : le marché actuel
     * @param considererMarge : boolean indiquant s'il faut considerer la marge de carte réservée à la récompense hebdomadaire
     * @return la carte créer dans le cas d'aucune exception
     * @throws ExceptionRareteDepasse : Exception indiquant dans quel cas la limite de carte de cette rareté à été dépassée
     */
    public static Carte creerCarte(Joueur joueur, Marche marcheActuel, boolean considererMarge) throws ExceptionRareteDepasse{
        if (joueur.compteurRare < maxExemplaire) {
            // verification qu'il reste au moins la carte réservée aux récompenses hebdo
            if(!considererMarge || marcheActuel.resteAuMoinsRare(2)) {
            Carte nouvelleCarte;
            joueur.incrementerCompteurRare();
            nouvelleCarte = new CarteRare(joueur, joueur.compteurRare);
            return nouvelleCarte;
            } else {
                throw new ExceptionRareteDepasse("Il ne reste plus qu'une carte rare créable sur tout le set de joueur, elle est réservée a la récompense hebdo du 1er mailleur joueur.");
            }
        }
        throw new ExceptionRareteDepasse("La limite de " + maxExemplaire + " cartes rare de ce joueur à été atteinte.");

    }

    /**
     * Donne une carte aléatoire, de n'importe quel joueur, de la rareté courante
     * @param marcheActuel : l'état du marché actuel
     * @return la carte aléatoire
     * @throws ExceptionRareteDepasse : exception dans le cas où plus aucune carte de cette rareté est disponible
     */
    public static Carte creerCarteAleatoire(Marche marcheActuel) throws ExceptionRareteDepasse {
        List<Joueur> listeJoueur = new ArrayList<>(List.copyOf(marcheActuel.joueursExistant));

        while(true) {
            Joueur j = Utils.getJoueurAleatoire(listeJoueur);
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
