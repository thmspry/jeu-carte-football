package com.javafootball;

import com.javafootball.Model.SystemeDonnee;
import com.javafootball.Model.Utilisateur.Admin;
import com.javafootball.Model.Utilisateur.Utilisateur;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class ConnexionController implements Initializable {

    @FXML
    TextField pseudoField;
    @FXML
    PasswordField motDePasseField;
    @FXML
    Label errorLbl;

    private SystemeDonnee sd;

    final String cheminVersFichierUtilisateurs = "src/main/resources/com/javafootball/data/utilisateurs.csv";
    final String cheminVersFichierJoueurs = "src/main/resources/com/javafootball/data/ext/Ligue1.csv";

    // Setteur pour transmettre les informations du jeu entre controllers
    void setSystemeDonne(SystemeDonnee sd) {
        this.sd = sd;
    }


    @FXML
    protected void inscription() {
        try {
            if(sd.enregistrerUtilisateur(new UtilisateurJoueur(pseudoField.getText(), motDePasseField.getText()), cheminVersFichierUtilisateurs)) {
                errorLbl.setText("Inscription réussie");
            } else {
                errorLbl.setText("Le pseudo existe déjà, inscription impossible");
            }
        } catch (IOException e) {
            System.out.println("Un erreur est survenue lors de l'écriture dans le fichier de sauvegarde des utilisateurs.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void connexion(ActionEvent event) throws IOException {
        int resultatRecherche = this.sd.verifCoupleLogin(pseudoField.getText(), motDePasseField.getText());
        switch (resultatRecherche) {
            case -1 -> errorLbl.setText("Ce compte n'existe pas");
            case 0 -> errorLbl.setText("Le mot de passe n'est pas le bon");
            case 1 -> {
                FXMLLoader fxmlLoader;
                Utilisateur utilisateurConnexion = sd.utilisateurs.get(pseudoField.getText());
                String nomVue = utilisateurConnexion.nomVue;
                fxmlLoader = new FXMLLoader(getClass().getResource(nomVue));

                Parent root = fxmlLoader.load();

                if(utilisateurConnexion instanceof UtilisateurJoueur) {
                    JeuController jeuController = fxmlLoader.getController();
                    jeuController.setUtilisateurCourant((UtilisateurJoueur) utilisateurConnexion);
                    jeuController.setSystemeDonnee(this.sd);
                } else {
                    AdminController adminController = fxmlLoader.getController();
                    adminController.setUtilisateurCourant((Admin) utilisateurConnexion);
                    adminController.setSystemeDonnee(this.sd);
                }

                Scene scene = new Scene(root, 1080, 720);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Zimdim Football");
                stage.setScene(scene);
                stage.show();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(sd == null) {
            sd = new SystemeDonnee();
            sd.parseUtilisateur(cheminVersFichierUtilisateurs);
            sd.marche.parseJoueurEquipe(cheminVersFichierJoueurs);
        }

    }

}
