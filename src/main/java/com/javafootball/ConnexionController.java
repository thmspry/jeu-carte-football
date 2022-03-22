package com.javafootball;

import com.javafootball.Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConnexionController implements Initializable {

    final private String cheminVersFichierData = "utilisateur.csv";
    List<Utilisateur> lesUtilisateur;

    @FXML
    TextField pseudoField;
    @FXML
    PasswordField motDePasseField;
    @FXML
    Label errorLbl;

    private Utilisateur currentUtilisateur;


    /**
     * Ajoute un utilisateur dans la liste d'utilisateur, ainsi que le fichier de sauvegarde
     * @param nouvelUtilisateur : l'utilisateur à ajouter
     * @return true si ça c'est bien passé, false sinon
     */
    private boolean enregistrerUtilisateur(Utilisateur nouvelUtilisateur) {
        this.lesUtilisateur.add(nouvelUtilisateur);
        try {
            FileWriter fw = new FileWriter(cheminVersFichierData, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(nouvelUtilisateur.toString());
            bw.newLine();
            bw.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void incription() {
        if(enregistrerUtilisateur(new Utilisateur(pseudoField.getText(), motDePasseField.getText()))) {
            errorLbl.setText("Inscription réussie");
        } else {
            errorLbl.setText("Une erreur dans la création du compte est survenue");
        }
    }

    /**
     * Vérifie le couple de pseudo mot de passe dans la liste de l'utilisateur
     * @param pseudo : le pseudo de l'utilisateur
     * @param motDePasse : le mot de passe de l'utilisateur
     * @return -1 si le pseudo n'apparait pas dans la liste
     *          0 si le pseudo apparait, mais le mot de passe n'est pas bon
     *          1 si le couple est bon
     */
    private int verifCoupleLogin(String pseudo, String motDePasse) {
        for (Utilisateur u: this.lesUtilisateur) {
            if(u.pseudo.equals(pseudo)) {
                if(u.motDePasse.equals(motDePasse)) {
                    currentUtilisateur = u;
                    return 1;
                }
                return 0;
            }
        }
        return -1;
    }

    @FXML
    protected void connexion(ActionEvent event) throws IOException {
        int resultatRecherche = verifCoupleLogin(pseudoField.getText(), motDePasseField.getText());
        switch (resultatRecherche) {
            case -1 -> errorLbl.setText("Ce compte n'existe pas");
            case 0 -> errorLbl.setText("Le mot de passe n'est pas le bon");
            case 1 -> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Jeu.fxml"));
                Parent root = fxmlLoader.load();
                if(currentUtilisateur != null) {
                    JeuController jeuController = fxmlLoader.getController();
                    jeuController.setUtilisateur(this.currentUtilisateur);
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
        this.lesUtilisateur = new ArrayList<>();
        try {
            File dataFile = new File(cheminVersFichierData);
            if (dataFile.createNewFile()) {
                System.out.println("Le fichier " + dataFile.getName() + " à été créé");
            } else {
                System.out.println("Le fichier de bd sur le utilisateur est déjà présent.");
                try {
                    Scanner myReader = new Scanner(dataFile);
                    while (myReader.hasNextLine()) {
                        String row = myReader.nextLine();
                        String [] splittedRow = row.split(";");
                        lesUtilisateur.add(new Utilisateur(splittedRow[0], splittedRow[1], Integer.parseInt(splittedRow[2])));
                    }
                    System.out.println("utilisateurs :" + lesUtilisateur.toString());
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue dans la création du fichier de sauvegarde des utilisateurs.");
            e.printStackTrace();
        }
    }
}
