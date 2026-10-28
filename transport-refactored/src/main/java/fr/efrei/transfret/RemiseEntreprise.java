package fr.efrei.transfret;

/** Entreprise : 10 % de remise, ou 15 % si le prix dépasse 5 000 €. */
public class RemiseEntreprise implements PolitiqueRemise {

    private static final double SEUIL_GROSSE_COMMANDE = 5000;
    private static final double COEF_GROSSE_COMMANDE = 0.85;
    private static final double COEF_NORMAL = 0.90;

    @Override
    public double appliquer(double prix) {
        if (prix > SEUIL_GROSSE_COMMANDE) {
            return prix * COEF_GROSSE_COMMANDE;
        }
        return prix * COEF_NORMAL;
    }
}
