package fr.efrei.transfret;

/** Les types de client, avec ce qui les distingue. */
public enum TypeClient {
    PARTICULIER(false, false),
    ENTREPRISE(true, false),
    PREMIUM(false, true);

    private final boolean siretObligatoire;
    private final boolean recoitSms;

    TypeClient(boolean siretObligatoire, boolean recoitSms) {
        this.siretObligatoire = siretObligatoire;
        this.recoitSms = recoitSms;
    }

    public boolean isSiretObligatoire() {
        return siretObligatoire;
    }

    public boolean isRecoitSms() {
        return recoitSms;
    }
}
