package com.javafootball;

import com.javafootball.Model.Joueur.CarteRare;
import com.javafootball.Model.Joueur.Joueur;
import com.javafootball.Model.Marche;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;
import com.javafootball.Model.Vente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class JeuController implements Initializable {


    @FXML
    Label montantArgent;
    @FXML
    Label pseudo;
    @FXML
    ListView<Vente> listeCarteBoutique;
    @FXML
    FlowPane fondCarte;
    @FXML
    Label nomJoueurLbl;
    @FXML
    Label posteLbl;
    @FXML
    Label vendeurLbl;
    @FXML
    Label rareteLbl;

    Vente venteSelectionnee;

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
        final String cheminVersFichierData = "src/main/resources/com/javafootball/data/" + utilisateur.pseudo + ".csv";
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
     *
     * @param uti : l'utilisateur courant à attribuer
     */
    void setUtilisateur(UtilisateurJoueur uti) {
        this.utilisateur = uti;
        setInfoUtilisateur();
    }

    void setMarche(Marche marche) {
        this.marche = marche;
        for (Vente v : marche.carteAVendre) {
            this.listeCarteBoutique.getItems().add(v);
        }
    }

    @FXML
    void seDeconnecter(ActionEvent event) throws IOException {
        this.utilisateur = null;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Color jaune = Color.web("#F7EF00");
        Color noir = Color.web("#000000");
        listeCarteBoutique.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vente>() {
            @Override
            public void changed(ObservableValue<? extends Vente> observableValue, Vente carte, Vente t1) {
                venteSelectionnee = listeCarteBoutique.getSelectionModel().getSelectedItem();
                Joueur joueurCourant = venteSelectionnee.carteAVendre.joueur;
                Image imageFondCarte = new Image(venteSelectionnee.carteAVendre.lienFondCarte);
                String pseudoVendeur = venteSelectionnee.getPseudoVendeur();

                BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(400, 500, false, false, false, false));
                Background bGround = new Background(bImg);
                nomJoueurLbl.setText(joueurCourant.prenom + " " + joueurCourant.nom);
                if(venteSelectionnee.carteAVendre instanceof CarteRare) {
                    nomJoueurLbl.setTextFill(jaune);
                    posteLbl.setTextFill(jaune);
                    rareteLbl.setTextFill(jaune);
                } else {
                    nomJoueurLbl.setTextFill(noir);
                    posteLbl.setTextFill(noir);
                    rareteLbl.setTextFill(noir);
                }
                posteLbl.setText(joueurCourant.poste.getAbreviationSimplifie());
                vendeurLbl.setText("Vendeur : " + pseudoVendeur);
                rareteLbl.setText(venteSelectionnee.carteAVendre.rareteLabel);
                fondCarte.setBackground(bGround);
            }
        });

    }
}
