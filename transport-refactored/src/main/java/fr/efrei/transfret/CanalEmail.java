package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

/** Envoi des emails (simulé sur la console). Tous les clients reçoivent des emails. */
public class CanalEmail implements CanalNotification {

    private static final String EXPEDITEUR = "noreply@transfret.fr";

    private final List<MessageEnvoye> envoyes = new ArrayList<>();

    @Override
    public boolean concerne(Client client) {
        return true;
    }

    @Override
    public void envoyer(Client client, Message message) {
        System.out.println("[EMAIL] " + EXPEDITEUR + " -> " + client.getEmail() + " | " + message.sujet());
        System.out.println("        " + message.corps());
        envoyes.add(new MessageEnvoye(client.getEmail(), message.sujet()));
    }

    /** Journal des emails envoyés (demandé par le support client). */
    public List<MessageEnvoye> getEnvoyes() {
        return envoyes;
    }
}
