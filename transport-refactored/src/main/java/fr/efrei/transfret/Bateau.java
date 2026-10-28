package fr.efrei.transfret;

import java.util.List;

/** Navire porte-conteneurs : prend du réfrigéré seulement s'il a des conteneurs frigorifiques. */
public class Bateau extends Vehicule implements Ravitaillable, Refrigerable {

    private static final double KM_PAR_MILLE_NAUTIQUE = 1.852;

    private final boolean conteneursRefrigeres;

    public Bateau(String immatriculation, double capaciteMax, boolean conteneursRefrigeres) {
        super(immatriculation, ModeTransport.MER, capaciteMax);
        this.conteneursRefrigeres = conteneursRefrigeres;
    }

    @Override
    protected boolean accepteColis(List<Colis> colis) {
        return conteneursRefrigeres || !contient(colis, TypeMarchandise.REFRIGERE);
    }

    @Override
    public void faireLePlein() {
        System.out.println("Plein effectué pour " + immatriculation);
    }

    @Override
    public void activerRefrigeration(double temperatureC) {
        System.out.println("Conteneurs frigorifiques réglés à " + temperatureC + "°C");
    }

    @Override
    public void partir(int distanceKm) {
        faireLePlein();
        if (contient(chargement, TypeMarchandise.REFRIGERE)) {
            activerRefrigeration(TEMPERATURE_FROID_C);
        }
        int milles = (int) (distanceKm / KM_PAR_MILLE_NAUTIQUE);
        System.out.println("Le navire " + immatriculation + " navigue sur " + milles + " milles nautiques");
    }
}
