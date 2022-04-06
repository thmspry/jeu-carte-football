package com.javafootball.Model.Joueur;

public class CarteCommune extends Carte{

    public CarteCommune(int numero, Joueur joueur) {
        this.numero = numero;
        this.joueur = joueur;
        this.maxExemplaire = 1000;
        this.coefficient = 1;
    }

    @Override
    public String toString() {
        return (this.joueur.prenom + this.joueur.nom +" numéro "+this.numero);
    }

}
