package com.javafootball.Model.Joueur;

import com.javafootball.Model.Exception.ExceptionPoste;

public class JoueurDeChamp extends Joueur {
    public JoueurDeChamp(String prenom, String nom, Poste poste, Equipe equipe) throws ExceptionPoste {
        super(prenom, nom, equipe);
        if (poste != Poste.GOALKEEPER) {
            this.poste = poste;
        } else {
            throw new ExceptionPoste("Erreur de poste, il doit être different de GK pour un joueur de champs (" + poste + " donné).");
        }
    }


}
