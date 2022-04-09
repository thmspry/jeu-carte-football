package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.ExceptionPoste;

import java.util.Arrays;

public enum Poste {
    GOALKEEPER("G"), DEFENSEUR("D"), MIDDLEFIELD("M"),FORWARD("F");

    private final String abreviation;


    Poste(String abreviation) {
        this.abreviation = abreviation;
    }

    static public Poste getPoste(String abreviation) throws ExceptionPoste {

        return switch (abreviation) {
            case "G" -> Poste.GOALKEEPER;
            case "D" -> Poste.DEFENSEUR;
            case "M" -> Poste.MIDDLEFIELD;
            case "F" -> Poste.FORWARD;
            default -> throw new ExceptionPoste("Aucun poste valide");
        };
    }

    public String getAbreviation(){
        return this.abreviation;
    }

    public String getAbreviationSimplifie() {
        if(this == GOALKEEPER) {
            return "Gardien";
        } else {
            return "Joueur de champ";
        }
    }
}
