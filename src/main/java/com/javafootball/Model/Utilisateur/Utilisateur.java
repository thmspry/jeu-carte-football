package com.javafootball.Model.Utilisateur;


import com.javafootball.Model.Exception.ExceptionTransaction;
import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Vente;

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
     * Méthode permettant de réaliser une vente
     * L'utilisateur courant va acheter la carte vendue par le vendeur passé en paramètre
     * @param vente : la vente mise en jeu
     */
    public void faireTransaction(Vente vente) throws ExceptionTransaction {
        Utilisateur vendeur = vente.vendeur;
        int montantTransaction = vente.prix;
        Carte carteEnJeu = vente.carteAVendre;

        // Il peut acheter s'il a asser d'argent, et que ce n'est pas sa carte
        if (this.aLesMoyens(vente) && !this.equals(vendeur)) {
            this.depenserArgent(montantTransaction);
            this.recevoirCarte(carteEnJeu);

            vente.vendeur.perdreCarte(carteEnJeu);
            vente.vendeur.recevoirArgent(montantTransaction);
        } else if (this.equals(vendeur)) {
            throw new ExceptionTransaction("Vous ne pouvez pas acheter votre propre carte.");
        } else {
            throw new ExceptionTransaction("Vous ne possedez pas assez de Zimdim Coin pour acheter cette carte.");
        }
    }

    abstract boolean aLesMoyens(Vente vente);

    public abstract void recevoirCarte(Carte carte);

    abstract void perdreCarte(Carte carte);

    abstract void depenserArgent(int montant);

    abstract void recevoirArgent(int montant);

    public abstract String getPseudoVendeur();

}
