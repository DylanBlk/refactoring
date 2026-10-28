package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

/**
 * Un véhicule de la flotte. Chaque véhicule répond lui-même à la question
 * « puis-je prendre en charge ces colis, pour ce mode ? » et sait comment partir.
 */
public abstract class Vehicule {

    protected final String immatriculation;
    private final ModeTransport mode;
    private final double capaciteMax;
    private double chargeActuelle;
    private boolean disponible = true;
    protected final List<Colis> chargement = new ArrayList<>();

    protected Vehicule(String immatriculation, ModeTransport mode, double capaciteMax) {
        this.immatriculation = immatriculation;
        this.mode = mode;
        this.capaciteMax = capaciteMax;
    }

    /** Vrai si le véhicule est libre, roule dans ce mode, a la place et accepte ce type de colis. */
    public boolean peutPrendreEnCharge(ModeTransport modeDemande, List<Colis> colis) {
        return disponible
                && mode == modeDemande
                && capaciteMax - chargeActuelle >= poids(colis)
                && accepteColis(colis);
    }

    /** Chaque sorte de véhicule dit quels colis elle accepte. */
    protected abstract boolean accepteColis(List<Colis> colis);

    /** Chaque sorte de véhicule part à sa façon (rouler, naviguer, voler…). */
    public abstract void partir(int distanceKm);

    public void charger(Colis colis) {
        chargement.add(colis);
        chargeActuelle = chargeActuelle + colis.getPoids();
    }

    public void decharger(Colis colis) {
        if (chargement.remove(colis)) {
            chargeActuelle = chargeActuelle - colis.getPoids();
        }
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public double getCapaciteMax() {
        return capaciteMax;
    }

    public double getChargeActuelle() {
        return chargeActuelle;
    }

    public boolean estDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    protected static boolean contient(List<Colis> colis, TypeMarchandise type) {
        for (Colis c : colis) {
            if (c.getType() == type) {
                return true;
            }
        }
        return false;
    }

    private static double poids(List<Colis> colis) {
        double total = 0;
        for (Colis c : colis) {
            total = total + c.getPoids();
        }
        return total;
    }
}
