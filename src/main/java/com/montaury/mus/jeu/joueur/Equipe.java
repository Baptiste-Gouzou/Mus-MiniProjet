package com.montaury.mus.jeu.joueur;

public class Equipe {
    private Joueur joueur1;
    private Joueur joueur2;
    private String nom;



    public Equipe(String n,Joueur j1, Joueur j2){
        nom = n;
        joueur1 = j1;
        joueur1.setEquipe(this);
        joueur2 = j2;
        joueur2.setEquipe(this);
    }

    public Joueur joueur1(){ return joueur1; }

    public Joueur joueur2(){ return joueur2; }

    public String nom(){ return nom; }

}
