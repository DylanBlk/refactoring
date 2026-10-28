package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Tests unitaires des stratégies de tarif (étape 5). */
class TarifTransportTest {

    private static final double CENTIME = 0.001;

    @Test
    void route_au_km_et_au_kg() {
        // 100 km x 1,20 + 50 kg x 0,10
        assertEquals(125.0, new TarifRoute().prixDeBase(100, 50, 0), CENTIME);
    }

    @Test
    void rail_au_km_et_au_kg() {
        // 500 km x 0,80 + 1000 kg x 0,07
        assertEquals(470.0, new TarifRail().prixDeBase(500, 1000, 0), CENTIME);
    }

    @Test
    void rail_minimum_150_euros() {
        assertEquals(150.0, new TarifRail().prixDeBase(10, 10, 0), CENTIME);
    }

    @Test
    void mer_au_km_et_au_kg() {
        // 2000 km x 0,50 + 1000 kg x 0,03
        assertEquals(1030.0, new TarifMer().prixDeBase(2000, 1000, 0), CENTIME);
    }

    @Test
    void mer_minimum_500_euros() {
        assertEquals(500.0, new TarifMer().prixDeBase(100, 100, 0), CENTIME);
    }

    @Test
    void air_au_poids_reel() {
        // 1000 km x 0,60 + 100 kg x 4,50 (poids volumétrique : 0,1 m3 x 167 = 16,7 kg)
        assertEquals(1050.0, new TarifAir().prixDeBase(1000, 100, 0.1), CENTIME);
    }

    @Test
    void air_au_poids_volumetrique() {
        // 1000 km x 0,60 + (1 m3 x 167 kg) x 4,50
        assertEquals(1351.5, new TarifAir().prixDeBase(1000, 10, 1), CENTIME);
    }
}
