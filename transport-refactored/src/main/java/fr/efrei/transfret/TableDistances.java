package fr.efrei.transfret;

import java.util.HashMap;
import java.util.Map;

/** Connaît la distance en km entre deux villes, dans les deux sens et sans tenir compte de la casse. */
public class TableDistances {

    public static final int DISTANCE_INCONNUE = -1;

    private final Map<String, Integer> distances = new HashMap<>();

    public static TableDistances standard() {
        TableDistances table = new TableDistances();
        table.ajouter("Paris", "Lyon", 465);
        table.ajouter("Paris", "Marseille", 775);
        table.ajouter("Paris", "Lille", 225);
        table.ajouter("Paris", "Bordeaux", 585);
        table.ajouter("Lyon", "Marseille", 315);
        table.ajouter("Le Havre", "New York", 5850);
        table.ajouter("Marseille", "Alger", 760);
        table.ajouter("Paris", "New York", 5840);
        return table;
    }

    public void ajouter(String villeA, String villeB, int km) {
        distances.put(cle(villeA, villeB), km);
        distances.put(cle(villeB, villeA), km);
    }

    /** @return la distance en km, ou DISTANCE_INCONNUE si le trajet n'est pas dans la table */
    public int getDistance(String villeA, String villeB) {
        return distances.getOrDefault(cle(villeA, villeB), DISTANCE_INCONNUE);
    }

    private static String cle(String villeA, String villeB) {
        return villeA.toUpperCase() + "->" + villeB.toUpperCase();
    }
}
