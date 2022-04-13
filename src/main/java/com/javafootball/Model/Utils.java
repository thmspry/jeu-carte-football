package com.javafootball.Model;

import javafx.scene.control.Alert;

public class Utils {

    static public void ouvrirFenetreErreur(String titreFenetre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titreFenetre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
