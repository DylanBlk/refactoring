package fr.efrei.transfret;

/** Supplément ×1.15 s'il y a au moins un colis fragile. */
public class SupplementFragile extends DecorateurPrix {

    private static final double COEF = 1.15;

    public SupplementFragile(CalculPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    public double calculer(CommandeTransport commande) {
        double prix = enveloppe.calculer(commande);
        if (commande.contient(TypeMarchandise.FRAGILE)) {
            prix = prix * COEF;
        }
        return prix;
    }
}
