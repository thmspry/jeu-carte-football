package com.javafootball;

import com.javafootball.Model.Joueur.Carte;
import com.javafootball.Model.Joueur.CarteRare;
import com.javafootball.Model.Joueur.Joueur;
import com.javafootball.Model.Marche;
import com.javafootball.Model.Utilisateur.Utilisateur;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;
import com.javafootball.Model.Vente;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class JeuController implements Initializable {


    @FXML
    Label montantArgent;
    @FXML
    Label pseudo;
    @FXML
    TableView<Vente> tableauBoutique;
    @FXML
    TableColumn<Vente, String> prenomBoutique;
    @FXML
    TableColumn<Vente, String> nomBoutique;
    @FXML
    TableColumn<Vente, String> rareteBoutique;
    @FXML
    TableColumn<Vente, String> posteBoutique;
    @FXML
    TableColumn<Vente, String> numeroBoutique;
    @FXML
    TableColumn<Vente, String> prixBoutique;
    @FXML
    TableColumn<Vente, String> vendeurBoutique;

    @FXML
    FlowPane fondCarteBoutique;
    @FXML
    Label nomJoueurBoutiqueLbl;
    @FXML
    Label posteBoutiqueLbl;
    @FXML
    Label vendeurBoutiqueLbl;
    @FXML
    Label rareteBoutiqueLbl;
    @FXML
    Label prixVenteBoutique;

    @FXML
    ListView<Carte> listeCartePerso;
    @FXML
    FlowPane fondCartePerso;
    @FXML
    Label nomJoueurPersoLbl;
    @FXML
    Label postePersoLbl;
    @FXML
    Label raretePersoLbl;
    @FXML
    Spinner<Integer> prixVentePerso;


    Vente venteSelectionnee;
    Carte carteSelectionne;

    UtilisateurJoueur utilisateur;
    Marche marche;

    /**
     * Pour avoir une chaine de caratère comportant le montant avec les milliers séparés
     *
     * @param valeur : la valeur a formater
     * @return la valeur sous format string
     */
    private String separeMilliers(int valeur) {
        DecimalFormat millierFormat = new DecimalFormat();
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols();
        customSymbols.setGroupingSeparator(' ');
        millierFormat.setDecimalFormatSymbols(customSymbols);
        return millierFormat.format(valeur);
    }

    /**
     * Remplis les champs relatifs à l'utilisateur dans la fenêtre
     */
    private void setInfoUtilisateur() {
        montantArgent.setText(separeMilliers(this.utilisateur.argent));

        pseudo.setText(this.utilisateur.pseudo);

        listeCartePerso.getItems().addAll(utilisateur.listeCarte);

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
            this.tableauBoutique.getItems().add(v);
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
        listeCartePerso.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Carte>() {
            @Override
            public void changed(ObservableValue<? extends Carte> observableValue, Carte cartePrecedente, Carte carteCourante) {
                carteSelectionne = carteCourante;
                Joueur joueurCourant = carteCourante.joueur;
                Image imageFondCarte = new Image(carteCourante.lienFondCarte);

                BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(400, 500, false, false, false, false));
                Background bGround = new Background(bImg);
                nomJoueurPersoLbl.setText(joueurCourant.prenom + " " + joueurCourant.nom);
                if (carteCourante instanceof CarteRare) {
                    nomJoueurPersoLbl.setTextFill(jaune);
                    postePersoLbl.setTextFill(jaune);
                    raretePersoLbl.setTextFill(jaune);
                } else {
                    nomJoueurPersoLbl.setTextFill(noir);
                    postePersoLbl.setTextFill(noir);
                    raretePersoLbl.setTextFill(noir);
                }
                postePersoLbl.setText(joueurCourant.poste.getAbreviationSimplifie());
                raretePersoLbl.setText(carteCourante.rareteLabel);
                fondCartePerso.setBackground(bGround);
            }
        });

        // Association des colonnes aux bons champs
        prenomBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.joueur.prenom));
        nomBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.joueur.nom));
        rareteBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.rareteLabel));
        posteBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.joueur.poste.getAbreviation()));
        numeroBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().carteAVendre.numero)));
        prixBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(separeMilliers(cellData.getValue().prix)));
        vendeurBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().vendeur.getPseudoVendeur()));

        tableauBoutique.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vente>() {
            @Override
            public void changed(ObservableValue<? extends Vente> observableValue, Vente ventePrecedente, Vente venteCourante) {
                venteSelectionnee = venteCourante;
                Joueur joueurCourant = venteCourante.carteAVendre.joueur;
                Image imageFondCarte = new Image(venteCourante.carteAVendre.lienFondCarte);

                BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(400, 500, false, false, false, false));
                Background bGround = new Background(bImg);
                nomJoueurBoutiqueLbl.setText(joueurCourant.prenom + " " + joueurCourant.nom);
                if (venteCourante.carteAVendre instanceof CarteRare) {
                    nomJoueurBoutiqueLbl.setTextFill(jaune);
                    posteBoutiqueLbl.setTextFill(jaune);
                    rareteBoutiqueLbl.setTextFill(jaune);
                } else {
                    nomJoueurBoutiqueLbl.setTextFill(noir);
                    posteBoutiqueLbl.setTextFill(noir);
                    rareteBoutiqueLbl.setTextFill(noir);
                }
                posteBoutiqueLbl.setText(joueurCourant.poste.getAbreviationSimplifie());
                vendeurBoutiqueLbl.setText("Vendeur : " + venteCourante.getPseudoVendeur());
                rareteBoutiqueLbl.setText(venteCourante.carteAVendre.rareteLabel);
                fondCarteBoutique.setBackground(bGround);
                prixVenteBoutique.setText("Prix : " + separeMilliers(venteCourante.prix));
            }
        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2000000);
        valueFactory.setValue(0);
        prixVentePerso.setValueFactory(valueFactory);

    }

    public void acheter(ActionEvent actionEvent) {
        int montant = venteSelectionnee.prix;

        if (montant < utilisateur.argent) {
            Carte carteEnJeu = venteSelectionnee.carteAVendre;
            utilisateur.depenserArgent(montant);
            utilisateur.recevoirCarte(carteEnJeu);

            venteSelectionnee.vendeur.donnerCarte(carteEnJeu);
            venteSelectionnee.vendeur.recevoirArgent(montant);

            majFichierUtilisateurApresVente(venteSelectionnee.vendeur, utilisateur);

            marche.carteAVendre.remove(venteSelectionnee);

            // Mise à jour de la vue
            montantArgent.setText(separeMilliers(utilisateur.argent));
            tableauBoutique.getItems().remove(venteSelectionnee);
            listeCartePerso.getItems().add(carteEnJeu);
        }

    }

    public void vendre(ActionEvent actionEvent) {
        int montant = prixVentePerso.getValue();

        Vente nouvelleVente = this.marche.ajouterCarteAVendre(carteSelectionne, this.utilisateur, montant);
        this.utilisateur.listeCarte.remove(carteSelectionne);

        // Mise à jour de la vue
        listeCartePerso.getItems().remove(carteSelectionne);
        tableauBoutique.getItems().add(nouvelleVente);
    }

    public void remplacerLigne(int numeroLigne, String data, String chemin) throws IOException {
        Path path = Paths.get(chemin);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        lines.set(numeroLigne - 1, data);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }

    public void majFichierUtilisateurApresVente(Utilisateur vendeur, Utilisateur acheteur) {
        final String cheminVersFichier = "src/main/resources/com/javafootball/data/utilisateurs.csv";

        File dataFile = new File(cheminVersFichier);
        int ligneVendeur = 0;
        int ligneAcheteur = 0;
        int compteurLigne = 1;
        try {
            Scanner myReader = new Scanner(dataFile);
            while (myReader.hasNextLine()) {
                String row = myReader.nextLine();
                String [] splittedRow = row.split(";");

                String pseudo = splittedRow[0];
                if(pseudo.equals(vendeur.pseudo)) {
                    ligneVendeur = compteurLigne;
                }

                if(pseudo.equals(acheteur.pseudo)) {
                    ligneAcheteur = compteurLigne;
                }
                compteurLigne++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Une erreur est survenue dans la lecture du fichier de sauvegarde de données utilisateur.");
            e.printStackTrace();
        }

        try {
            remplacerLigne(ligneVendeur, vendeur.toString(), cheminVersFichier);
            remplacerLigne(ligneAcheteur, acheteur.toString(), cheminVersFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
