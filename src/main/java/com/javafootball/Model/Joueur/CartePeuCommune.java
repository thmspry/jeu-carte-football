package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Marche.Marche;
import com.javafootball.Model.Utils;

import java.util.ArrayList;
import java.util.List;

public class CartePeuCommune extends Carte {

    final static public int maxExemplaire = 100;    // on aura au maximum 100 cartes peu communes d'un même joueur

    private CartePeuCommune(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
        this.coefficient = 1.05F;
        // Image de fond de carte doré évoquant quelque chose de plus raffiné
        this.lienFondCarte = "https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_1_3.png";
        this.rareteLabel = "Peu commune";
    }

    /**
     * Méthode static fournissant une nouvelle carte d'un joueur en particulier
     * @param joueur : le joueur concerné par la carte
     * @param marcheActuel : le marché actuel
     * @param considererMarge : boolean indiquant s'il faut considerer la marge de carte réservée à la récompense hebdomadaire
     * @return la carte créer dans le cas d'aucune exception
     * @throws ExceptionRareteDepasse : Exception indiquant dans quel cas la limite de carte de cette rareté à été dépassée
     */
    public static Carte creerCarte(Joueur joueur, Marche marcheActuel, boolean considererMarge) throws ExceptionRareteDepasse {
        if (considererMarge || joueur.compteurPeuCommune < maxExemplaire) {
            // verification qu'il reste au moins la carte réservée aux récompenses hebdo
            if (marcheActuel.resteAuMoinsPeuCommune(2)) {
                Carte nouvelleCarte;
                joueur.incrementerCompteurPeuCommune();
                nouvelleCarte = new CartePeuCommune(joueur, joueur.compteurPeuCommune);
                return nouvelleCarte;
            } else {
                throw new ExceptionRareteDepasse("Il ne reste plus qu'une carte peu commune créable sur tout le set de joueur, elle est réservée a la récompense hebdo du 2ème mailleur joueur.");
            }
        }
        throw new ExceptionRareteDepasse("La limite de " + maxExemplaire + " cartes peu communes de ce joueur à été atteinte.");
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
                    throw new ExceptionRareteDepasse("Plus aucune peu commune carte n'est disponible");
                }
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Peu commune";
    }
}
