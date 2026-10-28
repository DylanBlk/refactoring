package fr.efrei.transfret;

/** Route : 1,20 €/km + 0,10 €/kg. */
public class TarifRoute implements TarifTransport {

    private static final double PRIX_KM = 1.20;
    private static final double PRIX_KG = 0.10;

    @Override
    public double prixDeBase(int distanceKm, double poidsKg, double volumeM3) {
        return distanceKm * PRIX_KM + poidsKg * PRIX_KG;
    }
}
