package com.javafootball.Model;


abstract public class Utilisateur {
    public String pseudo;
    public String motDePasse;
    public String nomVue;

    public Utilisateur(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }
}
