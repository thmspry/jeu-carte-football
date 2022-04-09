package com.javafootball;

import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Marche;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AdminController {


    @FXML
    Label nomFichier;
    @FXML
    ListView<Joueur> listeJoueur;
    @FXML
    RadioButton radioCommune;
    @FXML
    RadioButton radioPeuCommune;
    @FXML
    RadioButton radioRare;
    @FXML
    Label errorCreationCarte;

    Marche marche;

    void setMarche(Marche marche) {
        this.marche = marche;
        for (Joueur j : marche.joueursExistant) {
            this.listeJoueur.getItems().add(j);
        }
    }

    @FXML
    void seDeconnecter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("Connexion.fxml"));

        Parent root = fxmlLoader.load();

        ConnexionController connexionController = fxmlLoader.getController();
        connexionController.setMarche(marche);

        Scene scene = new Scene(root, 1080, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Zimdim Football");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void importerFichier(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null) {
            nomFichier.setText("Aucun fichier sélectionné.");
        } else {
            nomFichier.setText(file.getName() + " sélectionné.");
        }


    }



    @FXML
    void mettreEnVente(ActionEvent event) {
        Joueur joueurSelectionne = listeJoueur.getSelectionModel().getSelectedItem();
        Carte nouvelleCarte = null;

        final String cheminVersFichierBoutique = "src/main/resources/com/javafootball/data/boutique.csv";

        // TODO : Factoriser les if d'une certaine manière
        if (radioCommune.isSelected()) {
            try {
                nouvelleCarte = CarteCommune.creerCarte(joueurSelectionne);
                errorCreationCarte.setText("Carte créée");
            } catch (ExceptionRareteDepasse e) {
                errorCreationCarte.setText("La limite de " + CarteCommune.maxExemplaire + " cartes de ce joueur à été atteinte.");
            }
        }
        if (radioPeuCommune.isSelected()) {
            try {
                nouvelleCarte = CartePeuCommune.creerCarte(joueurSelectionne);
                errorCreationCarte.setText("Carte créée");
            } catch (ExceptionRareteDepasse e) {
                errorCreationCarte.setText("La limite de " + CartePeuCommune.maxExemplaire + " cartes de ce joueur à été atteinte.");
            }
        }
        if (radioRare.isSelected()) {
            try {
                nouvelleCarte = CarteRare.creerCarte(joueurSelectionne);
                errorCreationCarte.setText("Carte créée");
            } catch (ExceptionRareteDepasse e) {
                errorCreationCarte.setText("La limite de " + CarteRare.maxExemplaire + " cartes de ce joueur à été atteinte.");
            }
        }

        if (nouvelleCarte != null) {
            marche.ajouterCarteAVendre(nouvelleCarte);

            try {
                FileWriter fw = new FileWriter(cheminVersFichierBoutique, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(nouvelleCarte.toString());
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

}
