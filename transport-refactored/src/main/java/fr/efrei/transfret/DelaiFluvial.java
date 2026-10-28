package fr.efrei.transfret;

/** Fluvial : distance / 360 arrondi au supérieur, + 1 jour. */
public class DelaiFluvial implements DelaiTransport {

    private static final double KM_PAR_JOUR = 360.0;
    private static final int JOURS_MANUTENTION = 1;

    @Override
    public int jours(int distanceKm, boolean urgent) {
        return (int) Math.ceil(distanceKm / KM_PAR_JOUR) + JOURS_MANUTENTION;
    }
}
