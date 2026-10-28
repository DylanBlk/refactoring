package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Tests unitaires des décorateurs (étape 6). Chaque décorateur enveloppe un faux prix fixe de 1000 €. */
class DecorateurPrixTest {

    private static final double CENTIME = 0.001;

    /** Faux calcul de prix : renvoie toujours 1000 €, pour tester un décorateur tout seul. */
    private static final CalculPrix MILLE_EUROS = commande -> 1000;

    private static Colis colis(TypeMarchandise type, double valeur) {
        return new Colis("colis", 10, 50, 40, 30, type, valeur);
    }

    private static CommandeTransport commande(List<Colis> colis, boolean urgent, boolean assurance, TypeClient client) {
        return new CommandeTransport(colis, 100, ModeTransport.ROUTE, urgent, assurance, client);
    }

    private static CommandeTransport standard() {
        return commande(List.of(colis(TypeMarchandise.STANDARD, 100)), false, false, TypeClient.PARTICULIER);
    }

    @Test
    void prix_de_base_delegue_au_tarif_du_mode() {
        // 100 km x 1,20 + 10 kg x 0,10
        assertEquals(121.0, new PrixDeBase().calculer(standard()), CENTIME);
    }

    @Test
    void supplement_fragile() {
        CommandeTransport fragile = commande(List.of(colis(TypeMarchandise.FRAGILE, 100)), false, false, TypeClient.PARTICULIER);
        assertEquals(1150.0, new SupplementFragile(MILLE_EUROS).calculer(fragile), CENTIME);
        assertEquals(1000.0, new SupplementFragile(MILLE_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void supplement_dangereux() {
        CommandeTransport dangereux = commande(List.of(colis(TypeMarchandise.DANGEREUX, 100)), false, false, TypeClient.PARTICULIER);
        assertEquals(1300.0, new SupplementDangereux(MILLE_EUROS).calculer(dangereux), CENTIME);
        assertEquals(1000.0, new SupplementDangereux(MILLE_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void supplement_refrigere() {
        CommandeTransport refrigere = commande(List.of(colis(TypeMarchandise.REFRIGERE, 100)), false, false, TypeClient.PARTICULIER);
        assertEquals(1200.0, new SupplementRefrigere(MILLE_EUROS).calculer(refrigere), CENTIME);
        assertEquals(1000.0, new SupplementRefrigere(MILLE_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void supplement_urgence() {
        CommandeTransport urgente = commande(List.of(colis(TypeMarchandise.STANDARD, 100)), true, false, TypeClient.PARTICULIER);
        assertEquals(1500.0, new SupplementUrgence(MILLE_EUROS).calculer(urgente), CENTIME);
        assertEquals(1000.0, new SupplementUrgence(MILLE_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void remise_selon_le_type_de_client() {
        CommandeTransport premium = commande(List.of(colis(TypeMarchandise.STANDARD, 100)), false, false, TypeClient.PREMIUM);
        assertEquals(800.0, new Remise(MILLE_EUROS).calculer(premium), CENTIME);
        assertEquals(1000.0, new Remise(MILLE_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void assurance_2_pourcent_de_la_valeur() {
        CommandeTransport assuree = commande(List.of(colis(TypeMarchandise.STANDARD, 5000)), false, true, TypeClient.PARTICULIER);
        assertEquals(1100.0, new Assurance(MILLE_EUROS).calculer(assuree), CENTIME);
    }

    @Test
    void assurance_minimum_15_euros() {
        CommandeTransport assuree = commande(List.of(colis(TypeMarchandise.STANDARD, 100)), false, true, TypeClient.PARTICULIER);
        assertEquals(1015.0, new Assurance(MILLE_EUROS).calculer(assuree), CENTIME);
    }

    @Test
    void pas_d_assurance_si_non_demandee() {
        assertEquals(1000.0, new Assurance(MILLE_EUROS).calculer(standard()), CENTIME);
    }

    @Test
    void les_decorateurs_s_empilent() {
        CommandeTransport fragileEtDangereux = commande(
                List.of(colis(TypeMarchandise.FRAGILE, 100), colis(TypeMarchandise.DANGEREUX, 100)), false, false, TypeClient.PARTICULIER);
        CalculPrix chaine = new SupplementDangereux(new SupplementFragile(MILLE_EUROS));
        assertEquals(1495.0, chaine.calculer(fragileEtDangereux), CENTIME); // 1000 x 1,15 x 1,30
    }
}
