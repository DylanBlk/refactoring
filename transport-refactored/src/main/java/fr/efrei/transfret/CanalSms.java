package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

/** Envoi des SMS (simulé sur la console). Réservé aux clients qui y ont droit (PREMIUM). */
public class CanalSms implements CanalNotification {

    private static final int LONGUEUR_MAX_SMS = 160;
    private static final String SUITE = "...";

    private final List<MessageEnvoye> envoyes = new ArrayList<>();

    @Override
    public boolean concerne(Client client) {
        return client.getType().isRecoitSms();
    }

    @Override
    public void envoyer(Client client, Message message) {
        String texte = message.texteCourt();
        if (texte.length() > LONGUEUR_MAX_SMS) {
            texte = texte.substring(0, LONGUEUR_MAX_SMS - SUITE.length()) + SUITE;
        }
        System.out.println("[SMS] -> " + client.getTelephone() + " : " + texte);
        envoyes.add(new MessageEnvoye(client.getTelephone(), texte));
    }

    /** Journal des SMS envoyés (facturés par l'opérateur). */
    public List<MessageEnvoye> getEnvoyes() {
        return envoyes;
    }
}
