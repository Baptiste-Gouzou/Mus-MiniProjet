package com.montaury.mus.jeu.tour;

import com.montaury.mus.jeu.Manche;
import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.joueur.*;
import com.montaury.mus.jeu.tour.phases.dialogue.Gehiago;
import com.montaury.mus.jeu.tour.phases.dialogue.Hordago;
import com.montaury.mus.jeu.tour.phases.dialogue.Idoki;
import com.montaury.mus.jeu.tour.phases.dialogue.Imido;
import com.montaury.mus.jeu.tour.phases.dialogue.Kanta;
import com.montaury.mus.jeu.tour.phases.dialogue.Paso;
import com.montaury.mus.jeu.tour.phases.dialogue.Tira;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.carte.Fixtures.paquetAvec;
import static com.montaury.mus.jeu.carte.Fixtures.paquetEntierCroissant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TourTest {

  @BeforeEach
  void setUp() {
    interfaceJoueurEsku = mock(InterfaceJoueur.class);
    interfaceJoueur2 = mock(InterfaceJoueur.class);
    interfaceJoueur3 = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    joueur2 = new Joueur("J2", interfaceJoueur2);
    joueur3 = new Joueur("J3", interfaceJoueur3);
    joueurZaku = new Joueur("J4", interfaceJoueurZaku);
    equipe1 = new Equipe("equipe1",joueurEsku,joueur2);
    equipe2 = new Equipe("equipe2",joueur3,joueurZaku);
    opposants = new Opposants(equipe1, equipe2);
    score = new Manche.Score(opposants);
    evenementsDeJeu = mock(AffichageEvenementsDeJeu.class);
    tour = new Tour(evenementsDeJeu, paquetEntierCroissant(), new Defausse());
  }

  @Test
  void devrait_donner_tous_les_points_au_joueur_esku_si_le_joueur_zaku_fait_tira() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceJoueur2.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueur3.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Tira());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 8);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 0);
  }

  @Test
  void devrait_repartir_les_points_si_tout_est_paso() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueur2.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueur3.faireChoixParmi(any())).thenReturn(new Paso());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Paso());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 1);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 5);
  }

  @Test
  void devrait_faire_gagner_le_joueur_zaku_si_hordago_au_grand() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Hordago());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Kanta());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).contains(equipe2);
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 0);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 40);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_idoki() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Idoki());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 2);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 10);
  }

  @Test
  void devrait_partager_les_points_si_tout_est_gehiago_puis_idoki() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido(), new Idoki(), new Imido(), new Idoki(), new Imido(), new Idoki(), new Imido(), new Idoki());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Gehiago(2));

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 4);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 16);
  }

  @Test
  void devrait_privilegier_le_joueur_esku_si_les_mains_sont_identiques() {
    when(interfaceJoueurEsku.faireChoixParmi(any())).thenReturn(new Imido());
    when(interfaceJoueurZaku.faireChoixParmi(any())).thenReturn(new Idoki());

    Tour tour = new Tour(evenementsDeJeu, paquetAvec(Carte.AS_BATON, Carte.DEUX_BATON, Carte.TROIS_BATON, Carte.QUATRE_BATON, Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.TROIS_COUPE, Carte.QUATRE_COUPE), new Defausse());

    tour.jouer(opposants, score);

    assertThat(score.vainqueur()).isEmpty();
    assertThat(score.scoreParEquipe()).containsEntry(equipe1, 7);
    assertThat(score.scoreParEquipe()).containsEntry(equipe2, 0);
  }

  private InterfaceJoueur interfaceJoueurEsku;
  private InterfaceJoueur interfaceJoueur2;
  private InterfaceJoueur interfaceJoueur3;
  private InterfaceJoueur interfaceJoueurZaku;
  private Joueur joueurEsku;
  private Joueur joueur2;
  private Joueur joueur3;
  private Joueur joueurZaku;
  private Equipe equipe1;
  private Equipe equipe2;
  private Opposants opposants;
  private Manche.Score score;
  private AffichageEvenementsDeJeu evenementsDeJeu;
  private Tour tour;
}
