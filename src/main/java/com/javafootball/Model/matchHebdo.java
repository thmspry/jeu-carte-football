package com.javafootball.Model;

import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Joueur.Joueur;
import com.javafootball.Model.Utilisateur.Utilisateur;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class matchHebdo {
    public  static int semaine;
    private List<UtilisateurJoueur> joueurInscrit;
    private Map<UtilisateurJoueur, Double> joueurScore;

    public matchHebdo(int semaine) {
        this.semaine = semaine;
        this.joueurInscrit = new ArrayList<UtilisateurJoueur>();
        this.joueurScore = new HashMap<>();
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
            joueurScore.put(joueur, score);
        }
        System.out.println(joueurScore.toString());
    }
}
