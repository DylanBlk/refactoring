package fr.efrei.transfret;

import java.util.List;

/**
 * Assemble la chaîne de décorateurs et calcule le prix HT arrondi au centime.
 * <p>
 * L'ordre compte : la remise s'applique après les suppléments et l'urgence, l'assurance après la remise.
 * <pre>
 * Assurance( Remise( Urgence( [HorsGabarit]( Réfrigéré( Dangereux( Fragile( PrixDeBase ) ) ) ) ) ) )
 * </pre>
 * Le supplément « hors gabarit » est une nouvelle règle : elle change le prix des colis de plus de 2 m.
 * On peut donc choisir les tarifs historiques (ceux des tests de contrat) ou les nouveaux tarifs.
 */
public class CalculateurPrix {

    private final CalculPrix chaine;

    /** Les tarifs historiques, sans le supplément hors gabarit. */
    public static CalculateurPrix tarifsHistoriques() {
        return new CalculateurPrix(false);
    }

    /** Les nouveaux tarifs, avec le supplément hors gabarit. */
    public static CalculateurPrix avecHorsGabarit() {
        return new CalculateurPrix(true);
    }

    private CalculateurPrix(boolean horsGabarit) {
        CalculPrix calcul = new PrixDeBase();
        calcul = new SupplementFragile(calcul);
        calcul = new SupplementDangereux(calcul);
        calcul = new SupplementRefrigere(calcul);
        if (horsGabarit) {
            calcul = new SupplementHorsGabarit(calcul);
        }
        calcul = new SupplementUrgence(calcul);
        calcul = new Remise(calcul);
        calcul = new Assurance(calcul);
        this.chaine = calcul;
    }

    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        CommandeTransport commande = new CommandeTransport(colis, distance, mode, urgent, assurance, typeClient);
        return Arrondi.auCentime(chaine.calculer(commande));
    }
}
