package fr.efrei.transfret;

/** Les types de client. Seules les entreprises doivent fournir un SIRET. */
public enum TypeClient {
    PARTICULIER(false),
    ENTREPRISE(true),
    PREMIUM(false);

    private final boolean siretObligatoire;

    TypeClient(boolean siretObligatoire) {
        this.siretObligatoire = siretObligatoire;
    }

    public boolean isSiretObligatoire() {
        return siretObligatoire;
    }
}
