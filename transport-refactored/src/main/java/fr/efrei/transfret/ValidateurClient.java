package fr.efrei.transfret;

/** Vérifie qu'une demande de création de client est correcte. */
public class ValidateurClient {

    private static final int LONGUEUR_SIRET = 14;

    /** @return true si la demande est valide ; affiche l'erreur sinon */
    public boolean estValide(String nom, String email, TypeClient type, String siret) {
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("ERREUR : nom obligatoire");
            return false;
        }
        if (email == null || !email.contains("@") || !email.contains(".")) {
            System.out.println("ERREUR : email invalide (" + email + ")");
            return false;
        }
        if (type == null) {
            System.out.println("ERREUR : type de client inconnu");
            return false;
        }
        if (type.isSiretObligatoire() && (siret == null || siret.length() != LONGUEUR_SIRET)) {
            System.out.println("ERREUR : SIRET obligatoire (14 chiffres) pour une entreprise");
            return false;
        }
        return true;
    }
}
