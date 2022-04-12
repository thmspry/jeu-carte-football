package com.javafootball.Model;

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
    public MatchHebdo matchHebdo;
    public HashMap<String, Utilisateur> utilisateurs;

    public SystemeDonnee() {
        this.marche = new Marche();
        this.matchHebdo = new MatchHebdo();
        this.utilisateurs = new HashMap<>();
    }

    /**
     * Vérifie le couple de pseudo mot de passe dans la liste de l'utilisateur
     * @param pseudo : le pseudo de l'utilisateur
     * @param motDePasse : le mot de passe de l'utilisateur
     * @return -1 si le pseudo n'apparait pas dans la liste
     *          0 si le pseudo apparait, mais le mot de passe n'est pas bon
     *          1 si le couple est bon
     */
    public int verifCoupleLogin(String pseudo, String motDePasse) {
        Utilisateur resUti = utilisateurs.get(pseudo);
        if(resUti != null) {
            if(resUti.motDePasse.equals(motDePasse)) {
                return 1;
            } else {
                return 0;
            }
        }
        return -1;
    }

    /**
     * Ajoute un utilisateur dans la liste d'utilisateur, ainsi que dans le fichier de sauvegarde
     * @param nouvelUtilisateur : l'utilisateur à ajouter
     * @return true si ça s'est bien passé, false sinon
     */
    public boolean enregistrerUtilisateur(Utilisateur nouvelUtilisateur, String cheminVersFichier) throws IOException {
        if(this.utilisateurs.get(nouvelUtilisateur.pseudo) == null) {
            this.utilisateurs.put(nouvelUtilisateur.pseudo, nouvelUtilisateur);
            FileWriter fw = new FileWriter(cheminVersFichier, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(nouvelUtilisateur.toString());
            bw.newLine();
            bw.close();
            return true;
        } else {
            return false;
        }
    }


    /**
     * Lit le fichier de données des utilisateurs pour renseigner sa HahsMap d'utilisateur
     * @param cheminVersFichier : le chemin d'accès vers le fichier de données
     */
    public void parseUtilisateur(String cheminVersFichier) {
        try {
            File dataFile = new File(cheminVersFichier);
            if (dataFile.createNewFile()) {
                System.out.println("Le fichier " + dataFile.getName() + " à été créé");
            } else {
                System.out.println("Le fichier de bd sur le utilisateur est déjà présent.");
                try {
                    Scanner myReader = new Scanner(dataFile);
                    while (myReader.hasNextLine()) {
                        String row = myReader.nextLine();
                        String [] splittedRow = row.split(";");
                        Utilisateur nouvelUtilisateur;
                        String pseudo = splittedRow[0];
                        String motDePasse = splittedRow[1];
                        if(splittedRow.length > 2) {    // Cas utilsateur joueur
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
     * @param numeroLigne : le numéro de la ligne
     * @param data : chaine de caractère à écrire à la plce
     * @param chemin : chemin d'accès vers le fichier
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
}
