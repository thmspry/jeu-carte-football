package com.javafootball.Model.Joueur;

public class CartePeuCommune extends Carte{

    CartePeuCommune(int numero, Joueur joueur){
        this.numero = numero;
        this.joueur = joueur;
        this.maxExemplaire = 100;
        this.coefficient =  1.05F;
    }


    @Override
    public String toString() {
        return (this.joueur.prenom + this.joueur.nom +" numéro "+this.numero);
    }
}
