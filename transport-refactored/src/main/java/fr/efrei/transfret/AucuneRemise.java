package fr.efrei.transfret;

/** Particulier : pas de remise. */
public class AucuneRemise implements PolitiqueRemise {

    @Override
    public double appliquer(double prix) {
        return prix;
    }
}
