package fr.efrei.transfret;

/** Un moyen de prévenir un client (email, SMS…). */
public interface CanalNotification {

    /** @return true si ce canal doit être utilisé pour ce client */
    boolean concerne(Client client);

    void envoyer(Client client, Message message);
}
