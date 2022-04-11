package com.javafootball.Model;

import com.javafootball.Model.Exception.*;
import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Joueur.JoueurGardien;

import java.util.ArrayList;
import java.util.List;

public class EquipeJeu {
    public List<Carte> compositionCarte;

    public EquipeJeu() {
        this.compositionCarte = new ArrayList<>();
    }

    /*public boolean equipeValide() throws ExceptionEquipeNonValide {
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
    }*/

    static public void equipeValide(List<Carte> propositionCarte) throws ExceptionEquipeNonValide {
        int cptGK = 0;
        if (propositionCarte.size() != 4) {
            throw new ExceptionEquipeNonValide("L'équipe n'est pas complète, il faut 4 carte et non " + propositionCarte.size() + ".");
        }
        for (Carte c : propositionCarte) {
            if (c.joueur instanceof JoueurGardien) {
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

    /*public boolean ajouterCarte(Carte carte) throws ExceptionEquipeNonValide {
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

    public boolean remplacerJoueur(Carte JoueurSortant, Carte JoueurEntrant) {
        if (compositionCarte.isEmpty()) {
            System.out.println("Equipe vide");
            return false;
        } else if (compositionCarte.contains(JoueurSortant)) {
            int index = compositionCarte.indexOf(JoueurSortant);
            compositionCarte.set(index, JoueurEntrant);
            try {
                if (equipeValide()) {
                    return true;
                }
            } catch (ExceptionEquipeNonValide exceptionEquipeNonValide) {
                exceptionEquipeNonValide.printStackTrace();
            }
        } else {
            System.out.println("Joueur non present dans l'équipe");
            return false;
        }

        return false;
    }*/
}
