package com.javafootball;

import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Marche;
import com.javafootball.Model.Utilisateur.Admin;
import com.javafootball.Model.Utilisateur.Utilisateur;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {


    @FXML
    Label nomFichier;
    @FXML
    RadioButton radioCommune;
    @FXML
    RadioButton radioPeuCommune;
    @FXML
    RadioButton radioRare;
    @FXML
    Label errorCreationCarte;
    @FXML
    Spinner<Integer> prixVente;

    @FXML
    TableView<Joueur> tableauCarte;
    @FXML
    TableColumn<Joueur, String> prenom;
    @FXML
    TableColumn<Joueur, String> nom;
    @FXML
    TableColumn<Joueur, String> poste;
    @FXML
    TableColumn<Joueur, String> equipe;

    @FXML
    ImageView imageCommune;
    @FXML
    ImageView imagePeuCommune;
    @FXML
    ImageView imageRare;

    Admin utilisateur;
    Marche marche;



    void setMarche(Marche marche) {
        this.marche = marche;
        this.tableauCarte.getItems().addAll(marche.joueursExistant);
    }

    void setUtilisateur(Admin u) {
        this.utilisateur = u;
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
        Joueur joueurSelectionne = tableauCarte.getSelectionModel().getSelectedItem();
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
            marche.ajouterCarteAVendre(nouvelleCarte, utilisateur, prixVente.getValue());

            /*try {
                FileWriter fw = new FileWriter(cheminVersFichierBoutique, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(nouvelleCarte.toString());
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2000000);
        valueFactory.setValue(0);
        prixVente.setValueFactory(valueFactory);

        prenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().prenom));
        nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nom));
        poste.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().poste.getAbreviation()));
        equipe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().equipe.nom));

        imageCommune.setImage(new Image("https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_1_2.png"));
        imagePeuCommune.setImage(new Image("https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_1_3.png"));
        imageRare.setImage(new Image("https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_4_0.png"));
    }
}
