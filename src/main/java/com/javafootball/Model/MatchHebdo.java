package com.javafootball.Model;

import com.javafootball.Model.Exception.ExceptionFichier;
import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MatchHebdo {
    public static int semaineCompteur = 1;
    public int semaine;
    public List<File> fichiersMatchs;
    public List<File> fichiersResultats;
    private final List<UtilisateurJoueur> joueurInscrit;
    private final List<UtilisateurJoueur> lesGagnants;

    public MatchHebdo() {
        this.semaine = semaineCompteur;
        this.joueurInscrit = new ArrayList<>();
        this.lesGagnants = new ArrayList<>();
        this.fichiersMatchs = new ArrayList<>();
        this.fichiersResultats = new ArrayList<>();
        semaineCompteur++;
    }

    public int getNumSemaine() {
        return semaine;
    }

    public int nombreFichierMatch() {
        return this.fichiersMatchs.size();
    }

    public int nombreFichierResultat() {
        return this.fichiersResultats.size();
    }

    public void ajouterFichierMatch(File fichierMatch) {
        this.fichiersMatchs.add(fichierMatch);
    }

    public void ajouterFichierResultat(File fichierResultat) {
        this.fichiersResultats.add(fichierResultat);
    }

    public void ajoutJoueur(UtilisateurJoueur joueur) {
        joueurInscrit.add(joueur);
    }

    public List<String> matchSemaine() throws FileNotFoundException {
        List<String> matchs = new ArrayList<>();
        for(File fichierMatch : this.fichiersMatchs) {

            Scanner myReader = new Scanner(fichierMatch);

            while (myReader.hasNextLine()) {
                String row = myReader.nextLine();
                matchs.add(row);
            }
            myReader.close();
        }
        return matchs;
    }

    private double scoreUtilisateur(UtilisateurJoueur uti) throws FileNotFoundException {
        double scoreTotal = 0;
        for (File fichierResultat : this.fichiersResultats) {
            EquipeJeu sesCartes = uti.equipe;

            Scanner myReader = new Scanner(fichierResultat);

            while (myReader.hasNextLine()) {
                String row = myReader.nextLine();
                String[] splittedRow = row.split(",");
                String nom = splittedRow[1];

                for (Carte c : sesCartes.compositionCarte) {
                    String nomPrenom = c.joueur.denomination();

                    if (nom.equals(nomPrenom)) {
                        String scoreString = splittedRow[2];
                        double scoreCarte = Double.parseDouble(scoreString) * c.coefficient;
                        scoreTotal += scoreCarte;
                    }
                }

            }
            myReader.close();
        }

        return scoreTotal;
    }

    public void calculScoreAllJoueur() throws FileNotFoundException {
        for (UtilisateurJoueur utilisateurJoueur : joueurInscrit) {
            utilisateurJoueur.scoreDeLaSemaine = scoreUtilisateur(utilisateurJoueur);
        }
    }

    public void chercher3Meilleurs() {
        joueurInscrit.sort((o1, o2) -> {
            double diff = o2.scoreDeLaSemaine - o1.scoreDeLaSemaine;
            return (int) diff;
        });

        // Foreach personnalisé (au cas où il y a moins de trois joueurs inscrits)
        for (int i = 0; i < joueurInscrit.size() && i < 3; i++) {
            lesGagnants.add(joueurInscrit.get(i));
        }
    }


    public void recompenseGagnant(Marche marche) throws ExceptionRareteDepasse {
        if (!lesGagnants.isEmpty()) {
            Carte cartePour1er = CarteRare.creerCarteAleatoire(marche);
            lesGagnants.get(0).recevoirCarte(cartePour1er);
        }

        if (lesGagnants.size() > 1) {
            Carte cartePour2eme = CartePeuCommune.creerCarteAleatoire(marche);
            lesGagnants.get(1).recevoirCarte(cartePour2eme);
        }

        if (lesGagnants.size() > 2) {
            Carte cartepour3eme = CarteCommune.creerCarteAleatoire(marche);
            lesGagnants.get(2).recevoirCarte(cartepour3eme);
        }
    }

    public void passerSemaineSuivante(Marche marche) throws ExceptionRareteDepasse, FileNotFoundException, ExceptionFichier {
        if (comporteFichiersMatch() && comporteFichiersResultat()) {
            calculScoreAllJoueur();
            chercher3Meilleurs();
            recompenseGagnant(marche);
        } else if(comporteFichiersResultat()) {
            throw new ExceptionFichier("Aucun fichier de match n'a été fournis.");
        } else if (comporteFichiersMatch()) {
            throw new ExceptionFichier("Aucun fichier de résultat n'a été fournis.");
        } else {
            throw new ExceptionFichier("Aucun fichier de match ni de résultat n'a été fourni.");
        }
    }

    public String getPremierGagnant() {
        if(!this.lesGagnants.isEmpty()) {
            return this.lesGagnants.get(0).pseudo;
        } else {
            return null;
        }
    }

    public String getDeuxiemeGagnant() {
        if(this.lesGagnants.size() > 1) {
            return this.lesGagnants.get(1).pseudo;
        } else {
            return null;
        }
    }

    public String getTroisiemeGagnant() {
        if(this.lesGagnants.size() > 2) {
            return this.lesGagnants.get(2).pseudo;
        } else {
            return null;
        }
    }



    public boolean comporteFichiersMatch() {
        return !this.fichiersMatchs.isEmpty();
    }

    public boolean comporteFichiersResultat() {
        return !this.fichiersResultats.isEmpty();
    }

    @Override
    public String toString() {
        return "Semaine " + this.semaine;
    }
}
