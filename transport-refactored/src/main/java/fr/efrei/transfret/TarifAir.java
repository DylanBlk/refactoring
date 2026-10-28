package fr.efrei.transfret;

/** Air : 0,60 €/km + 4,50 €/kg taxable (le plus grand du poids réel et du poids volumétrique). */
public class TarifAir implements TarifTransport {

    private static final double PRIX_KM = 0.60;
    private static final double PRIX_KG_TAXABLE = 4.50;
    private static final double KG_PAR_M3 = 167;

    @Override
    public double prixDeBase(int distanceKm, double poidsKg, double volumeM3) {
        double poidsTaxable = Math.max(poidsKg, volumeM3 * KG_PAR_M3);
        return distanceKm * PRIX_KM + poidsTaxable * PRIX_KG_TAXABLE;
    }
}
