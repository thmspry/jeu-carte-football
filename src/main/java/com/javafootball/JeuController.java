package com.javafootball;

import com.javafootball.Model.EquipeJeu;
import com.javafootball.Model.Exception.ExceptionEquipeNonValide;
import com.javafootball.Model.Joueur.*;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class JeuController implements Initializable {

    Color jaune = Color.web("#F7EF00");
    Color noir = Color.web("#000000");


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
    TableColumn<Vente, String> equipeBoutique;
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
    ImageView photoJoueurBoutique;
    @FXML
    Label prixVenteBoutique;

    @FXML
    TableView<Carte> tableauPerso;
    @FXML
    TableColumn<Carte, String> prenomPerso;
    @FXML
    TableColumn<Carte, String> nomPerso;
    @FXML
    TableColumn<Carte, String> raretePerso;
    @FXML
    TableColumn<Carte, String> postePerso;
    @FXML
    TableColumn<Carte, String> equipePerso;
    @FXML
    TableColumn<Carte, String> numeroPerso;
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

    @FXML
    AnchorPane fondTerrain;
    @FXML
    Label jdc1;
    @FXML
    Label jdc2;
    @FXML
    Label jdc3;
    @FXML
    Label gk;
    @FXML
    ComboBox<Carte> jdc1Cb;
    @FXML
    ComboBox<Carte> jdc2Cb;
    @FXML
    ComboBox<Carte> jdc3Cb;
    @FXML
    ComboBox<Carte> gkCb;
    @FXML
    FlowPane jdc1Fond;
    @FXML
    FlowPane jdc2Fond;
    @FXML
    FlowPane jdc3Fond;
    @FXML
    FlowPane gkFond;
    @FXML
            Label succes;


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

        majOngletCarte();

        majOngletEquipe();

        for (Carte c : utilisateur.sonEquipe.compositionCarte) {
            Joueur j = c.joueur;
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

        tableauBoutique.getItems().addAll(this.marche.carteAVendre);
    }

    @FXML
    void seDeconnecter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("Connexion.fxml"));

        Parent root = fxmlLoader.load();

        ConnexionController connexionController = fxmlLoader.getController();
        connexionController.setMarche(marche);
        connexionController.majUtilisateur(utilisateur);

        Scene scene = new Scene(root, 1080, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Zimdim Football");
        stage.setScene(scene);
        stage.show();
        this.utilisateur = null;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        prenomPerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().joueur.prenom));
        nomPerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().joueur.nom));
        raretePerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().rareteLabel));
        postePerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().joueur.poste.getAbreviation()));
        equipePerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().joueur.equipe.nom));
        numeroPerso.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().numero)));

        tableauPerso.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Carte>() {
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
                nomJoueurPersoLbl.setText(joueurCourant.denomination());
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
        equipeBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.joueur.equipe.nom));
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
                nomJoueurBoutiqueLbl.setText(joueurCourant.denomination());
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
                photoJoueurBoutique.setImage(new Image(venteCourante.carteAVendre.joueur.lienPhoto));
            }
        });

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2000000);
        valueFactory.setValue(0);
        prixVentePerso.setValueFactory(valueFactory);

        gkCb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Carte>() {
            @Override
            public void changed(ObservableValue<? extends Carte> observableValue, Carte joueurGardien, Carte t1) {
                if(t1 instanceof CarteRare) {
                    gk.setTextFill(jaune);
                } else {
                    gk.setTextFill(noir);
                }
                gk.setText(t1.joueur.denomination());
                Image imageFondCarte = new Image(t1.lienFondCarte);
                BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(87, 122, false, false, false, false));
                Background bGround = new Background(bImg);
                gkFond.setBackground(bGround);

            }
        });

        jdc1Cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Carte>() {
            @Override
            public void changed(ObservableValue<? extends Carte> observableValue, Carte joueurGardien, Carte t1) {
                if(t1 instanceof CarteRare) {
                    jdc1.setTextFill(jaune);
                } else {
                    jdc1.setTextFill(noir);
                }
                jdc1.setText(t1.joueur.denomination());
                Image imageFondCarte = new Image(t1.lienFondCarte);
                BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(87, 122, false, false, false, false));
                Background bGround = new Background(bImg);
                jdc1Fond.setBackground(bGround);
            }
        });

        jdc2Cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Carte>() {
            @Override
            public void changed(ObservableValue<? extends Carte> observableValue, Carte joueurGardien, Carte t1) {
                if(t1 instanceof CarteRare) {
                    jdc2.setTextFill(jaune);
                } else {
                    jdc2.setTextFill(noir);
                }
                jdc2.setText(t1.joueur.denomination());
                Image imageFondCarte = new Image(t1.lienFondCarte);
                BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(87, 122, false, false, false, false));
                Background bGround = new Background(bImg);
                jdc2Fond.setBackground(bGround);
            }
        });

        jdc3Cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Carte>() {
            @Override
            public void changed(ObservableValue<? extends Carte> observableValue, Carte joueurGardien, Carte t1) {
                if(t1 instanceof CarteRare) {
                    jdc3.setTextFill(jaune);
                } else {
                    jdc3.setTextFill(noir);
                }
                jdc3.setText(t1.joueur.denomination());
                Image imageFondCarte = new Image(t1.lienFondCarte);
                BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(87, 122, false, false, false, false));
                Background bGround = new Background(bImg);
                jdc3Fond.setBackground(bGround);
            }
        });


        Image imageFondTerrain = new Image("https://i.imgur.com/K9Kcq12.png");

        BackgroundImage bImgTerrain = new BackgroundImage(imageFondTerrain,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(735, 346, false, false, false, false));
        Background bGroundTerrain = new Background(bImgTerrain);
        fondTerrain.setBackground(bGroundTerrain);

    }

    public void acheter(ActionEvent actionEvent) {
        int montant = venteSelectionnee.prix;

        if (montant < utilisateur.argent && (venteSelectionnee.vendeur != utilisateur)) {
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
            tableauPerso.getItems().add(carteEnJeu);

            majOngletEquipe();



        } else if (venteSelectionnee.vendeur == utilisateur) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Achat d'une carte");

            alert.setHeaderText(null);
            alert.setContentText("Vous ne pouvez pas acheter votre propre Carte");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Achat d'une carte");

            alert.setHeaderText(null);
            alert.setContentText("Vous ne possedez pas assez de Zimdim Coin pour acheter cette carte");
            alert.showAndWait();
        }

    }

    public void vendre(ActionEvent actionEvent) {
        int montant = prixVentePerso.getValue();

        if (montant != 0) {
            Vente nouvelleVente = this.marche.ajouterCarteAVendre(carteSelectionne, this.utilisateur, montant);
            this.utilisateur.listeCarte.remove(carteSelectionne);

            tableauPerso.getItems().remove(carteSelectionne);
            tableauBoutique.getItems().add(nouvelleVente);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vente d'une carte sans prix");

            alert.setHeaderText(null);
            alert.setContentText("Vous ne pouvez pas mettre en vente une carte au prix de 0 Zimdim Coin");
            alert.showAndWait();
        }
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
                String[] splittedRow = row.split(";");

                String pseudo = splittedRow[0];
                if (pseudo.equals(vendeur.pseudo)) {
                    ligneVendeur = compteurLigne;
                }

                if (pseudo.equals(acheteur.pseudo)) {
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

    @FXML
    public void soumettreEquipe(ActionEvent event) {
        List<Carte> propositionEquipe = new ArrayList<>();
        Carte gardienSelectionne = gkCb.getSelectionModel().getSelectedItem();
        Carte jdc1Selectionne = jdc1Cb.getSelectionModel().getSelectedItem();
        Carte jdc2Selectionne = jdc2Cb.getSelectionModel().getSelectedItem();
        Carte jdc3Selectionne = jdc3Cb.getSelectionModel().getSelectedItem();
        if (gardienSelectionne != null) {
            propositionEquipe.add(gardienSelectionne);
        }
        if (jdc1Selectionne != null) {
            propositionEquipe.add(jdc1Selectionne);
        }
        if (jdc2Selectionne != null) {
            propositionEquipe.add(jdc2Selectionne);
        }
        if (jdc3Selectionne != null) {
            propositionEquipe.add(jdc3Selectionne);
        }

        try {
            EquipeJeu.equipeValide(propositionEquipe);
            this.utilisateur.sonEquipe.setEquipe(propositionEquipe);
            succes.setText("L'équipe à bien été enregistrée !");

        } catch (ExceptionEquipeNonValide e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Achat d'une carte");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void majOngletEquipe() {
        gkCb.getItems().clear();
        jdc1Cb.getItems().clear();
        jdc2Cb.getItems().clear();
        jdc3Cb.getItems().clear();

        gkCb.getItems().addAll(this.utilisateur.listeCarte);
        jdc1Cb.getItems().addAll(this.utilisateur.listeCarte);
        jdc2Cb.getItems().addAll(this.utilisateur.listeCarte);
        jdc3Cb.getItems().addAll(this.utilisateur.listeCarte);
    }

    public void majOngletCarte() {
        tableauPerso.getItems().clear();
        tableauPerso.getItems().addAll(this.utilisateur.listeCarte);
    }

}
