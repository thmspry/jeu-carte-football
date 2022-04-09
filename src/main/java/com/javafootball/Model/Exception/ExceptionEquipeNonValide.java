package com.javafootball.Model.Exception;

import com.javafootball.Model.Joueur.Joueur;

public class ExceptionEquipeNonValide extends Exception{
    public ExceptionEquipeNonValide(){
        super();
    }
    public ExceptionEquipeNonValide(String s){super(s);}

    public ExceptionEquipeNonValide(Joueur j , String s){
        super(s);
        System.out.println(j.nom+" "+j.prenom+" est deja dans l'équipe");
    }


}
