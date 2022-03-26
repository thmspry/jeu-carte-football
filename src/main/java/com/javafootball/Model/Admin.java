package com.javafootball.Model;

public class Admin extends Utilisateur{

    public Admin(String pseudo, String motDePasse) {
        super(pseudo, motDePasse);
        this.nomVue = "Admin.fxml";
    }

    @Override
    public String toString() {
        return pseudo + ';' + motDePasse;
    }
}
