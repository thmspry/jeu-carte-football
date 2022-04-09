package com.javafootball;

import com.javafootball.Model.Marche;
import com.javafootball.Model.Utilisateur.Admin;
import com.javafootball.Model.Utilisateur.Utilisateur;
import com.javafootball.Model.Utilisateur.UtilisateurJoueur;
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
import java.util.*;

public class ConnexionController implements Initializable {

    private final String cheminVersFichierData = "src/main/resources/com/javafootball/data/utilisateurs.csv";

    Map<String, Utilisateur> lesUtilisateur;

    @FXML
    TextField pseudoField;
    @FXML
    PasswordField motDePasseField;
    @FXML
    Label errorLbl;

    private Utilisateur currentUtilisateur;
    private Marche marche;


    /**
     * Ajoute un utilisateur dans la liste d'utilisateur, ainsi que le fichier de sauvegarde
     * @param nouvelUtilisateur : l'utilisateur à ajouter
     * @return true si ça s'est bien passé, false sinon
     */
    private boolean enregistrerUtilisateur(Utilisateur nouvelUtilisateur) throws IOException {
        if(this.lesUtilisateur.get(nouvelUtilisateur.pseudo) == null) {
            this.lesUtilisateur.put(nouvelUtilisateur.pseudo, nouvelUtilisateur);
            FileWriter fw = new FileWriter(cheminVersFichierData, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(nouvelUtilisateur.toString());
            bw.newLine();
            bw.close();
            return true;
        } else {
            return false;
        }
    }

    @FXML
    protected void inscription() {
        try {
            if(enregistrerUtilisateur(new UtilisateurJoueur(pseudoField.getText(), motDePasseField.getText()))) {
                errorLbl.setText("Inscription réussie");
            } else {
                errorLbl.setText("Le pseudo existe déjà, inscription impossible");
            }
        } catch (IOException e) {
            System.out.println("Un erreur est survenue lors de l'écriture dans le fichier de sauvegarde des utilisateurs.");
            e.printStackTrace();
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
        Utilisateur resUti = lesUtilisateur.get(pseudo);
        if(resUti != null) {
            if(resUti.motDePasse.equals(motDePasse)) {
                currentUtilisateur = resUti;
                return 1;
            } else {
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
                FXMLLoader fxmlLoader;
                String nomVue = currentUtilisateur.nomVue;
                fxmlLoader = new FXMLLoader(getClass().getResource(nomVue));

                Parent root = fxmlLoader.load();

                if(currentUtilisateur != null && !nomVue.equals("Admin.fxml")) {
                    JeuController jeuController = fxmlLoader.getController();
                    jeuController.setUtilisateur((UtilisateurJoueur) this.currentUtilisateur);
                    jeuController.setMarche(marche);
                } else {
                    AdminController adminController = fxmlLoader.getController();
                    adminController.setMarche(marche);
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
        this.lesUtilisateur = new HashMap<>();
        this.marche = new Marche();



        // Parse utilisateurs
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
                        Utilisateur nouvelUtilisateur;
                        String pseudo = splittedRow[0];
                        String motDePasse = splittedRow[1];
                        if(splittedRow.length > 2) {    // Cas utilsateur joueur
                            nouvelUtilisateur = new UtilisateurJoueur(pseudo, motDePasse, Integer.parseInt(splittedRow[2]), new ArrayList<>());
                        } else {    // Cas d'una admin
                            nouvelUtilisateur = new Admin(pseudo, motDePasse);
                        }
                        lesUtilisateur.put(pseudo, nouvelUtilisateur);

                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Une erreur est survenue dans la lecture du fichier de sauvegarde des utilisateurs.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue dans la création du fichier de sauvegarde des utilisateurs.");
            e.printStackTrace();
        }

        // Parse marché
        this.marche.initialisationDonnee("src/main/resources/com/javafootball/data/ext/2022_11_Nantes_ext.csv");

    }

    void setMarche(Marche marche) {
        this.marche = marche;
    }

}
