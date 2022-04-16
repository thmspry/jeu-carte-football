package com.javafootball.Model.Utilisateur;


import com.javafootball.Model.Exception.ExceptionTransaction;
import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Marche.Offre;

import java.util.Objects;

abstract public class Utilisateur {
    public String pseudo;
    public String motDePasse;
    public String nomVue;

    public Utilisateur(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(pseudo, that.pseudo);
    }


    /**
     * Méthode permettant de réaliser la transaction d'une offre
     * L'utilisateur courant va acheter la carte vendue par le vendeur passé en paramètre
     * @param offre : l'offre mise en jeu
     */
    public void faireTransaction(Offre offre) throws ExceptionTransaction {
        Utilisateur vendeur = offre.vendeur;
        int montantTransaction = offre.prix;
        Carte carteEnJeu = offre.carteAVendre;

        // Il peut acheter s'il a asser d'argent, et que ce n'est pas sa carte
        if (this.aLesMoyens(offre) && !this.equals(vendeur)) {
            this.depenserArgent(montantTransaction);
            this.recevoirCarte(carteEnJeu);

            offre.vendeur.perdreCarte(carteEnJeu);
            offre.vendeur.recevoirArgent(montantTransaction);
        } else if (this.equals(vendeur)) {
            throw new ExceptionTransaction("Vous ne pouvez pas acheter votre propre carte.");
        } else {
            throw new ExceptionTransaction("Vous ne possedez pas assez de Zimdim Coin pour acheter cette carte.");
        }
    }

    /**
     * Determiner si l'utilisateur a les moyens d'acheter la carte présente dans l'offre
     * @param offre : l'offre prise en compte
     * @return : true s'il a les moyens, false sinon
     */
    abstract boolean aLesMoyens(Offre offre);

    public abstract void recevoirCarte(Carte carte);

    abstract void perdreCarte(Carte carte);

    abstract void depenserArgent(int montant);

    abstract void recevoirArgent(int montant);

    public abstract String getPseudoVendeur();

}
