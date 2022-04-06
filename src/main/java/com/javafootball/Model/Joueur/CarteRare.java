package com.javafootball.Model.Joueur;

public class CarteRare extends Carte{
    CarteRare(int numero, Joueur joueur){
        this.numero = numero;
        this.joueur = joueur;
        this.maxExemplaire = 10;
        this.coefficient =  1.10F;
    }


    @Override
    public String toString() {
        return (this.joueur.prenom + this.joueur.nom +" numéro "+this.numero);
    }
}
