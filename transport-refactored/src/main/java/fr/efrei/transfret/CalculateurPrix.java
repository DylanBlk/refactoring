package fr.efrei.transfret;

import java.util.List;

public class CalculateurPrix {

    private static final double ROUTE_PRIX_KM = 1.20;
    private static final double ROUTE_PRIX_KG = 0.10;
    private static final double RAIL_PRIX_KM = 0.80;
    private static final double RAIL_PRIX_KG = 0.07;
    private static final double RAIL_MINIMUM = 150;
    private static final double MER_PRIX_KM = 0.50;
    private static final double MER_PRIX_KG = 0.03;
    private static final double MER_MINIMUM = 500;
    private static final double AIR_PRIX_KM = 0.60;
    private static final double AIR_PRIX_KG_TAXABLE = 4.50;
    private static final double AIR_KG_PAR_M3 = 167;

    private static final double SUPPLEMENT_FRAGILE = 1.15;
    private static final double SUPPLEMENT_DANGEREUX = 1.30;
    private static final double SUPPLEMENT_REFRIGERE = 1.20;
    private static final double SUPPLEMENT_URGENCE = 1.5;

    private static final double SEUIL_GROSSE_COMMANDE = 5000;
    private static final double REMISE_ENTREPRISE_GROSSE_COMMANDE = 0.85;
    private static final double REMISE_ENTREPRISE = 0.90;
    private static final double REMISE_PREMIUM = 0.80;

    private static final double TAUX_ASSURANCE = 0.02;
    private static final double ASSURANCE_MINIMUM = 15;

    public double calculerPrixHT(List<Colis> colis, int distance, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        double poids = 0;
        double volume = 0;
        double valeur = 0;
        boolean fragile = false;
        boolean dangereux = false;
        boolean refrigere = false;
        for (Colis c : colis) {
            poids = poids + c.getPoids();
            volume = volume + c.getVolume();
            valeur = valeur + c.getValeurDeclaree();
            if (c.getType() == TypeMarchandise.FRAGILE) fragile = true;
            if (c.getType() == TypeMarchandise.DANGEREUX) dangereux = true;
            if (c.getType() == TypeMarchandise.REFRIGERE) refrigere = true;
        }

        // tarif de base selon le mode
        double prix;
        if (mode == ModeTransport.ROUTE) {
            prix = distance * ROUTE_PRIX_KM + poids * ROUTE_PRIX_KG;
        } else if (mode == ModeTransport.RAIL) {
            prix = Math.max(distance * RAIL_PRIX_KM + poids * RAIL_PRIX_KG, RAIL_MINIMUM);
        } else if (mode == ModeTransport.MER) {
            prix = Math.max(distance * MER_PRIX_KM + poids * MER_PRIX_KG, MER_MINIMUM);
        } else if (mode == ModeTransport.AIR) {
            double poidsTaxable = Math.max(poids, volume * AIR_KG_PAR_M3);
            prix = distance * AIR_PRIX_KM + poidsTaxable * AIR_PRIX_KG_TAXABLE;
        } else {
            throw new IllegalArgumentException("Mode inconnu : " + mode);
        }

        // suppléments
        if (fragile) prix = prix * SUPPLEMENT_FRAGILE;
        if (dangereux) prix = prix * SUPPLEMENT_DANGEREUX;
        if (refrigere) prix = prix * SUPPLEMENT_REFRIGERE;
        if (urgent) prix = prix * SUPPLEMENT_URGENCE;

        // remise client
        if (typeClient == TypeClient.ENTREPRISE) {
            if (prix > SEUIL_GROSSE_COMMANDE) {
                prix = prix * REMISE_ENTREPRISE_GROSSE_COMMANDE;
            } else {
                prix = prix * REMISE_ENTREPRISE;
            }
        } else if (typeClient == TypeClient.PREMIUM) {
            prix = prix * REMISE_PREMIUM;
        }

        // assurance
        if (assurance) {
            prix = prix + Math.max(valeur * TAUX_ASSURANCE, ASSURANCE_MINIMUM);
        }
        return Arrondi.auCentime(prix);
    }
}
