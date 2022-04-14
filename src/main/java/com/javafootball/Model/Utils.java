package com.javafootball.Model;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Utils {

    static public void ouvrirFenetreErreur(String titreFenetre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titreFenetre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    static public File ouvrirFenetreFichier(ActionEvent event, String titreFenetre) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fileChooser.setTitle(titreFenetre);
        return fileChooser.showOpenDialog(stage);
    }
}
