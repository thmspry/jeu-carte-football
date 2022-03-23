package com.javafootball;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AdminController {

    @FXML
    Label nomFichier;

    @FXML
    void importerFichier(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file == null) {
            nomFichier.setText("Aucun fichier sélectionné.");
        } else {
            nomFichier.setText(file.getName() + " sélectionné.");
        }


    }
}
