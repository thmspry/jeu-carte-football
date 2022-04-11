package com.javafootball.Model;

import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Utilisateur.Utilisateur;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class matchHebdo {
    public  static int semaine;
    private List<UtilisateurJoueur> joueurInscrit;
    private Map<UtilisateurJoueur, Double> joueurScore;
    private List<UtilisateurJoueur> lesGagnants;

    public matchHebdo(int semaine) {
        this.semaine = semaine;
        this.joueurInscrit = new ArrayList<UtilisateurJoueur>();
        this.joueurScore = new HashMap<>();
        this.lesGagnants = new ArrayList<>();
    }

    public boolean ajoutJoueur(UtilisateurJoueur joueur){
        if (!joueurInscrit.contains(joueur)) {
            joueurInscrit.add(joueur);
        }
        else {
            System.out.println("joueur deja inscrit");
            return false;
        }
        return true;
    }

    public double scoreJoueur(UtilisateurJoueur uti)  {
        double scoreMax = 0;
        final String cheminVersFichier = "src/main/resources/com/javafootball/data/ext/2022_"+this.semaine+"_Nantes_ext.csv";
        File dataFile = new File(cheminVersFichier);
        try {
            EquipeJeu sesCartes = uti.getSonEquipe();
            for ( Carte c: sesCartes.compositionCarte) {
                String nomPrenom = c.joueur.prenom+" "+c.joueur.nom;
                Scanner myReader = new Scanner(dataFile);
                boolean trouve = false;
                double score = 0;
                while (myReader.hasNextLine() && !trouve) {

                    String row = myReader.nextLine();
                    String [] splittedRow = row.split(",");
                    String nom = splittedRow[1];

                    if (nom.equals(nomPrenom)){
                        String scoreString = splittedRow[3];
                        double lescore = Double.valueOf(scoreString);
                        score = lescore;
                        System.out.println(score);
                        trouve = true;

                    }

                }

                scoreMax+=score;

                myReader.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Une erreur est survenue dans la lecture du fichier de sauvegarde de données utilisateur.");
            e.printStackTrace();
        }
        return  scoreMax;
    }

    public void calculScoreAllJoueur(){
        for (UtilisateurJoueur joueur: joueurInscrit  ) {
            double score = scoreJoueur(joueur);
            joueurScore.putIfAbsent(joueur, score);
        }
        System.out.println(joueurScore.toString());
        System.out.println(gagnant());

    }

    public List<UtilisateurJoueur> gagnant(){
        double scoremax = 0;
        double scoremax2 = 0;
        double scoremax3 = 0;
        List<UtilisateurJoueur> gagnant = new ArrayList<>();
        for (UtilisateurJoueur joueur:joueurInscrit ) {
            double scoretest = joueurScore.get(joueur);
            if (scoretest > scoremax){
                double ancienScore = scoremax;
                scoremax = scoretest;
                if (gagnant.isEmpty()){
                    gagnant.add(joueur);
                }else {
                    if (ancienScore != 0){
                        UtilisateurJoueur ancien1 = gagnant.get(0);

                        UtilisateurJoueur ancien2 = gagnant.get(1);
                        scoremax3 = scoremax2;
                        scoremax2 = ancienScore;
                        gagnant.set(1,ancien1);
                        gagnant.set(2, ancien2);
                    }
                    gagnant.set(0, joueur);
                }
            }else if(scoretest > scoremax2){
                scoremax2 = scoretest;
                if (gagnant.get(1) == null){
                    gagnant.add(joueur);
                }else {
                    double ancienscore2 = scoremax2;
                    if (ancienscore2 != 0){
                        UtilisateurJoueur ancien2 = gagnant.get(1);
                        scoremax3 = ancienscore2;
                        gagnant.set(2,ancien2);
                    }
                    gagnant.set(1, joueur);
                }
            }else if(scoretest > scoremax3){
                scoremax3 = scoretest;
                if (gagnant.get(2) == null){
                    gagnant.add(joueur);
                }else {
                    gagnant.set(2, joueur);
                }
            }

        }
        System.out.println(joueurInscrit.size());
        System.out.println(gagnant.size());
        return gagnant;

    }

    public void recompenseGagnant(Joueur j1, Joueur j2, Joueur j3){
        try {
            Carte cartePour1er = CarteRare.creerCarte(j1);
            Carte cartePour2eme = CartePeuCommune.creerCarte(j2);
            Carte cartepour3eme = CarteCommune.creerCarte(j3);

            lesGagnants.get(0).donnerCarte(cartePour1er);
            lesGagnants.get(1).donnerCarte(cartePour2eme);
            lesGagnants.get(3).donnerCarte(cartepour3eme);
        } catch (ExceptionRareteDepasse exceptionRareteDepasse) {
            exceptionRareteDepasse.printStackTrace();
        }



    }
}
