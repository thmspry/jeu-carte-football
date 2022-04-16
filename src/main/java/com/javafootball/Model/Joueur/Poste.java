package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.ExceptionPoste;


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
            default -> throw new ExceptionPoste("Aucun poste valide (poste '" + abreviation + "' donné.");
        };
    }

    public String getAbreviation(){
        return this.abreviation;
    }

    /**
     * Donne une dénomination littérale binaire du poste, soit Gardien, soit Joueur de champ
     * @return la dénomination
     */
    public String denomination() {
        if(this == GOALKEEPER) {
            return "Gardien";
        } else {
            return "Joueur de champ";
        }
    }
}
