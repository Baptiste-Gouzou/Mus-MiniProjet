package com.montaury.mus.jeu.joueur;

import java.util.Iterator;
import java.util.List;
import java.util.*;


public class Opposants {
  private Equipe equipe1;
  private Equipe equipe2;

  private static LinkedList<Joueur> listJoueur = new LinkedList<Joueur();

  public Opposants(Equipe e1, Equipe e2) {
    this.equipe1 = e1;
    this.equipe2 = e2;

    listJoueur.add(equipe1.joueur1());
    listJoueur.add(equipe2.joueur1());
    listJoueur.add(equipe1.joueur2());
    listJoueur.add(equipe2.joueur2());
  }

  public void tourner() {
    Iterator<Joueur> i = itererDansLOrdre();

  }

  public Joueur joueurEsku() {
    return listJoueur.getFirst();
  }

  public Joueur joueurZaku() {
    return listJoueur.getLast();
  }

  public Iterator<Joueur> itererDansLOrdre() {
    return new IteratorInfini(this);
  }

  public List<Joueur> dansLOrdre() {
    return listJoueur;
  }

  private static class IteratorInfini implements Iterator<Joueur> {
    private final Opposants opposants;
    private Joueur suivant;

    public IteratorInfini(Opposants opposants) {
      this.opposants = opposants;
      suivant = opposants.joueurEsku();
    }

    @Override
    public boolean hasNext() {
      return true;
    }

    @Override
    public Joueur next() {
      suivant = listJoueur.getFirst();
      listJoueur.removeFirst();
      listJoueur.addLast(listJoueur.getFirst());
      return suivant;

      //Joueur next = suivant;
      //suivant = suivant == opposants.joueurEsku() ? opposants.joueurZaku() : opposants.joueurEsku();
      //return next;
    }
  }
}
