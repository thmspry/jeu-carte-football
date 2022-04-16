package com.javafootball.Model.Marche;

import com.javafootball.Model.Exception.ExceptionPoste;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Utilisateur.Utilisateur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Marche {
    public List<Offre> carteAVendre;        // Liste de Ventes, disponible dans la boutique
    public List<Joueur> joueursExistant;    // Tous les joueurs existant sur le marché, ayant des cartes disponible dans la boutique ou non
    List<Equipe> equipesExistante;

    public Marche() {
        this.carteAVendre = new ArrayList<>();
        this.joueursExistant = new ArrayList<>();
        this.equipesExistante = new ArrayList<>();
    }

    /**
     * Méthode determinant s'il reste au moins un certain nombre de cartes Commune prêtes à être vendue
     * @param montantMinimumDispo : le montant minimum
     * @return : le boolean décrivant la situation
     */
    public boolean resteAuMoinsCommune(int montantMinimumDispo) {
        int dispoCommune = 0;
        for (Joueur j : this.joueursExistant) {
            dispoCommune += CarteCommune.maxExemplaire - j.compteurCommune;
        }
        return dispoCommune >= montantMinimumDispo;
    }

    public boolean resteAuMoinsPeuCommune(int montantMinimumDispo) {
        int dispoPeuCommune = 0;
        for (Joueur j : this.joueursExistant) {
            dispoPeuCommune += CartePeuCommune.maxExemplaire - j.compteurPeuCommune;
        }
        return dispoPeuCommune >= montantMinimumDispo;
    }

    public boolean resteAuMoinsRare(int montantMinimumDispo) {
        int dispoRare = 0;
        for (Joueur j : this.joueursExistant) {
            dispoRare += CarteRare.maxExemplaire - j.compteurRare;
        }
        return dispoRare >= montantMinimumDispo;
    }


    /**
     * Parse un fichier de données comportant le nom des joueurs, leur équipe et leur poste
     * @param cheminVersFichier : le chemin vers le fichier à parser
     */
    public void parseJoueurEquipe(String cheminVersFichier) {
        File dataFile = new File(cheminVersFichier);
        if (dataFile.exists()) {
            try {

                Scanner myReader = new Scanner(dataFile, StandardCharsets.UTF_16);
                Equipe equipeCourante = new Equipe("equipefactice");

                while (myReader.hasNextLine()) {
                    String row = myReader.nextLine();   // Ligne courante
                    String[] splittedRow = row.split(";");  // Separation des champ avec le séparateur

                    String[] prenomNomJoueur = splittedRow[0].split(" ");   // Separation du champ prénom + nom
                    String prenomJoueur = prenomNomJoueur[0];   // Le prénom sera le premier element du champ
                    String nomJoueur = switch (prenomNomJoueur.length) {    // Le nom de famille sera toutes les chaines de caratères qui suivent le prénom
                        case 1 -> "";                   // Joueur sans nom de famille (joueur avec surnom)
                        case 2 -> prenomNomJoueur[1];   // Joueur avec un seul nom de famille
                        default ->                      // Joueur avec plusieurs noms de famille
                                String.join(" ", Arrays.copyOfRange(prenomNomJoueur, 1, prenomNomJoueur.length));
                    };

                    String poste = splittedRow[1];

                    String nomEquipe = splittedRow[2];
                    if (!equipeExiste(nomEquipe)) {
                        equipeCourante = new Equipe(nomEquipe);
                        this.equipesExistante.add(equipeCourante);
                    }

                    Joueur nouveauJoueur;
                    if (poste.equals("G")) { // Le joueur est un gardien
                        nouveauJoueur = new JoueurGardien(prenomJoueur, nomJoueur, equipeCourante);
                    } else {    // Le joueur est un joueur de champ
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


    /**
     * Ajoute une nouvelle offre dans la boutique
     * @param carte : la varte a vendre
     * @param vendeur : le vendeur de la carte
     * @param prix : le prix fixé pour l'offre
     */
    public void ajouterCarteAVendre(Carte carte, Utilisateur vendeur, int prix) {
        Offre nouvelleOffre = new Offre(carte, vendeur, prix);
        carteAVendre.add(nouvelleOffre);
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

