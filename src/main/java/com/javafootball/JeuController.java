package com.javafootball;

import com.javafootball.Model.*;
import com.javafootball.Model.Exception.ExceptionEquipeNonValide;
import com.javafootball.Model.Exception.ExceptionTransaction;
import com.javafootball.Model.Joueur.*;
import com.javafootball.Model.Marche.Offre;
import com.javafootball.Model.Match.EquipeJeu;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
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
    TableView<Offre> tableauBoutique;
    @FXML
    Label numeroCarteBoutique;
    @FXML
    TableColumn<Offre, String> prenomBoutique;
    @FXML
    TableColumn<Offre, String> nomBoutique;
    @FXML
    TableColumn<Offre, String> rareteBoutique;
    @FXML
    TableColumn<Offre, String> posteBoutique;
    @FXML
    TableColumn<Offre, String> numeroBoutique;
    @FXML
    TableColumn<Offre, String> equipeBoutique;
    @FXML
    TableColumn<Offre, String> prixBoutique;
    @FXML
    TableColumn<Offre, String> vendeurBoutique;
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
    Label numeroCartePerso;
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
    ImageView photoJoueurPerso;

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

    @FXML
    Label matchsSemaineDerniere;
    @FXML
    Label matchsSemaineProchaine;
    @FXML
    Label premier;
    @FXML
    Label deuxieme;
    @FXML
    Label troisieme;


    Offre offreSelectionnee;
    Carte carteSelectionne;

    SystemeDonnee sd;
    UtilisateurJoueur utilisateurCourant;

    // Setteurs pour transmettre les informations du jeu entre controllers
    void setUtilisateurCourant(UtilisateurJoueur utilisateurCourant) {
        this.utilisateurCourant = utilisateurCourant;
        majInfoUtilisateur();

        majOngletCarte();

        majOngletEquipe();
    }

    void setSystemeDonnee(SystemeDonnee sd) {
        this.sd = sd;
        majOngletBoutique();
        majOngletMatch();
    }

    // M??thodes permettant de mettre ?? jour la vue apr??s modification des donn??es du syst??me
    private void majInfoUtilisateur() {
        montantArgent.setText(separeMilliers(this.utilisateurCourant.argent));
        pseudo.setText(this.utilisateurCourant.pseudo);
    }

    private void majOngletEquipe() {
        gkCb.getItems().clear();
        jdc1Cb.getItems().clear();
        jdc2Cb.getItems().clear();
        jdc3Cb.getItems().clear();

        gkCb.getItems().addAll(this.utilisateurCourant.listeCarte);
        jdc1Cb.getItems().addAll(this.utilisateurCourant.listeCarte);
        jdc2Cb.getItems().addAll(this.utilisateurCourant.listeCarte);
        jdc3Cb.getItems().addAll(this.utilisateurCourant.listeCarte);


        if (this.utilisateurCourant.aSoumisEquipe()) {
            List<Carte> equipeListe = this.utilisateurCourant.equipe.compositionCarte;

            Carte goal = equipeListe.get(0);
            Carte joueurDeCamp1 = equipeListe.get(1);
            Carte joueurDeCamp2 = equipeListe.get(2);
            Carte joueurDeCamp3 = equipeListe.get(3);

            gk.setText(goal.denominationSimplifiee());
            jdc1.setText(joueurDeCamp1.denominationSimplifiee());
            jdc2.setText(joueurDeCamp2.denominationSimplifiee());
            jdc3.setText(joueurDeCamp3.denominationSimplifiee());

            gkCb.getSelectionModel().select(goal);
            jdc1Cb.getSelectionModel().select(joueurDeCamp1);
            jdc2Cb.getSelectionModel().select(joueurDeCamp2);
            jdc3Cb.getSelectionModel().select(joueurDeCamp3);
        }


    }

    private void majOngletCarte() {
        tableauPerso.getItems().clear();
        tableauPerso.getItems().addAll(this.utilisateurCourant.listeCarte);
    }

    private void majOngletBoutique() {
        tableauBoutique.getItems().clear();
        tableauBoutique.getItems().addAll(this.sd.marche.carteAVendre);
    }

    private void majOngletMatch() {
        matchsSemaineDerniere.setText("");
        matchsSemaineProchaine.setText("");
        try {
            List<String> matchsSemaineDerniereListe = this.sd.matchHebdoSemaineDerniere.matchSemaine();
            for (String match : matchsSemaineDerniereListe) {
                matchsSemaineDerniere.setText(matchsSemaineDerniere.getText() + "\n" + match);
            }
        } catch (FileNotFoundException e) {
            matchsSemaineDerniere.setText("Aucun match recens??");
        }

        try {
            List<String> matchsSemaineProchaineListe = this.sd.matchHebdoSemaineProchaine.matchSemaine();
            for (String match : matchsSemaineProchaineListe) {
                matchsSemaineProchaine.setText(matchsSemaineProchaine.getText() + "\n" + match);
            }
        } catch (FileNotFoundException e) {
            matchsSemaineProchaine.setText("Aucun match recens??");
        }


        UtilisateurJoueur utilisateurPremier = this.sd.matchHebdoSemaineDerniere.getPremierGagnant();
        UtilisateurJoueur utilisateurDeuxieme = this.sd.matchHebdoSemaineDerniere.getDeuxiemeGagnant();
        UtilisateurJoueur utilisateurTroisieme = this.sd.matchHebdoSemaineDerniere.getTroisiemeGagnant();
        if(utilisateurPremier != null) { premier.setText("1.  " + utilisateurPremier.pseudo + " : " + utilisateurPremier.scoreDeLaSemaine + " points"); }
        if(utilisateurDeuxieme != null) { deuxieme.setText("2.  " + utilisateurDeuxieme.pseudo + " : " + utilisateurDeuxieme.scoreDeLaSemaine + " points"); }
        if(utilisateurTroisieme != null) { troisieme.setText("3.  " + utilisateurTroisieme.pseudo + " : " + utilisateurTroisieme.scoreDeLaSemaine + " points"); }


    }


    /**
     * Pour avoir une chaine de caract??re comportant le montant avec les milliers s??par??s
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


    @FXML
    void seDeconnecter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("Connexion.fxml"));

        Parent root = fxmlLoader.load();

        ConnexionController connexionController = fxmlLoader.getController();
        connexionController.setSystemeDonne(this.sd);

        Scene scene = new Scene(root, 1080, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        this.utilisateurCourant = null;
    }


    @FXML
    public void acheter(ActionEvent actionEvent) {
        try {
            this.utilisateurCourant.faireTransaction(offreSelectionnee);
            this.sd.majFichierUtilisateurApresVente(offreSelectionnee.vendeur, this.utilisateurCourant);

            this.sd.marche.carteAVendre.remove(offreSelectionnee);

            // Mise ?? jour de la vue
            majOngletCarte();
            majOngletBoutique();
            majInfoUtilisateur();
            majOngletEquipe();
        } catch (ExceptionTransaction e) {
            Utils.ouvrirFenetreErreur("Achat d'une carte", e.getMessage());
        }
    }

    @FXML
    public void vendre(ActionEvent actionEvent) {
        int montant = prixVentePerso.getValue();

        this.sd.marche.ajouterCarteAVendre(carteSelectionne, this.utilisateurCourant, montant);
        this.utilisateurCourant.listeCarte.remove(carteSelectionne);

        majOngletCarte();
        majOngletBoutique();
        majOngletEquipe();
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
            this.utilisateurCourant.equipe.setEquipe(propositionEquipe);
            this.sd.matchHebdoSemaineProchaine.ajoutJoueur(this.utilisateurCourant);
            succes.setText("L'??quipe ?? bien ??t?? enregistr??e !");

        } catch (ExceptionEquipeNonValide e) {
            Utils.ouvrirFenetreErreur("Constitution de l'??quipe", e.getMessage());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialisation des colonnes du tableau de cartes personnelles
        prenomPerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().joueur.prenom));
        nomPerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().joueur.nom));
        raretePerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().rareteLabel));
        postePerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().joueur.poste.getAbreviation()));
        equipePerso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().joueur.equipe.nom));
        numeroPerso.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().numero)));

        // Listener appliquant un comportement ?? chaque changement de selection de ligne dans le tableau
        tableauPerso.getSelectionModel().selectedItemProperty().addListener((observableValue, cartePrecedente, carteCourante) -> {
            carteSelectionne = carteCourante;
            if (carteCourante != null) {
                Joueur joueurCourant = carteCourante.joueur;
                Image imageFondCarte = new Image(carteCourante.lienFondCarte);

                BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
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
                postePersoLbl.setText(joueurCourant.denominationPoste());
                raretePersoLbl.setText(carteCourante.rareteLabel);
                fondCartePerso.setBackground(bGround);
                photoJoueurPerso.setImage(new Image(carteCourante.joueur.lienPhoto));
                numeroCartePerso.setText(String.valueOf(carteCourante.numero));
            }
        });

        // Initialisation des colonnes du tableau de vente dans la boutique
        prenomBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.joueur.prenom));
        nomBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.joueur.nom));
        rareteBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.rareteLabel));
        posteBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.joueur.poste.getAbreviation()));
        equipeBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().carteAVendre.joueur.equipe.nom));
        numeroBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().carteAVendre.numero)));
        prixBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(separeMilliers(cellData.getValue().prix)));
        vendeurBoutique.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().vendeur.getPseudoVendeur()));

        // Listener appliquant un comportement ?? chaque changement de selection de ligne dans le tableau
        tableauBoutique.getSelectionModel().selectedItemProperty().addListener((observableValue, ventePrecedente, venteCourante) -> {
            offreSelectionnee = venteCourante;
            if (venteCourante != null) {
                Carte carteCourant = venteCourante.carteAVendre;
                Joueur joueurCourant = carteCourant.joueur;

                Image imageFondCarte = new Image(venteCourante.carteAVendre.lienFondCarte);
                BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
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
                posteBoutiqueLbl.setText(joueurCourant.denominationPoste());
                vendeurBoutiqueLbl.setText("Vendeur : " + venteCourante.getPseudoVendeur());
                rareteBoutiqueLbl.setText(venteCourante.carteAVendre.rareteLabel);
                fondCarteBoutique.setBackground(bGround);
                prixVenteBoutique.setText("Prix : " + separeMilliers(venteCourante.prix));
                photoJoueurBoutique.setImage(new Image(venteCourante.carteAVendre.joueur.lienPhoto));
                numeroCarteBoutique.setText(String.valueOf(carteCourant.numero));
            }
        });

        //  Initialisation du spinner (champs pour le prix)
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2000000);
        valueFactory.setValue(0);
        prixVentePerso.setValueFactory(valueFactory);

        // Listener appliquant un comportement ?? chaque changement de selection de carte de la ComboBox

        gkCb.getSelectionModel().selectedItemProperty().addListener((observableValue, joueurGardien, t1) -> {
            gk.setText(t1.joueur.denomination());
            Image imageFondCarte = new Image(t1.lienFondCarte);
            BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(145, 205, false, false, false, false));
            Background bGround = new Background(bImg);
            gkFond.setBackground(bGround);

        });

        jdc1Cb.getSelectionModel().selectedItemProperty().addListener((observableValue, joueurGardien, t1) -> {
            jdc1.setText(t1.joueur.denomination());
            Image imageFondCarte = new Image(t1.lienFondCarte);
            BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(145, 205, false, false, false, false));
            Background bGround = new Background(bImg);
            jdc1Fond.setBackground(bGround);
        });

        jdc2Cb.getSelectionModel().selectedItemProperty().addListener((observableValue, joueurGardien, t1) -> {
            jdc2.setText(t1.joueur.denomination());
            Image imageFondCarte = new Image(t1.lienFondCarte);
            BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(145, 205, false, false, false, false));
            Background bGround = new Background(bImg);
            jdc2Fond.setBackground(bGround);
        });

        jdc3Cb.getSelectionModel().selectedItemProperty().addListener((observableValue, joueurGardien, t1) -> {
            jdc3.setText(t1.joueur.denomination());
            Image imageFondCarte = new Image(t1.lienFondCarte);
            BackgroundImage bImg = new BackgroundImage(imageFondCarte,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(145, 205, false, false, false, false));
            Background bGround = new Background(bImg);
            jdc3Fond.setBackground(bGround);
        });


        Image imageFondTerrain = new Image("https://i.imgur.com/K9Kcq12.png");

        BackgroundImage bImgTerrain = new BackgroundImage(imageFondTerrain,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(735, 346, false, false, false, false));
        Background bGroundTerrain = new Background(bImgTerrain);
        fondTerrain.setBackground(bGroundTerrain);

    }

}
