package fr.efrei.transfret;

/** Crée les clients : valide la demande, enregistre le client, lui souhaite la bienvenue. */
public class ServiceClient {

    private final ValidateurClient validateur;
    private final ClientRepository clients;
    private final CanalNotification canalBienvenue;

    public ServiceClient(ValidateurClient validateur, ClientRepository clients, CanalNotification canalBienvenue) {
        this.validateur = validateur;
        this.clients = clients;
        this.canalBienvenue = canalBienvenue;
    }

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        if (!validateur.estValide(nom, email, type, siret)) {
            return null;
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        clients.sauvegarder(c);
        canalBienvenue.envoyer(c, new Message("Bienvenue chez TransFret",
                "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.", null));
        return c;
    }
}
