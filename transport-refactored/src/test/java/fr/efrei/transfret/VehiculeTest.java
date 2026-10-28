package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests unitaires des véhicules et de l'affectation (étape 7). */
class VehiculeTest {

    private static List<Colis> colis(double poids, TypeMarchandise type) {
        return List.of(new Colis("colis", poids, 50, 40, 30, type, 100));
    }

    @Test
    void un_camion_ne_prend_que_la_route() {
        Camion camion = new Camion("AB-123-CD", 19000, false);
        assertTrue(camion.peutPrendreEnCharge(ModeTransport.ROUTE, colis(100, TypeMarchandise.STANDARD)));
        assertFalse(camion.peutPrendreEnCharge(ModeTransport.RAIL, colis(100, TypeMarchandise.STANDARD)));
    }

    @Test
    void un_camion_non_certifie_refuse_le_dangereux() {
        assertFalse(new Camion("A", 19000, false).peutPrendreEnCharge(ModeTransport.ROUTE, colis(100, TypeMarchandise.DANGEREUX)));
        assertTrue(new Camion("B", 19000, true).peutPrendreEnCharge(ModeTransport.ROUTE, colis(100, TypeMarchandise.DANGEREUX)));
    }

    @Test
    void le_camion_frigorifique_ne_prend_que_du_refrigere() {
        CamionFrigorifique frigo = new CamionFrigorifique("IJ-789-KL", 8000);
        assertTrue(frigo.peutPrendreEnCharge(ModeTransport.ROUTE, colis(100, TypeMarchandise.REFRIGERE)));
        assertFalse(frigo.peutPrendreEnCharge(ModeTransport.ROUTE, colis(100, TypeMarchandise.STANDARD)));
        assertFalse(new Camion("A", 19000, true).peutPrendreEnCharge(ModeTransport.ROUTE, colis(100, TypeMarchandise.REFRIGERE)));
    }

    @Test
    void le_camion_frigorifique_peut_remplacer_un_camion_au_chargement() {
        // Liskov : charger() ne lève plus d'exception, quel que soit le véhicule
        Camion camion = new CamionFrigorifique("IJ-789-KL", 8000);
        camion.charger(colis(100, TypeMarchandise.STANDARD).get(0));
        assertEquals(100, camion.getChargeActuelle(), 0.001);
    }

    @Test
    void capacite_et_disponibilite() {
        Camion camion = new Camion("A", 1000, false);
        assertFalse(camion.peutPrendreEnCharge(ModeTransport.ROUTE, colis(1500, TypeMarchandise.STANDARD)));
        camion.setDisponible(false);
        assertFalse(camion.peutPrendreEnCharge(ModeTransport.ROUTE, colis(10, TypeMarchandise.STANDARD)));
    }

    @Test
    void un_bateau_sans_conteneurs_frigorifiques_refuse_le_refrigere() {
        assertFalse(new Bateau("B1", 1_000_000, false).peutPrendreEnCharge(ModeTransport.MER, colis(100, TypeMarchandise.REFRIGERE)));
        assertTrue(new Bateau("B2", 1_000_000, true).peutPrendreEnCharge(ModeTransport.MER, colis(100, TypeMarchandise.REFRIGERE)));
    }

    @Test
    void chaque_vehicule_sait_partir_sans_exception() {
        new Camion("A", 1000, false).partir(100);
        new CamionFrigorifique("F", 1000).partir(100);
        new Train("T", 1000).partir(100);
        new Bateau("B", 1000, true).partir(100);
        new Avion("V", 1000).partir(100);
    }

    @Test
    void l_affectation_prend_le_premier_vehicule_compatible() {
        Flotte flotte = Flotte.initiale();
        AffectationVehicule affectation = new AffectationVehicule(flotte);
        assertEquals("EF-456-GH", affectation.trouverVehicule(colis(100, TypeMarchandise.DANGEREUX), ModeTransport.ROUTE).getImmatriculation());
        assertEquals("TR-0001", affectation.trouverVehicule(colis(100, TypeMarchandise.STANDARD), ModeTransport.RAIL).getImmatriculation());
        assertNull(affectation.trouverVehicule(colis(25000, TypeMarchandise.STANDARD), ModeTransport.ROUTE));
    }
}
