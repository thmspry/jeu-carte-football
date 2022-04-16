package com.javafootball.Model.Utilisateur;

import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Marche.Offre;

public class Admin extends Utilisateur{

    public Admin(String pseudo, String motDePasse) {
        super(pseudo, motDePasse);
        this.nomVue = "Admin.fxml";
    }



    @Override
    public String getPseudoVendeur() {
        return "Système";
    }

    @Override
    boolean aLesMoyens(Offre offre) {
        return true;
    }

    @Override
    void depenserArgent(int montant) {    }

    @Override
    void perdreCarte(Carte carte) {      }

    @Override
    public void recevoirCarte(Carte carte) {    }

    @Override
    void recevoirArgent(int montant) {    }

    @Override
    public String toString() {
        return pseudo + ';' + motDePasse;
    }
}
