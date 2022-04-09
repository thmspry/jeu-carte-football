package com.javafootball.Model;

import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Utilisateur.Utilisateur;

public class Vente {
    public Carte carteAVendre;
    public Utilisateur vendeur;

    public Vente(Carte carteAVendre, Utilisateur vendeur) {
        this.carteAVendre = carteAVendre;
        this.vendeur = vendeur;
    }

    public String getPseudoVendeur() {
        if(vendeur != null) {
            return vendeur.pseudo;
        } else {
            return "System";
        }
    }

    @Override
    public String toString() {
        return carteAVendre.toStringVerbeux();
    }
}
