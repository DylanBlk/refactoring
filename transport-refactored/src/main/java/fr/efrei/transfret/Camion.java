package fr.efrei.transfret;

import java.util.List;

/** Camion : transporte tout sauf le réfrigéré, et le dangereux seulement s'il est certifié ADR. */
public class Camion extends Vehicule implements Ravitaillable {

    private final boolean certifieAdr;

    public Camion(String immatriculation, double capaciteMax, boolean certifieAdr) {
        super(immatriculation, ModeTransport.ROUTE, capaciteMax);
        this.certifieAdr = certifieAdr;
    }

    @Override
    protected boolean accepteColis(List<Colis> colis) {
        if (contient(colis, TypeMarchandise.REFRIGERE)) {
            return false;
        }
        return certifieAdr || !contient(colis, TypeMarchandise.DANGEREUX);
    }

    @Override
    public void faireLePlein() {
        System.out.println("Plein effectué pour " + immatriculation);
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        System.out.println("Le camion " + immatriculation + " roule sur " + distanceKm + " km");
    }
}
