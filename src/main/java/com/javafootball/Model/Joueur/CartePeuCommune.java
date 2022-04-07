package com.javafootball.Model.Joueur;

public class CartePeuCommune extends Carte {

    final static private int maxExemplaire = 100;
    final static private float coefficient = 1.05F;

    public CartePeuCommune(Joueur joueur, int numero) {
        super(joueur);
        this.numero = numero;
    }

    static Carte creerCarte(Joueur joueur) {
        if (joueur.compteurPeuCommune < maxExemplaire) {
            Carte nouvelleCarte;
            joueur.compteurPeuCommune++;
            nouvelleCarte = new CartePeuCommune(joueur, joueur.compteurPeuCommune);
            return nouvelleCarte;
        }
        return null;
    }

    @Override
    public String toString() {
        return (this.joueur.prenom + this.joueur.nom + " numéro " + this.numero);
    }
}
