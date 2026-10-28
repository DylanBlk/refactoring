package fr.efrei.transfret;

import java.util.List;

/** Vérifie les règles d'acceptation d'une expédition (hors trajet et véhicule). */
public class ValidateurExpedition {

    /** @return true si la demande respecte les règles ; affiche l'erreur sinon */
    public boolean estValide(Client client, List<Colis> colis, ModeTransport mode, boolean urgent) {
        if (client == null) {
            System.out.println("ERREUR : client obligatoire");
            return false;
        }
        if (colis == null || colis.isEmpty()) {
            System.out.println("ERREUR : au moins un colis est nécessaire");
            return false;
        }
        boolean dangereux = false;
        boolean refrigere = false;
        for (Colis c : colis) {
            if (c.getPoids() <= 0) {
                System.out.println("ERREUR : poids invalide pour " + c.getDescription());
                return false;
            }
            if (c.getType() == TypeMarchandise.DANGEREUX) dangereux = true;
            if (c.getType() == TypeMarchandise.REFRIGERE) refrigere = true;
        }
        if (dangereux && !mode.accepteDangereux()) {
            System.out.println("ERREUR : matières dangereuses interdites en " + mode);
            return false;
        }
        if (refrigere && !mode.accepteRefrigere()) {
            System.out.println("ERREUR : transport réfrigéré impossible en " + mode);
            return false;
        }
        if (urgent && !mode.accepteUrgence()) {
            System.out.println("ERREUR : pas d'option urgente en " + mode);
            return false;
        }
        return true;
    }
}
