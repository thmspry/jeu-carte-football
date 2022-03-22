package com.javafootball.Model;

public class Utilisateur {
    public String pseudo;
    public String motDePasse;
    public int argent;

    public Utilisateur(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.argent = 10000;
    }

    public Utilisateur(String pseudo, String motDePasse, int argent) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.argent = argent;
    }

    @Override
    public String toString() {
        return pseudo + ';' + motDePasse + ';'  + argent;
    }
}
