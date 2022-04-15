package com.javafootball.Model;

import com.javafootball.Model.Exception.ExceptionConnexion;
import com.javafootball.Model.Exception.ExceptionFichier;
import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Utilisateur.Admin;
import com.javafootball.Model.Utilisateur.Utilisateur;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SystemeDonnee {
    public Marche marche;
    public MatchHebdo matchHebdoSemaineDerniere;
    public MatchHebdo matchHebdoSemaineProchaine;
    public HashMap<String, Utilisateur> utilisateurs;

    public SystemeDonnee() {
        this.marche = new Marche();
        this.matchHebdoSemaineDerniere = new MatchHebdo();
        this.matchHebdoSemaineProchaine = new MatchHebdo();
        this.utilisateurs = new HashMap<>();
    }

    public Utilisateur verifCoupleLogin(String pseudo, String motDePasse) throws ExceptionConnexion {

        if (this.utilisateurs.containsKey(pseudo)) {
            Utilisateur resUti = utilisateurs.get(pseudo);
            if (resUti.motDePasse.equals(motDePasse)) {
                return resUti;
            }
            throw new ExceptionConnexion("Le mot de passe n'est pas le bon");
        }
        throw new ExceptionConnexion("Ce compte n'existe pas");
    }

    /**
     * Ajoute un utilisateur dans la liste d'utilisateur, ainsi que dans le fichier de sauvegarde
     *
     * @param nouvelUtilisateur : l'utilisateur à ajouter
     */
    public void enregistrerUtilisateur(Utilisateur nouvelUtilisateur, String cheminVersFichier) throws IOException, ExceptionConnexion {
        if (!this.utilisateurs.containsKey(nouvelUtilisateur.pseudo)) {
            this.utilisateurs.put(nouvelUtilisateur.pseudo, nouvelUtilisateur);
            FileWriter fw = new FileWriter(cheminVersFichier, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(nouvelUtilisateur.toString());
            bw.newLine();
            bw.close();
        } else {
            throw new ExceptionConnexion("Ce pseudo est déjà utilisé");
        }
    }


    /**
     * Lit le fichier de données des utilisateurs pour renseigner sa HahsMap d'utilisateur
     *
     * @param cheminVersFichier : le chemin d'accès vers le fichier de données
     */
    public void parseUtilisateur(String cheminVersFichier) {
        try {
            File dataFile = new File(cheminVersFichier);
            if (dataFile.createNewFile()) {
                System.out.println("Le fichier " + dataFile.getName() + " à été créé");
            } else {
                try {
                    Scanner myReader = new Scanner(dataFile);
                    while (myReader.hasNextLine()) {
                        String row = myReader.nextLine();
                        String[] splittedRow = row.split(";");
                        Utilisateur nouvelUtilisateur;
                        String pseudo = splittedRow[0];
                        String motDePasse = splittedRow[1];
                        if (splittedRow.length > 2) {    // Cas utilsateur joueur
                            nouvelUtilisateur = new UtilisateurJoueur(pseudo, motDePasse, Integer.parseInt(splittedRow[2]));
                        } else {    // Cas d'un admin
                            nouvelUtilisateur = new Admin(pseudo, motDePasse);
                        }
                        utilisateurs.put(pseudo, nouvelUtilisateur);

                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Une erreur est survenue dans la lecture du fichier de sauvegarde des utilisateurs.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue dans la création du fichier de sauvegarde des utilisateurs.");
            e.printStackTrace();
        }
    }

    /**
     * Remplace une ligne spécifique d'un fichier par une autre
     *
     * @param numeroLigne : le numéro de la ligne
     * @param data        : chaine de caractère à écrire à la plce
     * @param chemin      : chemin d'accès vers le fichier
     * @throws IOException : Exeption d'ouverture de fichier
     */
    private void remplacerLigne(int numeroLigne, String data, String chemin) throws IOException {
        Path path = Paths.get(chemin);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.set(numeroLigne - 1, data);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    /**
     * Mets à jour les champs de solde d'argent dans le fichier .csv après une transaction validé
     *
     * @param vendeur  : L'utilisateur ayant vendu une carte
     * @param acheteur : L'utilisateur ayant acheté une carte
     */
    public void majFichierUtilisateurApresVente(Utilisateur vendeur, Utilisateur acheteur) {
        final String cheminVersFichier = "src/main/resources/com/javafootball/data/utilisateurs.csv";

        File dataFile = new File(cheminVersFichier);
        int ligneVendeur = 0;
        int ligneAcheteur = 0;
        int compteurLigne = 1;
        try {
            Scanner myReader = new Scanner(dataFile);
            while (myReader.hasNextLine()) {
                String row = myReader.nextLine();
                String[] splittedRow = row.split(";");

                String pseudo = splittedRow[0];
                if (pseudo.equals(vendeur.pseudo)) {
                    ligneVendeur = compteurLigne;
                }

                if (pseudo.equals(acheteur.pseudo)) {
                    ligneAcheteur = compteurLigne;
                }
                compteurLigne++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Une erreur est survenue dans la lecture du fichier de sauvegarde de données utilisateur.");
            e.printStackTrace();
        }

        try {
            remplacerLigne(ligneVendeur, vendeur.toString(), cheminVersFichier);
            remplacerLigne(ligneAcheteur, acheteur.toString(), cheminVersFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void passerSemaineSuivante() throws ExceptionRareteDepasse, FileNotFoundException, ExceptionFichier {
        this.matchHebdoSemaineProchaine.passerSemaineSuivante(this.marche);
        this.matchHebdoSemaineDerniere = this.matchHebdoSemaineProchaine;
        this.matchHebdoSemaineProchaine = new MatchHebdo();
    }
}
