package com.javafootball;

import com.javafootball.Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
public class JeuController {

    @FXML
    Label montantArgent;
    @FXML
    Label pseudo;

    Utilisateur utilisateur;

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
    }

    /**
     * Attribut l'utilisateur courant
     * @param uti : l'utilisateur courant à attribuer
     */
    void setUtilisateur(Utilisateur uti) {
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


}
