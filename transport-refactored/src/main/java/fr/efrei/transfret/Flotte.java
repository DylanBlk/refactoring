package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

/** Les véhicules de TransFret. */
public class Flotte {

    private final List<Vehicule> vehicules = new ArrayList<>();

    /** La flotte de départ de l'entreprise. */
    public static Flotte initiale() {
        Flotte flotte = new Flotte();
        flotte.ajouter(new Camion("AB-123-CD", 19000, false));
        flotte.ajouter(new Camion("EF-456-GH", 12000, true));
        flotte.ajouter(new CamionFrigorifique("IJ-789-KL", 8000));
        flotte.ajouter(new Train("TR-0001", 600000));
        flotte.ajouter(new Bateau("CMA-CGM-01", 20000000, true));
        flotte.ajouter(new Avion("F-GTXA", 100000));
        return flotte;
    }

    public void ajouter(Vehicule vehicule) {
        vehicules.add(vehicule);
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }
}
