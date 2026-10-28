package fr.efrei.transfret;

/** Mer : distance / 720 arrondi au supérieur, + 2 jours. */
public class DelaiMer implements DelaiTransport {

    private static final double KM_PAR_JOUR = 720.0;
    private static final int JOURS_MANUTENTION = 2;

    @Override
    public int jours(int distanceKm, boolean urgent) {
        return (int) Math.ceil(distanceKm / KM_PAR_JOUR) + JOURS_MANUTENTION;
    }
}
