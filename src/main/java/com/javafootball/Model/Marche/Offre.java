package com.javafootball.Model.Marche;

import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Utilisateur.Utilisateur;

public class Offre {
    public Carte carteAVendre;
    public Utilisateur vendeur;
    public int prix;

    public Offre(Carte carteAVendre, Utilisateur vendeur, int prix) {
        this.carteAVendre = carteAVendre;
        this.vendeur = vendeur;
        this.prix = prix;
    }

    public String getPseudoVendeur() {
        return vendeur.getPseudoVendeur();
    }

    @Override
    public String toString() {
        return carteAVendre.toString() + ";" + getPseudoVendeur() + ";" + prix;
    }
}
