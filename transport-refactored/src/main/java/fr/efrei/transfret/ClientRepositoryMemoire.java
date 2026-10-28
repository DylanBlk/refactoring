package fr.efrei.transfret;

import java.util.HashMap;
import java.util.Map;

/** Stockage des clients en mémoire. */
public class ClientRepositoryMemoire implements ClientRepository {

    private final Map<String, Client> clients = new HashMap<>();

    @Override
    public void sauvegarder(Client client) {
        clients.put(client.getEmail(), client);
    }

    @Override
    public Client trouverParEmail(String email) {
        return clients.get(email);
    }
}
