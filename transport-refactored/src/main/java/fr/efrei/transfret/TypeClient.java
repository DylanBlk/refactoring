package fr.efrei.transfret;

/** Les types de client, avec ce qui les distingue (dont leur stratégie de remise). */
public enum TypeClient {
    PARTICULIER(false, false, new AucuneRemise()),
    ENTREPRISE(true, false, new RemiseEntreprise()),
    PREMIUM(false, true, new RemisePremium());

    private final boolean siretObligatoire;
    private final boolean recoitSms;
    private final PolitiqueRemise remise;

    TypeClient(boolean siretObligatoire, boolean recoitSms, PolitiqueRemise remise) {
        this.siretObligatoire = siretObligatoire;
        this.recoitSms = recoitSms;
        this.remise = remise;
    }

    public boolean isSiretObligatoire() {
        return siretObligatoire;
    }

    public boolean isRecoitSms() {
        return recoitSms;
    }

    public PolitiqueRemise getRemise() {
        return remise;
    }
}
