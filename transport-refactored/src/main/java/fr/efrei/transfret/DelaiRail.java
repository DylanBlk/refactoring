package fr.efrei.transfret;

/** Rail : distance / 1 440 arrondi au supérieur, + 1 jour (- 1 si urgent). */
public class DelaiRail implements DelaiTransport {

    private static final double KM_PAR_JOUR = 1440.0;
    private static final int JOURS_MANUTENTION = 1;

    @Override
    public int jours(int distanceKm, boolean urgent) {
        int jours = (int) Math.ceil(distanceKm / KM_PAR_JOUR) + JOURS_MANUTENTION;
        if (urgent) {
            jours = jours - 1;
        }
        return jours;
    }
}
