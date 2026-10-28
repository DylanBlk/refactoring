package fr.efrei.transfret;

/** Supplément ×1.20 s'il y a au moins un colis réfrigéré. */
public class SupplementRefrigere extends DecorateurPrix {

    private static final double COEF = 1.20;

    public SupplementRefrigere(CalculPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    public double calculer(CommandeTransport commande) {
        double prix = enveloppe.calculer(commande);
        if (commande.contient(TypeMarchandise.REFRIGERE)) {
            prix = prix * COEF;
        }
        return prix;
    }
}
