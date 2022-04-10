package com.javafootball.Model.Utilisateur;

import com.javafootball.Model.Joueur.Carte;

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
    public void depenserArgent(int montant) {    }

    @Override
    public void donnerCarte(Carte carte) {      }

    @Override
    public void recevoirCarte(Carte carte) {    }

    @Override
    public void recevoirArgent(int montant) {    }

    @Override
    public String toString() {
        return pseudo + ';' + motDePasse;
    }
}
