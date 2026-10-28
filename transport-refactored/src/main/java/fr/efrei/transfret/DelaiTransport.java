package fr.efrei.transfret;

/** Strategy : le délai de livraison (en jours) d'un mode de transport. */
public interface DelaiTransport {

    int jours(int distanceKm, boolean urgent);
}
