package fr.efrei.transfret;

/** Remise client : délègue à la stratégie de remise du type de client (étape 5). */
public class Remise extends DecorateurPrix {

    public Remise(CalculPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    public double calculer(CommandeTransport commande) {
        double prix = enveloppe.calculer(commande);
        return commande.typeClient().getRemise().appliquer(prix);
    }
}
