package com.montaury.mus.jeu.tour.phases.dialogue;

import com.montaury.mus.jeu.joueur.AffichageEvenementsDeJeu;
import com.montaury.mus.jeu.joueur.InterfaceJoueur;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.PASO;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.KANTA;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.TIRA;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.IDOKI;
import static com.montaury.mus.jeu.tour.phases.dialogue.TypeChoix.IMIDO;

public class Dialogue {
  private final List<ChoixJoueur> choix = new ArrayList<>();

  public final DialogueTermine derouler(AffichageEvenementsDeJeu affichage, Opposants opposants) {
    Iterator<Joueur> IterateurJoueurParlant = opposants.dansLOrdre().iterator();
    List<TypeChoix> choixPossibles = TypeChoix.INITIAUX;
    while(IterateurJoueurParlant.hasNext()){
      Joueur joueurParlant = IterateurJoueurParlant.next();
      Choix choixJoueur = joueurParlant.interfaceJoueur.faireChoixParmi(choixPossibles);
      affichage.choixFait(joueurParlant, choixJoueur);
      this.ajouter(choixJoueur, joueurParlant);
      if(choixJoueur.est(IMIDO)){
        IterateurJoueurParlant = opposants.adversaire(joueurParlant).iterator();
        choixPossibles = choixJoueur.prochainsChoixPossibles();
      }
      else if(choixJoueur.est(TIRA)){
        opposants.dansLOrdre().remove(joueurParlant);
      }
      else if(choixJoueur.est(KANTA) || choixJoueur.est(IDOKI)){
        IterateurJoueurParlant = Collections.emptyIterator();
        choixPossibles = Collections.emptyList();
      }

    }

    /*
    do {
      Joueur parlant = iteratorJoueur.next();
      Choix choixJoueur = parlant.interfaceJoueur.faireChoixParmi(prochainsChoixPossibles());
      iteratorJoueur.remove(parlant);
      affichage.choixFait(parlant, choixJoueur);
      ajouter(choixJoueur, parlant);
    }
    while (enCours());

     */
    return new DialogueTermine(choix);
  }

  void ajouter(Choix choix, Joueur joueur) {
    this.choix.add(new ChoixJoueur(choix, joueur));
  }

  boolean enCours() {
    return choix.size() <= 1 || (!dernierChoix().metFinAuDialogue() && !dernierChoix().est(PASO));
  }

  private Choix dernierChoix() {
    return choix.get(choix.size() - 1).choix;
  }

  private List<TypeChoix> prochainsChoixPossibles() {
    return choix.isEmpty() ? TypeChoix.INITIAUX : dernierChoix().prochainsChoixPossibles();
  }

  public static class ChoixJoueur {
    public final Choix choix;
    public final Joueur joueur;

    public ChoixJoueur(Choix choix, Joueur joueur) {
      this.choix = choix;
      this.joueur = joueur;
    }

    public int mise() {
      return choix.mise();
    }
  }
}
