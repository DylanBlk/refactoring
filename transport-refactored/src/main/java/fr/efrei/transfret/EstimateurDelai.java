package fr.efrei.transfret;

/** Calcule le délai de livraison en jours (au moins 1), en déléguant au mode de transport. */
public class EstimateurDelai {

    private static final int DELAI_MINIMUM_JOURS = 1;

    public int calculerDelai(int distance, ModeTransport mode, boolean urgent) {
        return Math.max(mode.getDelai().jours(distance, urgent), DELAI_MINIMUM_JOURS);
    }
}
