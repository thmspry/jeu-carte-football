package com.javafootball.Model.Utilisateur;

import com.javafootball.Model.Joueur.Carte;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurJoueur extends Utilisateur {

    public int argent;
    public List<Carte> listeCarte;


    public UtilisateurJoueur(String pseudo, String motDePasse, int argent, List<Carte> listeCarte) {
        super(pseudo, motDePasse);
        this.argent = argent;
        this.listeCarte = listeCarte;
        this.nomVue = "Jeu.fxml";
    }

    public UtilisateurJoueur(String pseudo, String motDePasse) {
        super(pseudo, motDePasse);
        this.argent = 10000;
        this.listeCarte = new ArrayList<>();
        this.nomVue = "Jeu.fxml";
    }

    @Override
    public String toString() {
        String informationsUtilisateur = pseudo + ';' + motDePasse + ';'  + argent;
        for (Carte c: listeCarte) {
            informationsUtilisateur += c.toString() + ';';
        }
        return informationsUtilisateur;
    }
}
