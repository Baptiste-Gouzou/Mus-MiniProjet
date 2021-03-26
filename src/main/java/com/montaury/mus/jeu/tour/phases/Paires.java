package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.joueur.Opposants;

import java.util.Iterator;
import java.util.List;

public class Paires extends Phase {
  public Paires() {
    super("Paires");
  }

  @Override
  protected boolean peutParticiper(Joueur joueur) {
    return joueur.main().aDesPaires();
  }

  @Override
  protected Joueur meilleurParmi(Opposants opposants) {
    List<Joueur> joueursAvecPaires = participantsParmi(opposants);
    Joueur meilleur = joueursAvecPaires.get(0);

    for (Joueur j : joueursAvecPaires) {
      if (j.main().getPaires().estMeilleureOuEgaleA(meilleur.main().getPaires()))
        meilleur = j;
    }

    return meilleur;
  }

    /*
    com.montaury.mus.jeu.carte.paires.Paires pairesJoueurEsku = opposants.joueurEsku().main().getPaires();
    com.montaury.mus.jeu.carte.paires.Paires pairesJoueurZaku = opposants.joueurZaku().main().getPaires();
    return pairesJoueurEsku.estMeilleureOuEgaleA(pairesJoueurZaku) ? opposants.joueurEsku() : opposants.joueurZaku();
     */


  @Override
  public int pointsBonus(Joueur vainqueur) {
    return vainqueur.main().getPaires().pointsBonus();
  }
}
