package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Tests du supplément « hors gabarit » (étape 8). */
class SupplementHorsGabaritTest {

    private static final double CENTIME = 0.001;
    private static final CalculPrix MILLE_EUROS = commande -> 1000;

    private static CommandeTransport commandeAvec(double longueur, double largeur, double hauteur) {
        Colis colis = new Colis("colis", 10, longueur, largeur, hauteur, TypeMarchandise.STANDARD, 100);
        return new CommandeTransport(List.of(colis), 100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER);
    }

    @Test
    void plus_10_pourcent_si_une_dimension_depasse_2_metres() {
        assertEquals(1100.0, new SupplementHorsGabarit(MILLE_EUROS).calculer(commandeAvec(240, 120, 150)), CENTIME);
        assertEquals(1100.0, new SupplementHorsGabarit(MILLE_EUROS).calculer(commandeAvec(100, 201, 100)), CENTIME);
        assertEquals(1100.0, new SupplementHorsGabarit(MILLE_EUROS).calculer(commandeAvec(100, 100, 250)), CENTIME);
    }

    @Test
    void rien_si_toutes_les_dimensions_font_2_metres_ou_moins() {
        assertEquals(1000.0, new SupplementHorsGabarit(MILLE_EUROS).calculer(commandeAvec(200, 200, 200)), CENTIME);
    }

    @Test
    void le_calculateur_n_applique_le_supplement_que_s_il_est_active() {
        // fûts de solvant de 240 cm, Paris -> Marseille, route, entreprise, urgent (cas des tests de contrat)
        List<Colis> futs = List.of(new Colis("Fûts de solvant", 5000, 240, 120, 150, TypeMarchandise.DANGEREUX, 20000));
        assertEquals(2509.65, CalculateurPrix.tarifsHistoriques()
                .calculerPrixHT(futs, 775, ModeTransport.ROUTE, true, false, TypeClient.ENTREPRISE), CENTIME);
        assertEquals(2760.62, CalculateurPrix.avecHorsGabarit()
                .calculerPrixHT(futs, 775, ModeTransport.ROUTE, true, false, TypeClient.ENTREPRISE), CENTIME);
    }
}
