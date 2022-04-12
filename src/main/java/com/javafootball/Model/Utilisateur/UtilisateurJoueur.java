package com.javafootball.Model.Utilisateur;

import com.javafootball.Model.EquipeJeu;
import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Vente;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurJoueur extends Utilisateur {

    public int argent;
    public List<Carte> listeCarte;
    public EquipeJeu sonEquipe;
    public double scoreDeLaSemaine;


    public UtilisateurJoueur(String pseudo, String motDePasse, int argent) {
        super(pseudo, motDePasse);
        this.argent = argent;
        this.listeCarte = new ArrayList<>();
        this.nomVue = "Jeu.fxml";
        this.sonEquipe = new EquipeJeu();

    }

    public UtilisateurJoueur(String pseudo, String motDePasse) {
        super(pseudo, motDePasse);
        this.argent = 10000;    // Lorsqu'un utilisateur arrive dans le jeu, il reçoit 10000 (dix-mille) pièces
        this.listeCarte = new ArrayList<>();
        this.nomVue = "Jeu.fxml";
        this.sonEquipe = new EquipeJeu();
        this.scoreDeLaSemaine = 0;
    }

    public boolean aSoumisEquipe() {
        return this.sonEquipe.aSoumisEquipe();
    }


    @Override
    public String getPseudoVendeur() {
        return pseudo;
    }

    @Override
    boolean aLesMoyens(Vente vente) {
        return vente.prix <= this.argent;
    }

    @Override
    public void recevoirCarte(Carte carte) {
        this.listeCarte.add(carte);
    }

    @Override
    void perdreCarte(Carte carte) {
        this.listeCarte.remove(carte);
    }

    @Override
    void depenserArgent(int montant) {
        argent -= montant;
    }

    @Override
    void recevoirArgent(int montant) {
        this.argent += montant;
    }

    @Override
    public String toString() {
        return pseudo + ';' + motDePasse + ';'  + argent;
    }
}
