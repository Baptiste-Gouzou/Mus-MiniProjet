package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.joueur.Joueur;
import com.montaury.mus.jeu.joueur.Equipe;
import com.montaury.mus.jeu.joueur.Opposants;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.joueur.Fixtures.main;
import static com.montaury.mus.jeu.joueur.Fixtures.unJoueurAvec;
import static org.assertj.core.api.Assertions.assertThat;

class PetitTest {
  @Test
  void devrait_faire_gagner_le_joueur_esku_s_il_a_la_plus_petite_carte() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.AS_COUPE));
    Joueur joueur2 = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.AS_COUPE));
    Joueur joueur3 = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_PIECE));

    Equipe equipe1 =  new Equipe("equipe1", joueurEsku, joueur2);
    Equipe equipe2 =  new Equipe("equipe2", joueur3, joueurZaku);

    Joueur vainqueur = new Petit().meilleurParmi(new Opposants(equipe1, equipe2));

    assertThat(vainqueur).isEqualTo(joueurEsku);
  }

  @Test
  void devrait_faire_gagner_le_joueur_zaku_s_il_a_la_plus_petite_carte() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.SIX_COUPE));
    Joueur joueur2 = unJoueurAvec(main(Carte.DEUX_PIECE, Carte.CINQ_PIECE, Carte.SEPT_BATON, Carte.SIX_COUPE));
    Joueur joueur3 = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.SIX_BATON));
    Joueur joueurZaku = unJoueurAvec(main(Carte.DEUX_BATON, Carte.TROIS_PIECE, Carte.CAVALIER_BATON, Carte.AS_PIECE));

    Equipe equipe1 =  new Equipe("equipe1", joueurEsku, joueur2);
    Equipe equipe2 =  new Equipe("equipe2", joueur3, joueurZaku);

    Joueur vainqueur = new Petit().meilleurParmi(new Opposants(equipe1, equipe2));

    assertThat(vainqueur).isEqualTo(joueurZaku);
  }

  @Test
  void devrait_faire_gagner_le_joueur_qui_a_la_seconde_plus_petite_carte_si_la_premiere_est_egale() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_BATON, Carte.CINQ_PIECE, Carte.QUATRE_BATON, Carte.SIX_COUPE));
    Joueur joueur2 = unJoueurAvec(main(Carte.AS_BATON, Carte.CINQ_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE));
    Joueur joueur3 = unJoueurAvec(main(Carte.ROI_BATON, Carte.VALET_PIECE, Carte.CAVALIER_BATON, Carte.ROI_PIECE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.SEPT_BATON, Carte.VALET_PIECE, Carte.TROIS_BATON, Carte.AS_PIECE));

    Equipe equipe1 =  new Equipe("equipe1", joueurEsku, joueur2);
    Equipe equipe2 =  new Equipe("equipe2", joueur3, joueurZaku);

    Joueur vainqueur = new Petit().meilleurParmi(new Opposants(equipe1, equipe2));

    assertThat(vainqueur).isEqualTo(joueurZaku);
  }

  @Test
  void devrait_faire_gagner_le_joueur_esku_si_les_deux_mains_sont_egales() {
    Joueur joueurEsku = unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE));
    Joueur joueur2 = unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE));
    Joueur joueur3 = unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE));
    Joueur joueurZaku = unJoueurAvec(main(Carte.AS_BATON, Carte.QUATRE_PIECE, Carte.VALET_BATON, Carte.SIX_COUPE));

    Equipe equipe1 =  new Equipe("equipe1", joueurEsku, joueur2);
    Equipe equipe2 =  new Equipe("equipe2", joueur3, joueurZaku);

    Joueur vainqueur = new Petit().meilleurParmi(new Opposants(equipe1, equipe2));

    assertThat(vainqueur).isEqualTo(joueurEsku);
  }
}