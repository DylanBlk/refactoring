package fr.efrei.transfret;

/** Supplément « hors gabarit » : ×1,10 si un colis dépasse 200 cm sur une de ses dimensions. */
public class SupplementHorsGabarit extends DecorateurPrix {

    private static final double COEF = 1.10;
    private static final double DIMENSION_MAX_CM = 200;

    public SupplementHorsGabarit(CalculPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    public double calculer(CommandeTransport commande) {
        double prix = enveloppe.calculer(commande);
        if (contientUnColisHorsGabarit(commande)) {
            prix = prix * COEF;
        }
        return prix;
    }

    private static boolean contientUnColisHorsGabarit(CommandeTransport commande) {
        for (Colis c : commande.colis()) {
            if (c.getLongueur() > DIMENSION_MAX_CM || c.getLargeur() > DIMENSION_MAX_CM || c.getHauteur() > DIMENSION_MAX_CM) {
                return true;
            }
        }
        return false;
    }
}
