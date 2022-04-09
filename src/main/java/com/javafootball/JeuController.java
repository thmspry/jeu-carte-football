package com.javafootball;

import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Joueur.Joueur;
import com.javafootball.Model.Marche;
import com.javafootball.Model.Utilisateur.Admin;
import com.javafootball.Model.Utilisateur.Utilisateur;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class JeuController  implements Initializable{

    @FXML
    Label montantArgent;
    @FXML
    Label pseudo;

    UtilisateurJoueur utilisateur;
    Marche marche;

    /**
     * Remplis les champs relatifs à l'utilisateur dans la fenêtre
     */
    private void setInfoUtilisateur() {
        // Pour avoir une chaine de caratère comportant le montant d'argent avec les milliers séparés
        DecimalFormat millierFormat = new DecimalFormat();
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        customSymbols.setGroupingSeparator(' ');
        millierFormat.setDecimalFormatSymbols(customSymbols);
        montantArgent.setText(millierFormat.format(this.utilisateur.argent));

        pseudo.setText(this.utilisateur.pseudo);

        // Parsing de ses cartes
        final String cheminVersFichierData = "src/main/resources/com/javafootball/data/utilisateurs/" + utilisateur.pseudo + ".csv";
        try {
            File dataFile = new File(cheminVersFichierData);
            if (dataFile.createNewFile()) {
                System.out.println("Le fichier " + dataFile.getName() + " à été créé");
            } else {
                System.out.println("Le fichier de donnée de l'utilisateur est déjà présent.");
                try {
                    Scanner myReader = new Scanner(dataFile);
                    while (myReader.hasNextLine()) {

                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Une erreur est survenue dans la lecture du fichier de sauvegarde de données utilisateur.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue dans la création du fichier de sauvegarde de données des utilisateur.");
            e.printStackTrace();
        }
    }

    /**
     * Attribut l'utilisateur courant
     * @param uti : l'utilisateur courant à attribuer
     */
    void setUtilisateur(UtilisateurJoueur uti) {
        this.utilisateur = uti;
        setInfoUtilisateur();
    }

    @FXML
    void seDeconnecter(ActionEvent event) throws IOException {
        this.utilisateur = null;
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("Connexion.fxml"));

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1080, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Zimdim Football");
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        marche = new Marche();
        // Parsing de joueur du jeu
        final String cheminVersFichierData = "src/main/resources/com/javafootball/data/utilisateurs/2022_11_Nantes_ext.csv";
        try {
            File dataFile = new File(cheminVersFichierData);
            if (dataFile.createNewFile()) {
                System.out.println("Le fichier " + dataFile.getName() + " à été créé");
            } else {
                System.out.println("Le fichier de donnée de l'utilisateur est déjà présent.");
                try {
                    Scanner myReader = new Scanner(dataFile);
                    while (myReader.hasNextLine()) {

                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Une erreur est survenue dans la lecture du fichier de sauvegarde de données utilisateur.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue dans la création du fichier de sauvegarde de données des utilisateur.");
            e.printStackTrace();
        }
    }
}
