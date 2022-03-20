package com.javafootball.Model;

public class Utilisateur {
    String pseudo;
    String motDePasse;
    int argent;

    public Utilisateur(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.argent = 10000;
    }
}
