package fr.efrei.transfret;

/** Supplément ×1.30 s'il y a au moins un colis dangereux. */
public class SupplementDangereux extends DecorateurPrix {

    private static final double COEF = 1.30;

    public SupplementDangereux(CalculPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    public double calculer(CommandeTransport commande) {
        double prix = enveloppe.calculer(commande);
        if (commande.contient(TypeMarchandise.DANGEREUX)) {
            prix = prix * COEF;
        }
        return prix;
    }
}
