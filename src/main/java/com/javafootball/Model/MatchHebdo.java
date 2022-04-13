package com.javafootball.Model;

import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MatchHebdo {
    public static int semaineCompteur = 1;
    public int semaine;
    private final List<UtilisateurJoueur> joueurInscrit;
    private final List<UtilisateurJoueur> lesGagnants;

    public MatchHebdo() {
        this.semaine = semaineCompteur;
        this.joueurInscrit = new ArrayList<>();
        this.lesGagnants = new ArrayList<>();
        semaineCompteur++;
    }

    public void ajoutJoueur(UtilisateurJoueur joueur) {
        joueurInscrit.add(joueur);
    }

    /**
     * Donne le numero de la semaine sous le format deux digit (XX)
     *
     * @return un chaine formatée
     */
    private String indicateurSemaine() {
        String indicateurSemaine;
        if (this.semaine < 10) {
            indicateurSemaine = "0" + this.semaine;
        } else {
            indicateurSemaine = Integer.toString(this.semaine);
        }
        return indicateurSemaine;
    }

    private double scoreUtilisateur(UtilisateurJoueur uti) {
        double scoreTotal = 0;

        final String cheminVersFichier = "src/main/resources/com/javafootball/data/ext/2022_" + this.indicateurSemaine() + "_Nantes.csv";
        File dataFile = new File(cheminVersFichier);
        try {
            EquipeJeu sesCartes = uti.sonEquipe;

            boolean joueurTrouve = false;
            Scanner myReader = new Scanner(dataFile);

            while (myReader.hasNextLine() && !joueurTrouve) {
                String row = myReader.nextLine();
                String[] splittedRow = row.split(",");
                String nom = splittedRow[1];

                for (Carte c : sesCartes.compositionCarte) {
                    String nomPrenom = c.joueur.denomination();

                    if (nom.equals(nomPrenom)) {
                        String scoreString = splittedRow[3];
                        double scoreCarte = Double.parseDouble(scoreString) * c.coefficient;
                        scoreTotal += scoreCarte;
                    }
                }

            }

            myReader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return scoreTotal;
    }

    public void calculScoreAllJoueur() {
        for (UtilisateurJoueur utilisateurJoueur : joueurInscrit) {
            utilisateurJoueur.scoreDeLaSemaine = scoreUtilisateur(utilisateurJoueur);
        }
    }

    public void chercher3Meilleurs() {
        joueurInscrit.sort((o1, o2) -> {
            double diff = o1.scoreDeLaSemaine - o2.scoreDeLaSemaine;
            return (int) diff;
        });
        for(int i = 0; i < joueurInscrit.size() && i < 3; i++) {
            lesGagnants.add(joueurInscrit.get(i));
        }
    }


    public void recompenseGagnant(Marche marche) throws ExceptionRareteDepasse {
        if(!lesGagnants.isEmpty()) {
            Carte cartePour1er = CarteRare.creerCarteAleatoire(marche);
            lesGagnants.get(0).recevoirCarte(cartePour1er);
        }

        if(lesGagnants.size() > 1) {
            Carte cartePour2eme = CartePeuCommune.creerCarteAleatoire(marche);
            lesGagnants.get(1).recevoirCarte(cartePour2eme);
        }

        if(lesGagnants.size() > 2) {
            Carte cartepour3eme = CarteCommune.creerCarteAleatoire(marche);
            lesGagnants.get(2).recevoirCarte(cartepour3eme);
        }
    }

    public void passerSemaineSuivante(Marche marche) throws ExceptionRareteDepasse {
        calculScoreAllJoueur();
        chercher3Meilleurs();
        recompenseGagnant(marche);
    }

    @Override
    public String toString() {
        return "Semaine " + this.semaine;
    }
}
