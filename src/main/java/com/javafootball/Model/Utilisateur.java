package com.javafootball.Model;

public class Utilisateur {
    public String pseudo;
    public String motDePasse;
    public int argent;
    public boolean admin;

    public Utilisateur(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.argent = 10000;
        this.admin = false;
    }

    public Utilisateur(String pseudo, String motDePasse, int argent, boolean admin) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.argent = argent;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return pseudo + ';' + motDePasse + ';'  + argent + ';' + admin;
    }
}
