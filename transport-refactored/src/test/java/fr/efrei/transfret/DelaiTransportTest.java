package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Tests unitaires des stratégies de délai (bonus de l'étape 5). */
class DelaiTransportTest {

    @Test
    void route() {
        assertEquals(1, new DelaiRoute().jours(465, false));   // 6,6 h -> 1 jour
        assertEquals(2, new DelaiRoute().jours(775, false));   // 11 h -> 2 jours
        assertEquals(1, new DelaiRoute().jours(775, true));    // 18 h par jour si urgent
    }

    @Test
    void rail() {
        assertEquals(2, new DelaiRail().jours(465, false));
        assertEquals(1, new DelaiRail().jours(465, true));
    }

    @Test
    void mer() {
        assertEquals(11, new DelaiMer().jours(5850, false));
        assertEquals(4, new DelaiMer().jours(760, false));
    }

    @Test
    void air() {
        assertEquals(1, new DelaiAir().jours(465, false));
        assertEquals(2, new DelaiAir().jours(5840, false));
        assertEquals(1, new DelaiAir().jours(5840, true));
    }

    @Test
    void jamais_moins_d_un_jour() {
        assertEquals(1, new EstimateurDelai().calculerDelai(10, ModeTransport.RAIL, true));
    }
}
