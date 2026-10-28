package fr.efrei.transfret;

import fr.efrei.transport.contrat.Canal;
import fr.efrei.transport.contrat.ColisDto;
import fr.efrei.transport.contrat.DemandeClient;
import fr.efrei.transport.contrat.DemandeDevis;
import fr.efrei.transport.contrat.DemandeExpedition;
import fr.efrei.transport.contrat.EtatExpedition;
import fr.efrei.transport.contrat.Notification;
import fr.efrei.transport.contrat.Statut;
import fr.efrei.transport.contrat.SystemeTransport;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Branche le nouveau code sur les tests de contrat.
 * Il ne fait que de la traduction : types du contrat <-> types de TransFret.
 */
public class NouveauSystemeAdapter implements SystemeTransport {

    private Application application;

    @Override
    public void reinitialiser() {
        application = new Application();
    }

    @Override
    public Optional<String> creerClient(DemandeClient d) {
        Client client = application.getServiceClient().creerClient(d.nom(), d.email(), d.telephone(), d.adresse(),
                TypeClient.valueOf(d.type().name()), d.siret());
        return Optional.ofNullable(client).map(Client::getEmail);
    }

    @Override
    public Optional<String> creerExpedition(DemandeExpedition d) {
        Client client = application.getClients().trouverParEmail(d.refClient());
        Expedition expedition = application.getServiceExpedition().creerExpedition(client, versColis(d.colis()),
                d.villeDepart(), d.villeArrivee(), ModeTransport.valueOf(d.mode().name()), d.urgent(), d.assurance());
        return Optional.ofNullable(expedition).map(Expedition::getId);
    }

    @Override
    public Optional<EtatExpedition> consulter(String id) {
        Expedition e = application.getExpeditions().trouver(id);
        if (e == null) {
            return Optional.empty();
        }
        int delai = (int) ChronoUnit.DAYS.between(e.getDateCreation(), e.getDateLivraisonPrevue());
        return Optional.of(new EtatExpedition(e.getId(), Statut.valueOf(e.getStatut().name()), e.getPrixHT(),
                e.getPrixTTC(), delai, e.getVehicule().getImmatriculation(), e.getFraisAnnulation()));
    }

    @Override
    public OptionalDouble devisTTC(DemandeDevis d) {
        double devis = application.getServiceExpedition().calculerDevis(versColis(d.colis()), d.villeDepart(),
                d.villeArrivee(), ModeTransport.valueOf(d.mode().name()), d.urgent(), d.assurance(),
                TypeClient.valueOf(d.typeClient().name()));
        return devis == TableDistances.DISTANCE_INCONNUE ? OptionalDouble.empty() : OptionalDouble.of(devis);
    }

    @Override
    public boolean demarrer(String id) {
        return application.getServiceExpedition().demarrerExpedition(id);
    }

    @Override
    public boolean livrer(String id) {
        return application.getServiceExpedition().livrerExpedition(id);
    }

    @Override
    public boolean annuler(String id) {
        return application.getServiceExpedition().annulerExpedition(id);
    }

    @Override
    public Optional<String> facture(String id) {
        return Optional.ofNullable(application.getExpeditions().trouver(id))
                .map(application.getGenerateurFacture()::generer);
    }

    @Override
    public List<Notification> notifications() {
        List<Notification> notifications = new ArrayList<>();
        for (MessageEnvoye email : application.getEmail().getEnvoyes()) {
            notifications.add(new Notification(Canal.EMAIL, email.destinataire(), email.objet()));
        }
        for (MessageEnvoye sms : application.getSms().getEnvoyes()) {
            notifications.add(new Notification(Canal.SMS, sms.destinataire(), sms.objet()));
        }
        return notifications;
    }

    private static List<Colis> versColis(List<ColisDto> colis) {
        List<Colis> resultat = new ArrayList<>();
        for (ColisDto c : colis) {
            resultat.add(new Colis(c.description(), c.poidsKg(), c.longueurCm(), c.largeurCm(), c.hauteurCm(),
                    TypeMarchandise.valueOf(c.type().name()), c.valeurDeclaree()));
        }
        return resultat;
    }
}
