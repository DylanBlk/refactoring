package fr.efrei.transfret;

/** Strategy : le tarif de base d'un mode de transport. */
public interface TarifTransport {

    double prixDeBase(int distanceKm, double poidsKg, double volumeM3);
}
