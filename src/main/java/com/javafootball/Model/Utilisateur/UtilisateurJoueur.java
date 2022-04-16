package com.javafootball.Model.Utilisateur;

import com.javafootball.Model.Match.EquipeJeu;
import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Marche.Offre;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurJoueur extends Utilisateur {

    public int argent;
    public List<Carte> listeCarte;
    public EquipeJeu equipe;
    public double scoreDeLaSemaine;


    // Constructeur utilisé pour reconstruire la liste des utilisateurs à partir du fichier de données
    public UtilisateurJoueur(String pseudo, String motDePasse, int argent) {
        super(pseudo, motDePasse);
        this.argent = argent;
        this.listeCarte = new ArrayList<>();
        this.nomVue = "Jeu.fxml";
        this.equipe = new EquipeJeu();

    }

    public UtilisateurJoueur(String pseudo, String motDePasse) {
        super(pseudo, motDePasse);
        this.argent = 10000;    // Lorsqu'un utilisateur arrive dans le jeu, il reçoit 10000 (dix-mille) pièces
        this.listeCarte = new ArrayList<>();
        this.nomVue = "Jeu.fxml";
        this.equipe = new EquipeJeu();
        this.scoreDeLaSemaine = 0;
    }

    public boolean aSoumisEquipe() {
        return this.equipe.aSoumisEquipe();
    }


    @Override
    public String getPseudoVendeur() {
        return pseudo;
    }

    @Override
    boolean aLesMoyens(Offre offre) {
        return offre.prix <= this.argent;
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
        this.argent -= montant;
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
