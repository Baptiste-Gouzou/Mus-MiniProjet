package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Opposants;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.main;
import static com.montaury.mus.jeu.joueur.Fixtures.unJoueurAvec;
import static org.assertj.core.api.Assertions.assertThat;

class PairesTest {
  @Test
  void ne_doit_pas_se_derouler_si_personne_n_a_de_paires() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.AS_COUPE));
    Joueur joueur2 = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.AS_COUPE));
    Joueur joueur3 = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_PIECE));

    Equipe equipe1 =  new Equipe("equipe1", joueurEsku, joueur2);
    Equipe equipe2 =  new Equipe("equipe2", joueur3, joueurZaku);

    Opposants opposants = new Opposants(equipe1, equipe2);

    boolean peutSeDerouler = new Paires().peutSeDerouler(opposants);

    assertThat(peutSeDerouler).isFalse();
  }

  @Test
  void ne_doit_pas_se_derouler_si_un_des_joueurs_n_a_pas_de_paires() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.DEUX_COUPE));
    Joueur joueur2 = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.AS_COUPE));
    Joueur joueur3 = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_PIECE));

    Equipe equipe1 =  new Equipe("equipe1", joueurEsku, joueur2);
    Equipe equipe2 =  new Equipe("equipe2", joueur3, joueurZaku);

    Opposants opposants = new Opposants(equipe1,equipe2);

    boolean peutSeDerouler = new Paires().peutSeDerouler(opposants);

    assertThat(peutSeDerouler).isFalse();
  }

  @Test
  void devrait_se_derouler_si_les_deux_joueurs_ont_des_paires() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.CINQ_BATON, Carte.AS_COUPE));
    Joueur joueur2 = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.AS_COUPE));
    Joueur joueur3 = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.TROIS_BATON, Carte.SIX_PIECE));

    Equipe equipe1 =  new Equipe("equipe1", joueurEsku, joueur2);
    Equipe equipe2 =  new Equipe("equipe2", joueur3, joueurZaku);

    Opposants opposants = new Opposants(equipe1, equipe2);

    boolean peutSeDerouler = new Paires().peutSeDerouler(opposants);

    assertThat(peutSeDerouler).isTrue();
  }

  @Test
  void devrait_faire_gagner_le_joueur_ayant_la_meilleure_paire() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.CINQ_BATON, Carte.AS_COUPE));
    Joueur joueur2 = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.AS_COUPE));
    Joueur joueur3 = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_BATON, Carte.SIX_PIECE, Carte.TROIS_BATON, Carte.SIX_PIECE));

    Equipe equipe1 =  new Equipe("equipe1", joueurEsku, joueur2);
    Equipe equipe2 =  new Equipe("equipe2", joueur3, joueurZaku);

    Opposants opposants = new Opposants(equipe1, equipe2);

    Joueur vainqueur = new Paires().meilleurParmi(opposants);

    assertThat(vainqueur).isEqualTo(joueurZaku);
  }

  @Test
  void devrait_donner_un_bonus_de_1_si_le_joueur_a_une_paire_simple() {
    Joueur joueur = unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.SIX_EPEE, Carte.AS_PIECE));

    int bonus = new Paires().pointsBonus(joueur);

    assertThat(bonus).isEqualTo(1);
  }

  @Test
  void devrait_donner_un_bonus_de_2_si_le_joueur_a_des_meds() {
    Joueur joueur = unJoueurAvec(main(Carte.VALET_PIECE, Carte.SIX_PIECE, Carte.SIX_EPEE, Carte.SIX_COUPE));

    int bonus = new Paires().pointsBonus(joueur);

    assertThat(bonus).isEqualTo(2);
  }

  @Test
  void devrait_donner_un_bonus_de_3_si_le_joueur_a_des_doubles() {
    Joueur joueur = unJoueurAvec(main(Carte.VALET_PIECE, Carte.VALET_BATON, Carte.VALET_COUPE, Carte.VALET_EPEE));

    int bonus = new Paires().pointsBonus(joueur);

    assertThat(bonus).isEqualTo(3);
  }

}