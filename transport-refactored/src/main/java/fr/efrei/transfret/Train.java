package fr.efrei.transfret;

import java.util.List;

/** Train électrique : pas de plein à faire, pas de wagons frigorifiques. */
public class Train extends Vehicule {

    public Train(String immatriculation, double capaciteMax) {
        super(immatriculation, ModeTransport.RAIL, capaciteMax);
    }

    @Override
    protected boolean accepteColis(List<Colis> colis) {
        return true;
    }

    @Override
    public void partir(int distanceKm) {
        System.out.println("Le train " + immatriculation + " circule sur " + distanceKm + " km de voies ferrées");
    }
}
