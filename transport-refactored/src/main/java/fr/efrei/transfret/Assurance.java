package fr.efrei.transfret;

/** Assurance (optionnelle) : + 2 % de la valeur déclarée totale, minimum 15 €. */
public class Assurance extends DecorateurPrix {

    private static final double TAUX = 0.02;
    private static final double MINIMUM = 15;

    public Assurance(CalculPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    public double calculer(CommandeTransport commande) {
        double prix = enveloppe.calculer(commande);
        if (commande.assurance()) {
            prix = prix + Math.max(commande.valeurTotale() * TAUX, MINIMUM);
        }
        return prix;
    }
}
