package fr.efrei.transfret;

import java.util.Random;

/** Fabrique les identifiants d'expédition : EXP-<numéro>-<4 chiffres au hasard>. */
public class GenerateurIdentifiant {

    private static final int ALEA_MIN = 1000;
    private static final int ALEA_PLAGE = 9000;

    private final Random random = new Random();
    private int compteur = 0;

    public String prochainId() {
        compteur++;
        return "EXP-" + compteur + "-" + (ALEA_MIN + random.nextInt(ALEA_PLAGE));
    }
}
