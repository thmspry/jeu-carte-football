package com.javafootball.Model.Joueur;

import com.javafootball.Model.Equipe;
import com.javafootball.Model.Exception.ExceptionPoste;

public class JoueurDeChamp extends Joueur{
    public JoueurDeChamp(String prenom, String nom, Poste sonPoste, Equipe equipe) throws ExceptionPoste {
        super(prenom, nom, equipe);
        if(sonPoste == Poste.GOALKEEPER){
            throw new ExceptionPoste("Erreur de poste, il doit etre different de GK pour un joueur de champs");
        }else { this.poste = sonPoste;}
    }

    @Override
    public String toString() {
        return super.toString() + " "+this.poste.getAbreviation();
    }
}
