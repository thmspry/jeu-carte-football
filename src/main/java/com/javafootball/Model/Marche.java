package com.javafootball.Model;

import com.javafootball.Model.Exception.ExceptionPoste;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Utilisateur.Utilisateur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Marche {
    public List<Vente> carteAVendre;
    public static List<Joueur> joueursExistant;
    List<Equipe> equipesExistante;

    public Marche() {
        this.carteAVendre = new ArrayList<>();
        joueursExistant = new ArrayList<>();
        this.equipesExistante = new ArrayList<>();
    }


    public static Joueur getJoueurAleatoire(List<Joueur> joueurList) {
        Random rand = new Random();
        return joueurList.get(rand.nextInt(joueursExistant.size()));
    }


    public void parseJoueurEquipe(String cheminVersFichier) {
        File dataFile = new File(cheminVersFichier);
        if (dataFile.exists()) {
            try {

                Scanner myReader = new Scanner(dataFile, StandardCharsets.UTF_16);
                Equipe equipeCourante = new Equipe("equipefactice");

                while (myReader.hasNextLine()) {
                    String row = myReader.nextLine();
                    String[] splittedRow = row.split(";");

                    String[] prenomNomJoueur = splittedRow[0].split(" ");
                    String prenomJoueur = prenomNomJoueur[0];
                    String nomJoueur = switch (prenomNomJoueur.length) {
                        case 1 -> "";                   // Joueur sans nom de famille (joueur avec surnom)
                        case 2 -> prenomNomJoueur[1];   // Joueur avec un seul nom de famille
                        default ->                      // Joueur avec plusieurs noms de famille
                                String.join(" ", Arrays.copyOfRange(prenomNomJoueur, 1, prenomNomJoueur.length));
                    };

                    String poste = splittedRow[1];
                    Joueur nouveauJoueur;

                    String nomEquipe = splittedRow[2];
                    if (!equipeExiste(nomEquipe)) {
                        equipeCourante = new Equipe(nomEquipe);
                        this.equipesExistante.add(equipeCourante);
                    }

                    if (poste.equals("G")) { // Le joueur est un gardien
                        nouveauJoueur = new JoueurGardien(prenomJoueur, nomJoueur, equipeCourante);
                    } else {
                        nouveauJoueur = new JoueurDeChamp(prenomJoueur, nomJoueur, Poste.getPoste(poste), equipeCourante);
                    }


                    if (splittedRow.length > 3) {    // Un lien pour une photo de joueur est fournis sur la ligne
                        nouveauJoueur.setLienPhoto(splittedRow[3]);
                    }

                    joueursExistant.add(nouveauJoueur);
                    equipeCourante.ajouterJoueur(nouveauJoueur);

                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("Une erreur est survenue dans la lecture du fichier de données des joueurs.");
                e.printStackTrace();
            } catch (ExceptionPoste | IOException e) {
                e.printStackTrace();
            }
        }


    }


    public Vente ajouterCarteAVendre(Carte c, Utilisateur vendeur, int prix) {
        Vente nouvelleVente = new Vente(c, vendeur, prix);
        carteAVendre.add(nouvelleVente);
        return nouvelleVente;
    }

    public boolean equipeExiste(String nomEquipe) {
        for (Equipe e : this.equipesExistante) {
            if (e.nom.equals(nomEquipe)) {
                return true;
            }
        }
        return false;
    }

}

