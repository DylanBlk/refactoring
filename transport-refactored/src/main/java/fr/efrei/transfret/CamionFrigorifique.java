package fr.efrei.transfret;

import java.util.List;

/**
 * Camion frigorifique : ne prend que des colis réfrigérés.
 * Il ne refuse plus rien au chargement (ce qui violait Liskov) : il le dit avant, dans accepteColis.
 */
public class CamionFrigorifique extends Camion implements Refrigerable {

    public CamionFrigorifique(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, false);
    }

    @Override
    protected boolean accepteColis(List<Colis> colis) {
        for (Colis c : colis) {
            if (c.getType() != TypeMarchandise.REFRIGERE) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void activerRefrigeration(double temperatureC) {
        System.out.println("Réfrigération activée à " + temperatureC + "°C sur " + immatriculation);
    }

    @Override
    public void partir(int distanceKm) {
        activerRefrigeration(TEMPERATURE_FROID_C);
        super.partir(distanceKm);
    }
}
