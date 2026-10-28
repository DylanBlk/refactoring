package fr.efrei.transfret;

/** Urgence : ×1,5. */
public class SupplementUrgence extends DecorateurPrix {

    private static final double COEF = 1.5;

    public SupplementUrgence(CalculPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    public double calculer(CommandeTransport commande) {
        double prix = enveloppe.calculer(commande);
        if (commande.urgent()) {
            prix = prix * COEF;
        }
        return prix;
    }
}
