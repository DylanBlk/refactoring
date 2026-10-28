package fr.efrei.transfret;

/** Air : 1 jour, 2 jours au-delà de 3 000 km, toujours 1 jour si urgent. */
public class DelaiAir implements DelaiTransport {

    private static final int SEUIL_LONG_COURRIER_KM = 3000;

    @Override
    public int jours(int distanceKm, boolean urgent) {
        if (urgent || distanceKm <= SEUIL_LONG_COURRIER_KM) {
            return 1;
        }
        return 2;
    }
}
