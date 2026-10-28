package fr.efrei.transfret;

import java.util.List;

/** Avion cargo. Les règles du mode aérien (pas de dangereux, pas de réfrigéré) sont vérifiées avant. */
public class Avion extends Vehicule implements Ravitaillable {

    private static final int ALTITUDE_CROISIERE_PIEDS = 35000;

    public Avion(String immatriculation, double capaciteMax) {
        super(immatriculation, ModeTransport.AIR, capaciteMax);
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
        System.out.println("L'avion " + immatriculation + " roule sur la piste");
        System.out.println("L'avion " + immatriculation + " vole à " + ALTITUDE_CROISIERE_PIEDS + " pieds");
    }
}
