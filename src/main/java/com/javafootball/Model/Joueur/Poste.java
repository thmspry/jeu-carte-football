package com.javafootball.Model.Joueur;

public enum Poste {
    GOALKEEPER("GK"), DEFENSEUR("D"), MIDDLEFIELD("M"),FORWARD("F");

    private String abreviation;


    private Poste(String abreviation) {
        this.abreviation = abreviation;
    }

    public String getAbreviation(){
        return this.abreviation;
    }
}
