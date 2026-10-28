package fr.efrei.transfret;

import java.time.LocalDate;
import java.util.List;

/** Orchestre le cycle de vie d'une expédition : création, départ, livraison, annulation. */
public class ServiceExpedition {

    private static final double TAUX_TVA = 1.2;
    private static final double TAUX_FRAIS_ANNULATION = 0.5;

    private static final double KM_PAR_MILLE_NAUTIQUE = 1.852;
    private static final int KM_ROULAGE_PISTE = 2;
    private static final int ALTITUDE_CROISIERE_PIEDS = 35000;
    private static final double TEMPERATURE_FRIGO = 4;

    private final ExpeditionRepository expeditions;
    private final ValidateurExpedition validateur;
    private final TableDistances distances;
    private final AffectationVehicule affectation;
    private final CalculateurPrix calculateur;
    private final EstimateurDelai estimateurDelai;
    private final Notificateur notificateur;
    private final GenerateurIdentifiant generateurId;

    public ServiceExpedition(ExpeditionRepository expeditions, ValidateurExpedition validateur, TableDistances distances,
                             AffectationVehicule affectation, CalculateurPrix calculateur, EstimateurDelai estimateurDelai,
                             Notificateur notificateur, GenerateurIdentifiant generateurId) {
        this.expeditions = expeditions;
        this.validateur = validateur;
        this.distances = distances;
        this.affectation = affectation;
        this.calculateur = calculateur;
        this.estimateurDelai = estimateurDelai;
        this.notificateur = notificateur;
        this.generateurId = generateurId;
    }

    public Expedition creerExpedition(Client client, List<Colis> colis, String villeDepart, String villeArrivee, ModeTransport mode, boolean urgent, boolean assurance) {
        if (!validateur.estValide(client, colis, mode, urgent)) {
            return null;
        }
        int distance = distances.getDistance(villeDepart, villeArrivee);
        if (distance == TableDistances.DISTANCE_INCONNUE) {
            System.out.println("ERREUR : trajet inconnu " + villeDepart + " -> " + villeArrivee);
            return null;
        }
        Vehicule v = affectation.trouverVehicule(colis, mode);
        if (v == null) {
            System.out.println("ERREUR : aucun véhicule disponible pour cette expédition");
            return null;
        }
        for (Colis c : colis) {
            v.charger(c);
        }

        Expedition e = new Expedition();
        e.setId(generateurId.prochainId());
        e.setClient(client);
        e.setColis(colis);
        e.setVilleDepart(villeDepart);
        e.setVilleArrivee(villeArrivee);
        e.setDistance(distance);
        e.setMode(mode);
        e.setUrgent(urgent);
        e.setAssurance(assurance);
        e.setStatut(StatutExpedition.CREEE);
        e.setVehicule(v);
        e.setPrixHT(calculateur.calculerPrixHT(colis, distance, mode, urgent, assurance, client.getType()));
        e.setPrixTTC(Arrondi.auCentime(e.getPrixHT() * TAUX_TVA));
        e.setDateCreation(LocalDate.now());
        e.setDateLivraisonPrevue(e.getDateCreation().plusDays(estimateurDelai.calculerDelai(distance, mode, urgent)));

        expeditions.sauvegarder(e);
        notificateur.notifier(client, new Message("Confirmation de votre expédition " + e.getId(),
                "Votre expédition " + villeDepart + " -> " + villeArrivee + " (" + mode + ") a été enregistrée. Montant : " + e.getPrixTTC() + " € TTC.",
                "TransFret : expédition " + e.getId() + " créée, livraison prévue le " + FormatDate.formater(e.getDateLivraisonPrevue())));
        return e;
    }

    // retourne le prix TTC, TableDistances.DISTANCE_INCONNUE si le trajet est inconnu
    public double calculerDevis(List<Colis> colis, String villeDepart, String villeArrivee, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        int distance = distances.getDistance(villeDepart, villeArrivee);
        if (distance == TableDistances.DISTANCE_INCONNUE) {
            return TableDistances.DISTANCE_INCONNUE;
        }
        double ht = calculateur.calculerPrixHT(colis, distance, mode, urgent, assurance, typeClient);
        return Arrondi.auCentime(ht * TAUX_TVA);
    }

    public boolean demarrerExpedition(String id) {
        Expedition e = expeditions.trouver(id);
        if (e == null || e.getStatut() != StatutExpedition.CREEE) {
            System.out.println("ERREUR : impossible de démarrer l'expédition " + id);
            return false;
        }
        e.setStatut(StatutExpedition.EN_COURS);
        Vehicule v = e.getVehicule();
        v.setDisponible(false);
        try {
            v.faireLePlein();
        } catch (Exception ex) {
        }
        if (e.getMode() == ModeTransport.ROUTE || e.getMode() == ModeTransport.RAIL) {
            v.rouler(e.getDistance());
        } else if (e.getMode() == ModeTransport.MER) {
            v.naviguer((int) (e.getDistance() / KM_PAR_MILLE_NAUTIQUE));
        } else if (e.getMode() == ModeTransport.AIR) {
            v.rouler(KM_ROULAGE_PISTE);
            v.voler(ALTITUDE_CROISIERE_PIEDS);
        }
        for (Colis c : e.getColis()) {
            if (c.getType() == TypeMarchandise.REFRIGERE) {
                v.activerRefrigeration(TEMPERATURE_FRIGO);
                break;
            }
        }
        notificateur.notifier(e.getClient(), new Message("Votre expédition " + e.getId() + " est en route",
                "Votre expédition a quitté " + e.getVilleDepart() + ".",
                "TransFret : expédition " + e.getId() + " partie de " + e.getVilleDepart()));
        return true;
    }

    public boolean livrerExpedition(String id) {
        Expedition e = expeditions.trouver(id);
        if (e == null || e.getStatut() != StatutExpedition.EN_COURS) {
            System.out.println("ERREUR : impossible de livrer l'expédition " + id);
            return false;
        }
        e.setStatut(StatutExpedition.LIVREE);
        libererVehicule(e);
        notificateur.notifier(e.getClient(), new Message("Votre expédition " + e.getId() + " est livrée",
                "Votre expédition est arrivée à " + e.getVilleArrivee() + ". Merci de votre confiance.",
                "TransFret : expédition " + e.getId() + " livrée à " + e.getVilleArrivee()));
        return true;
    }

    public boolean annulerExpedition(String id) {
        Expedition e = expeditions.trouver(id);
        if (e == null || e.getStatut() == StatutExpedition.LIVREE || e.getStatut() == StatutExpedition.ANNULEE) {
            System.out.println("ERREUR : impossible d'annuler l'expédition " + id);
            return false;
        }
        if (e.getStatut() == StatutExpedition.EN_COURS) {
            // frais si déjà partie
            e.setFraisAnnulation(Arrondi.auCentime(e.getPrixTTC() * TAUX_FRAIS_ANNULATION));
        }
        e.setStatut(StatutExpedition.ANNULEE);
        libererVehicule(e);
        notificateur.notifier(e.getClient(), new Message("Annulation de votre expédition " + e.getId(),
                "Votre expédition a été annulée. Frais d'annulation : " + e.getFraisAnnulation() + " €",
                "TransFret : expédition " + e.getId() + " annulée"));
        return true;
    }

    private void libererVehicule(Expedition e) {
        for (Colis c : e.getColis()) {
            e.getVehicule().decharger(c);
        }
        e.getVehicule().setDisponible(true);
    }
}
