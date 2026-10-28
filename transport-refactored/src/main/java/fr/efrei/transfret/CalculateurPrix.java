package fr.efrei.transfret;

import java.util.List;

/**
 * Assemble la chaîne de décorateurs et calcule le prix HT arrondi au centime.
 * <p>
 * L'ordre compte : la remise s'applique après les suppléments et l'urgence, l'assurance après la remise.
 * <pre>
 * Assurance( Remise( Urgence( Réfrigéré( Dangereux( Fragile( PrixDeBase ) ) ) ) ) )
 * </pre>
 */
public class CalculateurPrix {

    private final CalculPrix chaine;

    public CalculateurPrix() {
        CalculPrix calcul = new PrixDeBase();
        calcul = new SupplementFragile(calcul);
        calcul = new SupplementDangereux(calcul);
        calcul = new SupplementRefrigere(calcul);
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
