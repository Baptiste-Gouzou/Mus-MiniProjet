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
    Joueur j = listJoueur.getFirst();
    listJoueur.removeFirst();
    listJoueur.addLast(j);
  }

  public Joueur joueurEsku() {
    return listJoueur.getFirst();
  }

  public Joueur joueurZaku() {
    return listJoueur.getLast();
  }

  public Equipe getEquipe1() {
    return equipe1;
  }

  public Equipe getEquipe2() {
    return equipe2;
  }

  public List<Joueur> dansLOrdre() {
    return listJoueur;
  }

  public List<Joueur> adversaire(Joueur joueur) {
    if (joueur.getEquipe() == equipe1)
      return List.of(equipe2.joueur1(), equipe2.joueur2());
    else
      return List.of(equipe1.joueur1(), equipe2.joueur2());
  }
}