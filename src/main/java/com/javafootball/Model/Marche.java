package com.javafootball.Model;

import com.javafootball.Model.Exception.ExceptionPoste;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Utilisateur.Admin;
import com.javafootball.Model.Utilisateur.Utilisateur;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Marche {
    public List<Vente> carteAVendre;
    public List<Joueur> joueursExistant;
    List<Equipe> equipesExistante;

    public Marche() {
        this.carteAVendre = new ArrayList<>();
        this.joueursExistant = new ArrayList<>();
        this.equipesExistante = new ArrayList<>();
    }

    public void initialisationBoutique(String cheminVersFichier) {
        try {
            File dataFile = new File(cheminVersFichier);
            if (dataFile.createNewFile()) {
                System.out.println("Le fichier " + dataFile.getName() + " à été créé");
            } else {
                System.out.println("Le fichier de bd boutique est déjà présent.");
                try {
                    Scanner myReader = new Scanner(dataFile);
                    while (myReader.hasNextLine()) {
                        String row = myReader.nextLine();
                        String [] splittedRow = row.split(";");

                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Une erreur est survenue dans la lecture du fichier de boutique.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue dans la création du fichier de boutique.");
            e.printStackTrace();
        }
    }

    public void initialisationJoueur(String cheminVersFichier) {
        try {
            File dataFile = new File(cheminVersFichier);
            if (!dataFile.createNewFile()) {
                try {
                    Scanner myReader = new Scanner(dataFile);
                    boolean equipeAjoutee = false;
                    Equipe equipeFichier = null;   // L'équipe concernant le fichier de données
                    while (myReader.hasNextLine()) {
                        String row = myReader.nextLine();
                        String [] splittedRow = row.split(",");

                        if(!equipeAjoutee) {
                            String nomEquipe = splittedRow[0];
                            equipeFichier = new Equipe(nomEquipe);
                            this.equipesExistante.add(equipeFichier);
                            equipeAjoutee = true;
                        }

                        String [] prenomNomJoueur = splittedRow[1].split(" ");
                        String prenomJoueur = prenomNomJoueur[0];
                        String nomJoueur = switch (prenomNomJoueur.length) {
                            case 1 -> "";
                            case 2 -> prenomNomJoueur[1];
                            default -> // Joueur avec plusieurs noms de famille
                                    String.join(" ", Arrays.copyOfRange(prenomNomJoueur, 1, prenomNomJoueur.length - 1));
                        };

                        String poste = splittedRow[2];
                        Joueur nouveauJoueur;
                        if(poste.equals("G")) { // Le joueur est un gardien
                            nouveauJoueur = new JoueurGardien(prenomJoueur, nomJoueur, equipeFichier);
                        } else {
                            nouveauJoueur = new JoueurDeChamp(prenomJoueur, nomJoueur, Poste.getPoste(poste), equipeFichier);
                        }

                        this.joueursExistant.add(nouveauJoueur);

                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Une erreur est survenue dans la lecture du fichier de données des joueurs.");
                    e.printStackTrace();
                } catch (ExceptionPoste e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue dans la lecture du fichier de données des joueurs.");
            e.printStackTrace();
        }
    }





    public void ajouterCarteAVendre(Vente v) {
        carteAVendre.add(v);
    }

    public void ajouterCarteAVendre(Carte c, Utilisateur vendeur) {
        carteAVendre.add(new Vente(c, vendeur));
    }

    public void ajouterCarteAVendre(Carte c) {
        carteAVendre.add(new Vente(c, null));
    }
}

