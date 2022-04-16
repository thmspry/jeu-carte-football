package com.javafootball.Model.Match;

import com.javafootball.Model.Exception.ExceptionFichier;
import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Marche.Marche;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MatchHebdo {
    public static int semaineCompteur = 1;
    public int semaine; // Numéro de la semaine
    public List<File> fichiersMatchs;       // Liste de fichier décrivant les matchs à venir pour la semaine
    public List<File> fichiersResultats;    // Liste de fichier décrivant le score de joueur ayant participé à un match
    private final List<UtilisateurJoueur> utilisateursInscrits;    // Liste des utilisateurs ayant soumis une équipe pour la semaine
    private final List<UtilisateurJoueur> lesGagnants;             // Liste des gagnants de la semaine

    public MatchHebdo() {
        this.semaine = semaineCompteur;
        this.utilisateursInscrits = new ArrayList<>();
        this.lesGagnants = new ArrayList<>();
        this.fichiersMatchs = new ArrayList<>();
        this.fichiersResultats = new ArrayList<>();
        semaineCompteur++;
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
        utilisateursInscrits.add(joueur);
    }

    /**
     * Donne une liste de chaine represent chacune un match prévu pour la semaine
     * @return la liste des chaines
     * @throws FileNotFoundException : le fichier des matchs n'a pas été trouvé
     */
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

    /**
     * Calcul du score d'un utilisateur suivant son équipe et les résultats de la semaine
     * @param uti : l'utilisateur
     * @return le score de l'utilisateur pour la semaine
     * @throws FileNotFoundException : le fichier de résultat n'a pas été trouvé
     */
    private double scoreUtilisateur(UtilisateurJoueur uti) throws FileNotFoundException {
        double scoreTotal = 0;
        for (File fichierResultat : this.fichiersResultats) {   // Pour chaque fichier de résultat d'équipe fourni
            EquipeJeu sesCartes = uti.equipe;

            Scanner myReader = new Scanner(fichierResultat);

            while (myReader.hasNextLine()) {    // On parcours le fichier ligne par ligne
                String row = myReader.nextLine();
                String[] splittedRow = row.split(",");
                String prenomNomFichier = splittedRow[1]; // Concatenation du prénom et du nom du joueur dans le fichier

                for (Carte c : sesCartes.compositionCarte) {    // Pour chaque carte de l'équipe de l'utilisateur
                    String nomPrenom = c.joueur.denomination();

                    if (prenomNomFichier.equals(nomPrenom)) {   // Un joueur du fichier de résultat est présent dans l'équipe de l'utilisateur
                        String scoreString = splittedRow[2];
                        /* "Les points obtenus par une carte sont proportionnels à la performance du joueur
                        lors de son match et à un facteur dépendant de la rareté de cette carte." */
                        double scoreCarte = Double.parseDouble(scoreString) * c.coefficient;
                        scoreTotal += scoreCarte;
                    }
                }

            }
            myReader.close();
        }

        return scoreTotal;
    }

    /**
     * Calcul du score de chaque joueur ayant soumis une équipe pour la semaine
     * @throws FileNotFoundException : le fichier de résultat n'a pas été trouvé
     */
    private void calculScoreAllJoueur() throws FileNotFoundException {
        for (UtilisateurJoueur utilisateurJoueur : utilisateursInscrits) {
            utilisateurJoueur.scoreDeLaSemaine = scoreUtilisateur(utilisateurJoueur);
        }
    }

    /**
     * Ajoute à la liste des gagnants les trois meilleurs utilisateurs de la semaine suivant leur score
     */
    private void chercher3Meilleurs() {
        utilisateursInscrits.sort((o1, o2) -> {
            double diff = o2.scoreDeLaSemaine - o1.scoreDeLaSemaine;
            return (int) diff;
        });

        // Foreach personnalisé (au cas où il y a moins de trois joueurs ayant soumis une équipe pour la semaine)
        for (int i = 0; i < utilisateursInscrits.size() && i < 3; i++) {
            lesGagnants.add(utilisateursInscrits.get(i));
        }
    }


    /**
     * Envoie au gagnant leur récompense respective
     * @param marche : la marché actuel
     * @throws ExceptionRareteDepasse : motif d'exception de rareté
     */
    private void recompenseGagnant(Marche marche) throws ExceptionRareteDepasse {
        if (getPremierGagnant() != null) {
            Carte cartePour1er = CarteRare.creerCarteAleatoire(marche);
            getPremierGagnant().recevoirCarte(cartePour1er);
        }

        if (getDeuxiemeGagnant() != null) {
            Carte cartePour2eme = CartePeuCommune.creerCarteAleatoire(marche);
            getDeuxiemeGagnant().recevoirCarte(cartePour2eme);
        }

        if (getTroisiemeGagnant() != null) {
            Carte cartepour3eme = CarteCommune.creerCarteAleatoire(marche);
            getTroisiemeGagnant().recevoirCarte(cartepour3eme);
        }
    }

    /**
     * Execute toutes les étapes permettant de passer à la semaine suivante
     * @param marche : le marché actuel
     * @throws ExceptionRareteDepasse : motif d'exception de rareté
     * @throws FileNotFoundException : certains fichiers n'ont pas été trouvés
     * @throws ExceptionFichier : certains fichiers n'ont pas été fournis
     */
    public void passerSemaineSuivante(Marche marche) throws ExceptionRareteDepasse, FileNotFoundException, ExceptionFichier {
        /* Pour passer à la semaine suivante, il faut :
            - importer les fichiers recensant les matchs à venir pour informer les utilisateurs
            - importer les fichiers de résultats de la semaine des équipes pour calculer le score des joueurs
                et les récompenser en conséquence.
        */
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

    public UtilisateurJoueur getPremierGagnant() {
        if(!this.lesGagnants.isEmpty()) {
            return this.lesGagnants.get(0);
        } else {
            return null;
        }
    }

    public UtilisateurJoueur getDeuxiemeGagnant() {
        if(this.lesGagnants.size() >= 2) {
            return this.lesGagnants.get(1);
        } else {
            return null;
        }
    }

    public UtilisateurJoueur getTroisiemeGagnant() {
        if(this.lesGagnants.size() >= 3) {
            return this.lesGagnants.get(2);
        } else {
            return null;
        }
    }


    /**
     * Determine si au moins un fichier recesant les matchs de la semaine a été fourni
     * @return : le boolean décrivant la situation
     */
    public boolean comporteFichiersMatch() {
        return !this.fichiersMatchs.isEmpty();
    }

    /**
     * Determine si au moins un fichier de résultat d'une équipe a été fourni
     * @return : le boolean décrivant la situation
     */
    public boolean comporteFichiersResultat() {
        return !this.fichiersResultats.isEmpty();
    }

    @Override
    public String toString() {
        return "Semaine " + this.semaine;
    }
}
