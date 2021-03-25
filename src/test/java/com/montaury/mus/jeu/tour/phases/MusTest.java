package com.montaury.mus.jeu.tour.phases;

import com.montaury.mus.jeu.carte.Carte;
import com.montaury.mus.jeu.carte.Defausse;
import com.montaury.mus.jeu.joueur.*;
import com.montaury.mus.jeu.joueur.Equipe;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.montaury.mus.jeu.carte.Fixtures.paquetEntierCroissant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MusTest {

  @BeforeEach
  void setUp() {
    defausse = new Defausse();
    mus = new Mus(paquetEntierCroissant(), defausse, new AffichageConsoleEvenementsDeJeu(joueurEsku));
    interfaceJoueurEsku = mock(InterfaceJoueur.class);
    interfaceJoueurZaku = mock(InterfaceJoueur.class);
    joueurEsku = new Joueur("J1", interfaceJoueurEsku);
    joueur2 = new Joueur("J2", interfaceJoueur2);
    joueur3 = new Joueur("J3", interfaceJoueur3);
    joueurZaku = new Joueur("J4", interfaceJoueurZaku);

    equipe1 = new Equipe("e1",joueurEsku, joueur2);
    equipe2 = new Equipe("e2",joueur3, joueurZaku);

    opposants = new Opposants(equipe1, equipe2);
  }

  @Test
  void devrait_distribuer_quatre_cartes_a_chaque_joueur() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_COUPE, Carte.AS_EPEE, Carte.AS_PIECE);
    assertThat(joueur2.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_COUPE, Carte.AS_EPEE, Carte.AS_PIECE);
    assertThat(joueur3.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_COUPE, Carte.DEUX_EPEE, Carte.DEUX_PIECE);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_COUPE, Carte.DEUX_EPEE, Carte.DEUX_PIECE);
  }

  @Test
  void devrait_se_terminer_si_le_joueur_esku_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    verify(interfaceJoueurZaku, times(0)).veutAllerMus();
  }

  @Test
  void devrait_se_terminer_si_le_joueur_zaku_veut_sortir() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(false);

    mus.jouer(opposants);

    verify(interfaceJoueurEsku, times(0)).cartesAJeter();
  }

  @Test
  void devrait_demander_les_cartes_a_jeter_aux_joueurs_s_ils_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueur2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueur3.veutAllerMus()).thenReturn(true);

    mus.jouer(opposants);

    verify(interfaceJoueurEsku, times(1)).cartesAJeter();
    verify(interfaceJoueurZaku, times(1)).cartesAJeter();
    verify(interfaceJoueur2, times(1)).cartesAJeter();
    verify(interfaceJoueur3, times(1)).cartesAJeter();
  }

  @Test
  void devrait_defausser_les_cartes_a_jeter_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.cartesAJeter()).thenReturn(List.of(Carte.DEUX_COUPE));
    when(interfaceJoueur2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueur2.cartesAJeter()).thenReturn(List.of(Carte.TROIS_COUPE));
    when(interfaceJoueur3.veutAllerMus()).thenReturn(true);
    when(interfaceJoueur3.cartesAJeter()).thenReturn(List.of(Carte.QUATRE_COUPE));

    mus.jouer(opposants);

    assertThat(defausse.reprendreCartes()).containsExactly(Carte.AS_COUPE, Carte.DEUX_COUPE, Carte.DEUX_COUPE, Carte.QUATRE_COUPE);
  }

  @Test
  void devrait_distribuer_des_cartes_pour_remplacer_les_cartes_jetees_si_les_joueurs_vont_mus() {
    when(interfaceJoueurEsku.veutAllerMus()).thenReturn(true, false);
    when(interfaceJoueurEsku.cartesAJeter()).thenReturn(List.of(Carte.AS_COUPE));
    when(interfaceJoueurZaku.veutAllerMus()).thenReturn(true);
    when(interfaceJoueurZaku.cartesAJeter()).thenReturn(List.of(Carte.DEUX_COUPE));
    when(interfaceJoueur2.veutAllerMus()).thenReturn(true);
    when(interfaceJoueur2.cartesAJeter()).thenReturn(List.of(Carte.TROIS_COUPE));
    when(interfaceJoueur3.veutAllerMus()).thenReturn(true);
    when(interfaceJoueur3.cartesAJeter()).thenReturn(List.of(Carte.QUATRE_COUPE));

    mus.jouer(opposants);

    assertThat(joueurEsku.main().cartes()).containsExactly(Carte.AS_BATON, Carte.AS_EPEE, Carte.AS_PIECE, Carte.CINQ_BATON);
    assertThat(joueurZaku.main().cartes()).containsExactly(Carte.DEUX_BATON, Carte.DEUX_EPEE, Carte.DEUX_PIECE, Carte.CINQ_COUPE);
    assertThat(joueur2.main().cartes()).containsExactly(Carte.TROIS_BATON, Carte.TROIS_EPEE, Carte.TROIS_PIECE, Carte.CINQ_EPEE);
    assertThat(joueur3.main().cartes()).containsExactly(Carte.QUATRE_BATON, Carte.QUATRE_EPEE, Carte.QUATRE_PIECE, Carte.CINQ_PIECE);
  }

  private Mus mus;
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
  private Defausse defausse;
}