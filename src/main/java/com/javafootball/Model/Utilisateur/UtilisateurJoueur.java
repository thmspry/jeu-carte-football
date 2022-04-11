package com.javafootball.Model.Utilisateur;

import com.javafootball.Model.EquipeJeu;
import com.javafootball.Model.Joueur.Carte;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurJoueur extends Utilisateur {

    public int argent;
    public List<Carte> listeCarte;
    public EquipeJeu sonEquipe;


    public UtilisateurJoueur(String pseudo, String motDePasse, int argent) {
        super(pseudo, motDePasse);
        this.argent = argent;
        this.listeCarte = new ArrayList<>();
        this.nomVue = "Jeu.fxml";
        this.sonEquipe = new EquipeJeu();

    }

    public UtilisateurJoueur(String pseudo, String motDePasse) {
        super(pseudo, motDePasse);
        this.argent = 10000;
        this.listeCarte = new ArrayList<>();
        this.nomVue = "Jeu.fxml";
        this.sonEquipe = new EquipeJeu();
    }

    @Override
    public String getPseudoVendeur() {
        return pseudo;
    }

    public EquipeJeu getSonEquipe() {
        return sonEquipe;
    }

    @Override
    public void recevoirCarte(Carte carte) {
        this.listeCarte.add(carte);
    }

    @Override
    public void donnerCarte(Carte carte) {
        this.listeCarte.remove(carte);
    }

    @Override
    public void depenserArgent(int montant) {
        argent -= montant;
    }

    @Override
    public void recevoirArgent(int montant) {
        this.argent += montant;
    }

    @Override
    public String toString() {
        return pseudo + ';' + motDePasse + ';'  + argent;
    }
}
