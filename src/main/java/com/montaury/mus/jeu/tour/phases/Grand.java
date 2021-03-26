package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.ValeurCarte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Main;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.List;

import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_GRANDE;
import static com.montaury.mus.jeu.carte.ValeurCarte.Comparaison.PLUS_PETITE;

public class Grand extends Phase {
  public Grand() {
    super("Grand");
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    Joueur joueur1 = opposants.getEquipe1().joueur1();
    Joueur joueur2 = opposants.getEquipe1().joueur2();
    Joueur joueur3 = opposants.getEquipe2().joueur1();
    Joueur joueur4 = opposants.getEquipe2().joueur2();

    Joueur joueurInter = plusGrandeCarte(joueur1, joueur2);
    Joueur joueurInter2 = plusGrandeCarte(joueur3, joueur4);

    return plusGrandeCarte(joueurInter, joueurInter2);
  }

  public Joueur plusGrandeCarte(Joueur j1, Joueur j2){
    List<Carte> carteJ1 = j1.main().cartesDuPlusGrandAuPlusPetit();
    List<Carte> carteJ2 = j2.main().cartesDuPlusGrandAuPlusPetit();

    if(carteJ1.get(0).valeur() >= carteJ2.get(0).valeur())
      return j1;
    else
      return j2;

  }
}
