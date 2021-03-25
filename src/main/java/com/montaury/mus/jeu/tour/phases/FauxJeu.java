package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;

import static com.montaury.mus.jeu.tour.phases.Jeu.aLeJeu;

public class FauxJeu extends Phase {
  public FauxJeu() {
    super("Faux Jeu");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return !aLeJeu(joueur);
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    Joueur joueur1 = opposants.getEquipe1().joueur1();
    Joueur joueur2 = opposants.getEquipe1().joueur2();
    Joueur joueur3 = opposants.getEquipe2().joueur1();
    Joueur joueur4 = opposants.getEquipe2().joueur2();

    Joueur joueurInter = plusDePoints(joueur1, joueur2);
    Joueur joueurInter2 = plusDePoints(joueur3, joueur4);

    return plusDePoints(joueurInter, joueurInter2);
  }

  public Joueur plusDePoints(Joueur j1, Joueur j2){
    return j1.main().pointsPourJeu() >= j2.main().pointsPourJeu() ? j1 : j2;
  }

  @Override
  public int pointsBonus(Joueur vainqueur) {
    return 1;
  }
}
