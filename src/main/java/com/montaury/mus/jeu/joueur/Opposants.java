package com.montaury.mus.jeu.joueur;

import java.util.Iterator;
import java.util.List;
import java.util.*;


public class Opposants {
  private final Equipe equipe1;
  private final Equipe equipe2;

  private static final LinkedList<Joueur> listJoueur = new LinkedList<Joueur>();

  public Opposants(Equipe e1, Equipe e2) {
    this.equipe1 = e1;
    this.equipe2 = e2;

    listJoueur.add(equipe1.joueur1());
    listJoueur.add(equipe2.joueur1());
    listJoueur.add(equipe1.joueur2());
    listJoueur.add(equipe2.joueur2());
  }

  public void tourner() {
    Joueur j = listJoueur.removeFirst();
    listJoueur.addLast(j);
  }

  public Joueur joueurEsku() {
    return listJoueur.getFirst();
  }

  public Joueur joueurZaku() {
    return listJoueur.getLast();
  }

  public Equipe getEquipe1(){ return equipe1; }

  public Equipe getEquipe2(){ return equipe2; }

  public Iterator<Joueur> itererDansLOrdre() {
    return new IteratorInfini(this);
  }

  public List<Joueur> dansLOrdre() {
    return listJoueur;
  }

  public List<Joueur> adversaire(Joueur joueur) {
    if (joueur.getEquipe() == equipe1) {
      return List.of(equipe2.joueur1(),equipe2.joueur2());
    }
    else{
      return List.of(equipe1.joueur1(),equipe2.joueur2());
    }
  }


    private static class IteratorInfini implements Iterator<Joueur> {
    private final Opposants opposants;
    private final Joueur suivant;

    public IteratorInfini(Opposants opposants) {
      this.opposants = opposants;
      suivant = opposants.joueurEsku();
    }

    @Override
    public boolean hasNext() {
      return suivant != listJoueur.getLast();
    }

    @Override
    public Joueur next() {
      return listJoueur.get(listJoueur.indexOf(suivant)+1);
    }
  }
}
