package fr.efrei.transfret;

/** Le cœur de la « poupée russe » : le tarif de base du mode de transport (stratégie de l'étape 5). */
public class PrixDeBase implements CalculPrix {

    @Override
    public double calculer(CommandeTransport commande) {
        return commande.mode().getTarif().prixDeBase(commande.distanceKm(), commande.poidsTotal(), commande.volumeTotal());
    }
}
