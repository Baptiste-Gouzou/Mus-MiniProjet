package com.montaury.mus.jeu.joueur;

import com.montaury.mus.jeu.carte.Carte;
import java.util.List;

public class Joueur {
  public static Joueur humain(String nom, boolean e, boolean z) {
    return new Joueur(nom, new InterfaceJoueurHumain(), e, z);
  }

  public static Joueur ordinateur(boolean e, boolean z) {
    return new Joueur("Ordinateur", new InterfaceJoueurOrdinateur(), e, z);
  }

  private final String nom;
  public final InterfaceJoueur interfaceJoueur;
  private final Main main = Main.vide();
  private boolean esku;
  private boolean zaku;

  public Joueur(String nom, InterfaceJoueur interfaceJoueur, boolean e, boolean z) {
    this.nom = nom;
    this.interfaceJoueur = interfaceJoueur;
    esku = e;
    zaku = z;
  }

  public boolean isEsku(){ return esku; }

  public boolean isZaku(){ return zaku; }

  public devientEsku(boolean b){ esku = b; }

  public devientZaku(boolean b){ zaku = b; }


  public String nom() {
    return nom;
  }

  public Main main() {
    return main;
  }

  public void donnerCartes(List<Carte> cartes) {
    main.ajouter(cartes);
    interfaceJoueur.nouvelleMain(main);
  }
}
