package fr.efrei.transfret;

/** Mer : 0,50 €/km + 0,03 €/kg, minimum 500 €. */
public class TarifMer implements TarifTransport {

    private static final double PRIX_KM = 0.50;
    private static final double PRIX_KG = 0.03;
    private static final double MINIMUM = 500;

    @Override
    public double prixDeBase(int distanceKm, double poidsKg, double volumeM3) {
        return Math.max(distanceKm * PRIX_KM + poidsKg * PRIX_KG, MINIMUM);
    }
}
