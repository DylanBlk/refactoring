package fr.efrei.transfret;

import java.util.List;

/**
 * Le seul endroit qui construit les objets de l'application et les branche entre eux.
 * Construire une nouvelle Application = repartir de zéro (flotte neuve, aucun client, aucune expédition).
 */
public class Application {

    private final ClientRepository clients = new ClientRepositoryMemoire();
    private final ExpeditionRepository expeditions = new ExpeditionRepositoryMemoire();
    private final CanalEmail email = new CanalEmail();
    private final CanalSms sms = new CanalSms();
    private final GenerateurFacture generateurFacture = new GenerateurFacture();
    private final ServiceClient serviceClient;
    private final ServiceExpedition serviceExpedition;

    /** L'application avec les tarifs historiques. */
    public Application() {
        this(CalculateurPrix.tarifsHistoriques());
    }

    /** L'application avec le calculateur de prix choisi (par exemple avec le supplément hors gabarit). */
    public Application(CalculateurPrix calculateurPrix) {
        Flotte flotte = Flotte.initiale();
        Notificateur notificateur = new Notificateur(List.of(email, sms));
        serviceClient = new ServiceClient(new ValidateurClient(), clients, email);
        serviceExpedition = new ServiceExpedition(expeditions, new ValidateurExpedition(), TableDistances.standard(),
                new AffectationVehicule(flotte), calculateurPrix, new EstimateurDelai(),
                notificateur, new GenerateurIdentifiant());
    }

    public ClientRepository getClients() {
        return clients;
    }

    public ExpeditionRepository getExpeditions() {
        return expeditions;
    }

    public CanalEmail getEmail() {
        return email;
    }

    public CanalSms getSms() {
        return sms;
    }

    public GenerateurFacture getGenerateurFacture() {
        return generateurFacture;
    }

    public ServiceClient getServiceClient() {
        return serviceClient;
    }

    public ServiceExpedition getServiceExpedition() {
        return serviceExpedition;
    }
}
