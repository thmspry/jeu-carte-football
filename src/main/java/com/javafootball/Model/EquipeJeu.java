package com.javafootball.Model;

import com.javafootball.Model.Exception.ExceptionEquipeNonValide;
import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Joueur.JoueurGardien;

import java.util.ArrayList;
import java.util.List;

public class EquipeJeu {
    public List<Carte> compositionCarte;

    public EquipeJeu() {
        this.compositionCarte = new ArrayList<Carte>();
    }

    public boolean equipeValide() throws ExceptionEquipeNonValide {  // creer une exception pour ca
        int cptGK = 0;
        for (Carte j : compositionCarte) {
            if (j.joueur instanceof JoueurGardien) {
                cptGK++;
            }
        }
        if (cptGK != 1) {
            throw new ExceptionEquipeNonValide("Il faut 1 gardien");
        }
        return true;
    }

    public boolean ajouterJoueur(Carte carte) throws ExceptionEquipeNonValide { // creer une exception pour ca
        String leNom = carte.joueur.nom;
        String lePrenom = carte.joueur.prenom;
        if (compositionCarte.size() >= 4) {
            System.out.println("Equipe déja faite");
            return false;
        } else if (compositionCarte.isEmpty()) {
            System.out.println("Joueur ajouté");
            compositionCarte.add(carte);
        } else {
            for (Carte c : compositionCarte) {
                if (c.joueur.prenom.equals(lePrenom) && c.joueur.nom.equals(leNom)) {
                    throw new ExceptionEquipeNonValide(c.joueur, "Il y a deja ce joueur dans l'equipe");

                }
            }
            try {
                compositionCarte.add(carte);
                equipeValide();
            } catch (Exception e) {
                System.out.println("Probleme d'équipe");
                compositionCarte.remove(carte);
                return false;
            }

            System.out.println("Joueur ajouté");
            return true;
        }
        return false;
    }
}
