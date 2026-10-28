package fr.efrei.transfret;

/** Fluvial : 0,40 €/km + 0,02 €/kg, minimum 300 €. */
public class TarifFluvial implements TarifTransport {

    private static final double PRIX_KM = 0.40;
    private static final double PRIX_KG = 0.02;
    private static final double MINIMUM = 300;

    @Override
    public double prixDeBase(int distanceKm, double poidsKg, double volumeM3) {
        return Math.max(distanceKm * PRIX_KM + poidsKg * PRIX_KG, MINIMUM);
    }
}
