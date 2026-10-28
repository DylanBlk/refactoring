package fr.efrei.transfret;

/** Arrondi des montants, écrit une seule fois. */
public final class Arrondi {

    private static final double CENTIMES_PAR_EURO = 100.0;

    private Arrondi() {
    }

    public static double auCentime(double montant) {
        return Math.round(montant * CENTIMES_PAR_EURO) / CENTIMES_PAR_EURO;
    }
}
