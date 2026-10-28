package fr.efrei.transfret;

import java.util.List;

/** Tout ce qu'il faut savoir pour calculer un prix (expédition ou simple devis). */
public record CommandeTransport(List<Colis> colis, int distanceKm, ModeTransport mode, boolean urgent,
                                boolean assurance, TypeClient typeClient) {

    public double poidsTotal() {
        double total = 0;
        for (Colis c : colis) {
            total = total + c.getPoids();
        }
        return total;
    }

    public double volumeTotal() {
        double total = 0;
        for (Colis c : colis) {
            total = total + c.getVolume();
        }
        return total;
    }

    public double valeurTotale() {
        double total = 0;
        for (Colis c : colis) {
            total = total + c.getValeurDeclaree();
        }
        return total;
    }

    /** @return true si au moins un colis est de ce type */
    public boolean contient(TypeMarchandise type) {
        for (Colis c : colis) {
            if (c.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
