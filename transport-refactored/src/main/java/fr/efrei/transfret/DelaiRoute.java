package fr.efrei.transfret;

/** Route : distance / 70 km/h, 9 h de conduite par jour (18 h si urgent). */
public class DelaiRoute implements DelaiTransport {

    private static final double VITESSE_KMH = 70.0;
    private static final int HEURES_PAR_JOUR = 9;
    private static final int HEURES_PAR_JOUR_URGENT = 18;

    @Override
    public int jours(int distanceKm, boolean urgent) {
        double heures = distanceKm / VITESSE_KMH;
        int heuresParJour = urgent ? HEURES_PAR_JOUR_URGENT : HEURES_PAR_JOUR;
        return (int) Math.ceil(heures / heuresParJour);
    }
}
