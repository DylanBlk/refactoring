package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Tests unitaires des stratégies de remise (étape 5). */
class PolitiqueRemiseTest {

    private static final double CENTIME = 0.001;

    @Test
    void particulier_aucune_remise() {
        assertEquals(1000.0, new AucuneRemise().appliquer(1000), CENTIME);
    }

    @Test
    void entreprise_10_pourcent() {
        assertEquals(900.0, new RemiseEntreprise().appliquer(1000), CENTIME);
    }

    @Test
    void entreprise_15_pourcent_au_dela_de_5000() {
        assertEquals(5100.0, new RemiseEntreprise().appliquer(6000), CENTIME);
    }

    @Test
    void entreprise_10_pourcent_a_5000_pile() {
        assertEquals(4500.0, new RemiseEntreprise().appliquer(5000), CENTIME);
    }

    @Test
    void premium_20_pourcent() {
        assertEquals(800.0, new RemisePremium().appliquer(1000), CENTIME);
    }

    @Test
    void chaque_type_de_client_porte_sa_remise() {
        assertEquals(1000.0, TypeClient.PARTICULIER.getRemise().appliquer(1000), CENTIME);
        assertEquals(900.0, TypeClient.ENTREPRISE.getRemise().appliquer(1000), CENTIME);
        assertEquals(800.0, TypeClient.PREMIUM.getRemise().appliquer(1000), CENTIME);
    }
}
