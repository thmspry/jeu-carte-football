package com.javafootball.Model.Joueur;

import java.util.Objects;

abstract public class Joueur {
    // Compteurs spécifiques au joueur courant pour chaque rareté
    // Ils permettent de limiter le nombre de cartes de ce même joueur sur les différentes raretés
    public int compteurCommune = 0;
    public int compteurPeuCommune = 0;
    public int compteurRare = 0;

    public final String prenom;
    public final String nom;
    public String lienPhoto;    // Photo qui sera affichée sur sa carte
    public Equipe equipe;
    public Poste poste;


    protected Joueur(String prenom, String nom, Equipe equipe) {
        this.prenom = prenom;
        this.nom = nom;
        this.equipe = equipe;
        // La photo par default est une silhouette de joueur, elle sera remplacée par la suite dans le cas où une photo est fournie pour le joueur
        this.lienPhoto = "https://creazilla-store.fra1.digitaloceanspaces.com/emojis/46301/bust-in-silhouette-emoji-clipart-sm.png";
    }


    public void incrementerCompteurCommune() {
        this.compteurCommune++;
    }
    public void incrementerCompteurPeuCommune() {
        this.compteurPeuCommune++;
    }
    public void incrementerCompteurRare() {
        this.compteurRare++;
    }

    public void setLienPhoto(String lienPhoto) {
        this.lienPhoto = lienPhoto;
    }

    /**
     * Donne la dénomination du joueur, comprenant son prénom suivi de son nom
     * @return : la dénomination
     */
    public String denomination() {
        return this.prenom + " " + this.nom;
    }

    public String denominationPoste() {
        return this.poste.denomination();
    }

    @Override
    public String toString() {
        return this.prenom + " " + this.nom + ", " + this.equipe.nom + ", " + this.poste.getAbreviation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return Objects.equals(prenom, joueur.prenom) && Objects.equals(nom, joueur.nom) && equipe.equals(joueur.equipe);
    }
}
