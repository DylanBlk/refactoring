package fr.efrei.transfret;

import java.util.List;

/** Péniche fluviale. Les règles du mode fluvial (pas de réfrigéré) sont vérifiées avant. */
public class Peniche extends Vehicule implements Ravitaillable {

    public Peniche(String immatriculation, double capaciteMax) {
        super(immatriculation, ModeTransport.FLUVIAL, capaciteMax);
    }

    @Override
    protected boolean accepteColis(List<Colis> colis) {
        return true;
    }

    @Override
    public void faireLePlein() {
        System.out.println("Plein effectué pour " + immatriculation);
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        System.out.println("La péniche " + immatriculation + " navigue sur " + distanceKm + " km de voies navigables");
    }
}
