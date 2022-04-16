package com.javafootball.Model.Match;

import com.javafootball.Model.Exception.*;
import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Joueur.Poste;

import java.util.ArrayList;
import java.util.List;

public class EquipeJeu {
    public List<Carte> compositionCarte;

    public EquipeJeu() {
        this.compositionCarte = new ArrayList<>();
    }

    public boolean aSoumisEquipe() {
        return !this.compositionCarte.isEmpty();
    }


    static public void equipeValide(List<Carte> propositionCarte) throws ExceptionEquipeNonValide {
        int cptGK = 0;
        if (propositionCarte.size() != 4) {
            throw new ExceptionEquipeNonValide("L'équipe n'est pas complète, il faut 4 carte et non " + propositionCarte.size() + ".");
        }
        for (Carte c : propositionCarte) {
            if (c.joueur.poste == Poste.GOALKEEPER) {
                cptGK++;
            }
        }
        if (cptGK < 1) {
            throw new ExceptionEquipeNonValide("Il faut 1 gardien dans l'équipe.");
        }

        if (cptGK > 1) {
            throw new ExceptionEquipeNonValide("Il faut ne faut qu'un seul gardien dans l'équipe.");
        }

        for (int i = 0; i < propositionCarte.size(); i++) {
            for (int j = 0; j < propositionCarte.size(); j++) {
                if (i != j) {
                    Carte c1 = propositionCarte.get(i);
                    Carte c2 = propositionCarte.get(j);
                    if (c1.joueur.equals(c2.joueur)) {
                        throw new ExceptionEquipeNonValide("Il y a deux fois le joueur " + c1.joueur.denomination() + " dans l'équipe");
                    }
                }
            }
        }
    }

    public void setEquipe(List<Carte> compositionCarte) {
        this.compositionCarte = compositionCarte;
    }
}
