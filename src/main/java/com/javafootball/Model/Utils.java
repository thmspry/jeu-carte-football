package com.javafootball.Model;

import com.javafootball.Model.Joueur.Joueur;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * Ensemble de méthodes statics utilisée plusieurs fois dans le projet, ne trouvant pas sa place dans une classe précise
 */
public class Utils {

    /**
     * Ouvre une fenêtre/pop-up d'erreur
     * @param titreFenetre : le titre de la fenêtre
     * @param message : le message à afficher
     */
    static public void ouvrirFenetreErreur(String titreFenetre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titreFenetre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Ouvre graphiquement un explorateur de fichier
     * @param event : l'événement pris en compte
     * @param titreFenetre : titre de la fenêtre d'explorateur
     * @return le fichier selectionné lors de la navigation dans l'explorateur
     */
    static public File ouvrirFenetreFichier(ActionEvent event, String titreFenetre) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fileChooser.setTitle(titreFenetre);
        return fileChooser.showOpenDialog(stage);
    }

    /**
     * Renvoie un joueur aléatoire parmis une liste de joueur
     *
     * @param joueurList : la liste de joueur
     * @return : le joueur indeterminé
     */
    public static Joueur getJoueurAleatoire(List<Joueur> joueurList) {
        Random rand = new Random();
        return joueurList.get(rand.nextInt(joueurList.size()));
    }

    /**
     * Remplace une ligne spécifique d'un fichier par une autre
     *
     * @param numeroLigne : le numéro de la ligne
     * @param data        : chaine de caractère à écrire à la plce
     * @param chemin      : chemin d'accès vers le fichier
     * @throws IOException : Exeption d'ouverture de fichier
     */
    public static void remplacerLigne(int numeroLigne, String data, String chemin) throws IOException {
        Path path = Paths.get(chemin);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.set(numeroLigne - 1, data);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }
}
