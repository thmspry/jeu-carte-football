package com.javafootball.Model;

import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Utilisateur.Utilisateur;

public class Vente {
    public Carte carteAVendre;
    public Utilisateur vendeur;
    public int prix;

    public Vente(Carte carteAVendre, Utilisateur vendeur, int prix) {
        this.carteAVendre = carteAVendre;
        this.vendeur = vendeur;
        this.prix = prix;
    }

    public String getPseudoVendeur() {
        return vendeur.getPseudoVendeur();
    }

    @Override
    public String toString() {
        return carteAVendre.toStringVerbeux() + ";" + getPseudoVendeur() + ";" + prix;
    }
}
