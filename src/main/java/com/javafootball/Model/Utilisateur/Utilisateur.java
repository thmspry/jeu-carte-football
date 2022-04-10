package com.javafootball.Model.Utilisateur;


import com.javafootball.Model.Joueur.Carte;

abstract public class Utilisateur {
    public String pseudo;
    public String motDePasse;
    public String nomVue;

    public Utilisateur(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    public abstract void recevoirCarte(Carte carte);

    public abstract void donnerCarte(Carte carte);

    public abstract void depenserArgent(int montant);

    public abstract void recevoirArgent(int montant);

    public abstract String getPseudoVendeur();

}
