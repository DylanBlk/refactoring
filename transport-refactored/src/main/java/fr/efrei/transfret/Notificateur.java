package fr.efrei.transfret;

import java.util.List;

/** Prévient le client sur chacun des canaux qui le concernent. */
public class Notificateur {

    private final List<CanalNotification> canaux;

    public Notificateur(List<CanalNotification> canaux) {
        this.canaux = canaux;
    }

    public void notifier(Client client, Message message) {
        for (CanalNotification canal : canaux) {
            if (canal.concerne(client)) {
                canal.envoyer(client, message);
            }
        }
    }
}
