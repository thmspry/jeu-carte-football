package com.javafootball;

import com.javafootball.Model.Exception.ExceptionFichier;
import com.javafootball.Model.Exception.ExceptionRareteDepasse;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.SystemeDonnee;
import com.javafootball.Model.Utilisateur.Admin;
import com.javafootball.Model.Utils;
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
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {


    @FXML
    RadioButton radioCommune;
    @FXML
    RadioButton radioPeuCommune;
    @FXML
    RadioButton radioRare;
    @FXML
    Label resultCreationCarte;
    @FXML
    Spinner<Integer> prixVente;
    @FXML
    Spinner<Integer> nombreCarte;
    @FXML
    Button miseEnVenteBtn;

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

    @FXML
    Label messageSemaine;

    @FXML
    Label nomFichierMatch;
    @FXML
    Label nomFichierResultat;

    private SystemeDonnee sd;
    private Admin adminCourant;

    // Setteurs pour transmettre les informations du jeu entre controllers
    public void setUtilisateurCourant(Admin adminCourant) {
        this.adminCourant = adminCourant;
    }

    public void setSystemeDonnee(SystemeDonnee sd) {
        this.sd = sd;
        this.tableauCarte.getItems().addAll(this.sd.marche.joueursExistant);
        int nombreFichierMatch = this.sd.matchHebdoSemaineProchaine.nombreFichierMatch();
        nomFichierMatch.setText(nombreFichierMatch + " fichier" + conjugueNom(nombreFichierMatch) + " ajouté" + conjugueNom(nombreFichierMatch) + ".");

        int nombreFichierResultat = this.sd.matchHebdoSemaineProchaine.nombreFichierResultat();
        nomFichierResultat.setText(nombreFichierResultat + " fichier" + conjugueNom(nombreFichierResultat) + " ajouté" + conjugueNom(nombreFichierResultat) + ".");
    }


    @FXML
    void seDeconnecter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("Connexion.fxml"));

        Parent root = fxmlLoader.load();

        ConnexionController connexionController = fxmlLoader.getController();
        connexionController.setSystemeDonne(this.sd);

        Scene scene = new Scene(root, 1080, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Zimdim Football");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void importerFichierMatch(ActionEvent event) {
        File fileMatch = Utils.ouvrirFenetreFichier(event, "Ajouter un fichier de match");
        if (fileMatch == null) {
            nomFichierMatch.setText("Aucun fichier sélectionné.");
        } else {
            nomFichierMatch.setText(fileMatch.getName() + " sélectionné.");
            this.sd.matchHebdoSemaineProchaine.ajouterFichierMatch(fileMatch);

        }
    }

    public void importerFichierResultat(ActionEvent event) {
        File fileResultat = Utils.ouvrirFenetreFichier(event, "Ajouter un fichier de résultats");
        if (fileResultat == null) {
            nomFichierResultat.setText("Aucun fichier sélectionné.");
        } else {
            nomFichierResultat.setText(fileResultat.getName() + " sélectionné.");
            this.sd.matchHebdoSemaineProchaine.ajouterFichierResultat(fileResultat);
        }
    }


    @FXML
    public void passerSemaineSuivante(ActionEvent event) {
        try {
            this.sd.passerSemaineSuivante();
            messageSemaine.setText("Une semaine est passée. Recompenses hebdo envoyées.");
        } catch (ExceptionRareteDepasse e) {
            Utils.ouvrirFenetreErreur("Cartes épuisées", e.getMessage());
        } catch (FileNotFoundException e) {
            Utils.ouvrirFenetreErreur("Ouverture fichier", "Le fichier de résultat hebdomadaire n'a pas pu être lu :" + e.getMessage());
        } catch (ExceptionFichier e) {
            Utils.ouvrirFenetreErreur("Fichiers semaine prochaine", e.getMessage());
        }
    }

    private String conjugueNom(int nombre) {
        if (nombre > 1) {
            return "s";
        } else {
            return "";
        }
    }

    private String conjugueEtre(int nombre) {
        if(nombre > 1) {
            return "ont";
        } else {
            return "a";
        }
    }

    @FXML
    void mettreEnVente(ActionEvent event) {
        Joueur joueurSelectionne = tableauCarte.getSelectionModel().getSelectedItem();
        if(joueurSelectionne != null) {
            Carte nouvelleCarte;
            int nombreCarteVoulue = nombreCarte.getValue();
            int nombreCarteCreee = 0;

            resultCreationCarte.setText("");

            try {

                // TODO : Factoriser d'une certaine manière

                if (radioCommune.isSelected()) {
                    for (nombreCarteCreee = 0; nombreCarteCreee < nombreCarteVoulue; nombreCarteCreee++) {
                        nouvelleCarte = CarteCommune.creerCarte(joueurSelectionne, this.sd.marche, true);
                        this.sd.marche.ajouterCarteAVendre(nouvelleCarte, this.adminCourant, prixVente.getValue());
                    }
                }
                if (radioPeuCommune.isSelected()) {
                    for (nombreCarteCreee = 0; nombreCarteCreee < nombreCarteVoulue; nombreCarteCreee++) {
                        nouvelleCarte = CartePeuCommune.creerCarte(joueurSelectionne, this.sd.marche, true);
                        this.sd.marche.ajouterCarteAVendre(nouvelleCarte, this.adminCourant, prixVente.getValue());
                    }
                }
                if (radioRare.isSelected()) {
                    for (nombreCarteCreee = 0; nombreCarteCreee < nombreCarteVoulue; nombreCarteCreee++) {
                        nouvelleCarte = CarteRare.creerCarte(joueurSelectionne, this.sd.marche, true);
                        this.sd.marche.ajouterCarteAVendre(nouvelleCarte, this.adminCourant, prixVente.getValue());
                    }
                }

            } catch (ExceptionRareteDepasse e) {
                Utils.ouvrirFenetreErreur("Creation de carte", e.getMessage());
            }

            resultCreationCarte.setText(nombreCarteCreee + " carte" + conjugueNom(nombreCarteCreee) + " de " +
                    joueurSelectionne.denomination() + " " + conjugueEtre(nombreCarteCreee) + " été créée" + conjugueNom(nombreCarteCreee) + ".");
        } else {
            resultCreationCarte.setText("Veuillez selection un joueur dans le tableau.");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //  Initialisation du spinner (champs pour le prix)
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2000000);
        valueFactory.setValue(0);
        prixVente.setValueFactory(valueFactory);

        //  Initialisation du spinner (champs pour le nombre de cartes)
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, CarteCommune.maxExemplaire);
        valueFactory2.setValue(1);
        nombreCarte.setValueFactory(valueFactory2);
        nombreCarte.getEditor().textProperty().addListener((observableValue, s, nouvelleValeur) -> miseEnVenteBtn.setText("Mettre en vente " + nouvelleValeur + " carte" + conjugueNom(Integer.parseInt(nouvelleValeur))));

        miseEnVenteBtn.setText("Mettre en vente " + nombreCarte.getValue().toString() + " carte");


        //  Initialisation des colonnes du tableau des joueurs
        prenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().prenom));
        nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nom));
        poste.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().poste.getAbreviation()));
        equipe.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().equipe.nom));

        //  Affectation des bonnes image pour les aperçus des raretés
        imageCommune.setImage(new Image("https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_1_2.png"));
        imagePeuCommune.setImage(new Image("https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_1_3.png"));
        imageRare.setImage(new Image("https://cdn-0.fifarosters.com/assets/cards/fifa22/cards_bg_e_1_4_0.png"));
    }
}
