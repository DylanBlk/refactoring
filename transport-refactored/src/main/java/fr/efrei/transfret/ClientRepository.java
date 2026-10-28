package fr.efrei.transfret;

/** Où sont rangés les clients. Le métier ne sait pas si c'est une base de données ou une simple liste. */
public interface ClientRepository {

    void sauvegarder(Client client);

    /** @return le client, ou null s'il n'existe pas */
    Client trouverParEmail(String email);
}
