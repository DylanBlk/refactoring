package fr.efrei.transfret;

/** Rail : 0,80 €/km + 0,07 €/kg, minimum 150 €. */
public class TarifRail implements TarifTransport {

    private static final double PRIX_KM = 0.80;
    private static final double PRIX_KG = 0.07;
    private static final double MINIMUM = 150;

    @Override
    public double prixDeBase(int distanceKm, double poidsKg, double volumeM3) {
        return Math.max(distanceKm * PRIX_KM + poidsKg * PRIX_KG, MINIMUM);
    }
}
