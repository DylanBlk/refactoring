package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests du mode FLUVIAL (étape 8). */
class FluvialTest {

    private static final double CENTIME = 0.001;

    private static List<Colis> colis(double poids, TypeMarchandise type) {
        return List.of(new Colis("colis", poids, 50, 40, 30, type, 100));
    }

    @Test
    void tarif_au_km_et_au_kg() {
        // 365 km x 0,40 + 10 000 kg x 0,02
        assertEquals(346.0, new TarifFluvial().prixDeBase(365, 10000, 0), CENTIME);
    }

    @Test
    void tarif_minimum_300_euros() {
        assertEquals(300.0, new TarifFluvial().prixDeBase(365, 100, 0), CENTIME);
    }

    @Test
    void delai() {
        assertEquals(3, new DelaiFluvial().jours(365, false)); // 365 / 360 -> 2, + 1
        assertEquals(2, new DelaiFluvial().jours(360, false));
    }

    @Test
    void regles_du_mode() {
        assertFalse(ModeTransport.FLUVIAL.accepteRefrigere());
        assertFalse(ModeTransport.FLUVIAL.accepteUrgence());
        assertTrue(ModeTransport.FLUVIAL.accepteDangereux());
    }

    @Test
    void la_peniche_ne_navigue_qu_en_fluvial() {
        Peniche peniche = new Peniche("PEN-0001", 2_500_000);
        assertTrue(peniche.peutPrendreEnCharge(ModeTransport.FLUVIAL, colis(1000, TypeMarchandise.STANDARD)));
        assertFalse(peniche.peutPrendreEnCharge(ModeTransport.MER, colis(1000, TypeMarchandise.STANDARD)));
    }

    @Test
    void expedition_fluviale_de_bout_en_bout() {
        Application application = new Application();
        Client client = application.getServiceClient().creerClient("Jean Dupont", "jean.dupont@mail.fr", "0601020304",
                "12 rue de la Paix, 75002 Paris", TypeClient.PARTICULIER, null);
        Expedition e = application.getServiceExpedition().creerExpedition(client, colis(10000, TypeMarchandise.STANDARD),
                "Paris", "Le Havre", ModeTransport.FLUVIAL, false, false);
        assertNotNull(e);
        assertEquals("PEN-0001", e.getVehicule().getImmatriculation());
        assertEquals(346.0, e.getPrixHT(), CENTIME);
        assertEquals(415.2, e.getPrixTTC(), CENTIME);
        assertEquals(3, (int) java.time.temporal.ChronoUnit.DAYS.between(e.getDateCreation(), e.getDateLivraisonPrevue()));
        assertTrue(application.getServiceExpedition().demarrerExpedition(e.getId()));
        assertTrue(application.getServiceExpedition().livrerExpedition(e.getId()));
    }

    @Test
    void fluvial_refuse_le_refrigere_et_l_urgence() {
        Application application = new Application();
        Client client = application.getServiceClient().creerClient("A", "a@b.fr", "06", "adr", TypeClient.PARTICULIER, null);
        assertNull(application.getServiceExpedition().creerExpedition(client, colis(100, TypeMarchandise.REFRIGERE),
                "Paris", "Le Havre", ModeTransport.FLUVIAL, false, false));
        assertNull(application.getServiceExpedition().creerExpedition(client, colis(100, TypeMarchandise.STANDARD),
                "Paris", "Le Havre", ModeTransport.FLUVIAL, true, false));
    }
}
