package com.javafootball.Model.Match;

import com.javafootball.Model.Exception.*;
import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Joueur.Poste;

import java.util.ArrayList;
import java.util.List;

public class EquipeJeu {
    public List<Carte> compositionCarte;    // Liste de 4 cartes composant une équipe de la semaine

    public EquipeJeu() {
        this.compositionCarte = new ArrayList<>();
    }

    public boolean aSoumisEquipe() {
        // Une équipe est soumise si elle a été validée lors de la phase de validation, la liste est alors remplie dans ce cas là
        return !this.compositionCarte.isEmpty();
    }


    static public void equipeValide(List<Carte> propositionCarte) throws ExceptionEquipeNonValide {
        int nombreGK = 0;
        if (propositionCarte.size() != 4) {
            throw new ExceptionEquipeNonValide("L'équipe n'est pas complète, il faut 4 carte et non " + propositionCarte.size() + ".");
        }
        for (Carte c : propositionCarte) {  // Compte le nombre de gardien la proposition
            if (c.joueur.poste == Poste.GOALKEEPER) {
                nombreGK++;
            }
        }
        if (nombreGK < 1) {
            throw new ExceptionEquipeNonValide("Il faut 1 gardien dans l'équipe.");
        }

        if (nombreGK > 1) {
            throw new ExceptionEquipeNonValide("Il faut ne faut qu'un seul gardien dans l'équipe.");
        }

        // Double boucle permettant de vérifier s'il n'y a pas deux fois le même joueur dans la proposition d'équipe
        for (int i = 0; i < propositionCarte.size(); i++) {
            for (int j = 0; j < propositionCarte.size(); j++) {
                if (i != j) {   // Ne pas comparer un joueur avec lui-même
                    Carte c1 = propositionCarte.get(i);
                    Carte c2 = propositionCarte.get(j);
                    if (c1.joueur.equals(c2.joueur)) {  // Deux cartes d'indice différente dans la liste réfèrent au même joueur
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
