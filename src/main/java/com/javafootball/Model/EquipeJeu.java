package com.javafootball.Model;

import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Joueur.Joueur;
import com.javafootball.Model.Joueur.JoueurGardien;

import java.util.List;

public class EquipeJeu {
    public List<Carte> compositionCarte;


    boolean equipeValide(){  // creer une exception pour ca
        int cptGK =0;
        for (Carte j: compositionCarte) {
            if (j.joueur instanceof JoueurGardien){
                cptGK++;
            }
        }
        if (cptGK != 1){
            System.out.println("Il faut 1 gardien");
            return false;
        }
        return true;
    }

    boolean AjouterJoueur(Carte carte){ // creer une exception pour ca
        String leNom = carte.joueur.nom;
        String lePrenom = carte.joueur.prenom;
        if (compositionCarte.size() >= 4){
            System.out.println("Equipe déja faites");
            return false;
        }else if (compositionCarte.isEmpty()){
            compositionCarte.add(carte);
        }else{
            for (Carte c: compositionCarte) {
                if ((c.joueur.prenom == lePrenom) && (c.joueur.nom == leNom)){
                    System.out.println("Il y a deja ce joueur dans l'equipe");
                    return false;
                }
            }
            compositionCarte.add(carte);
        }
        System.out.println("Joueur ajouté");
        return true;
    }
}
