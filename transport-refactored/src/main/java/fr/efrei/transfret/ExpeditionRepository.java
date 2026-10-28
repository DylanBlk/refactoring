package fr.efrei.transfret;

/** Où sont rangées les expéditions. */
public interface ExpeditionRepository {

    void sauvegarder(Expedition expedition);

    /** @return l'expédition, ou null si elle n'existe pas */
    Expedition trouver(String id);
}
