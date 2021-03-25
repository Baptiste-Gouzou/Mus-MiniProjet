package com.montaury.mus;

import com.montaury.mus.jeu.Partie;
import com.montaury.mus.jeu.joueur.AffichageConsoleEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import com.montaury.mus.jeu.joueur.Equipe;
import java.util.Scanner;

public class Mus {
  public static void main(String[] args) {
    System.out.print("Entrez votre nom: ");
    String nomJoueur = new Scanner(System.in).next();
    System.out.print("Entrez votre nom d'équipe: ");
    String nomEquipe = new Scanner(System.in).next();
    Joueur humain = Joueur.humain(nomJoueur);
    Joueur ordi1 = Joueur.ordinateur(true);
    Joueur ordi2 = Joueur.ordinateur(false);
    Joueur ordi3 = Joueur.ordinateur(false);

    Equipe equipeHumain = new Equipe(nomEquipe,humain, ordi1);
    Equipe equipeOrdi = new Equipe("equipe2",ordi2,ordi3);

    Partie partie = new Partie(new AffichageConsoleEvenementsDeJeu(humain));
    Partie.Resultat resultat = partie.jouer(new Opposants(equipeHumain, equipeOrdi));

    System.out.println("Le vainqueur de la partie est " + resultat.vainqueur().nom());
  }
}
