package fr.efrei.transfret;

import java.util.List;

/** Choisit le premier véhicule de la flotte capable de prendre l'expédition. */
public class AffectationVehicule {

    private final Flotte flotte;

    public AffectationVehicule(Flotte flotte) {
        this.flotte = flotte;
    }

    /** @return le premier véhicule disponible et compatible, null si aucun */
    public Vehicule trouverVehicule(List<Colis> colis, ModeTransport mode) {
        for (Vehicule v : flotte.getVehicules()) {
            if (v.peutPrendreEnCharge(mode, colis)) {
                return v;
            }
        }
        return null;
    }
}
