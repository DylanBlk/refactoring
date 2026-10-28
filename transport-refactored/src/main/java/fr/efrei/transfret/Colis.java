package fr.efrei.transfret;

/** Un colis : poids en kg, dimensions en cm, valeur déclarée en euros. */
public class Colis {

    private static final double CM3_PAR_M3 = 1_000_000;

    private final String description;
    private final double poids;
    private final double longueur;
    private final double largeur;
    private final double hauteur;
    private final TypeMarchandise type;
    private final double valeurDeclaree;

    public Colis(String description, double poids, double longueur, double largeur, double hauteur, TypeMarchandise type, double valeurDeclaree) {
        this.description = description;
        this.poids = poids;
        this.longueur = longueur;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.type = type;
        this.valeurDeclaree = valeurDeclaree;
    }

    /** Volume en m3. */
    public double getVolume() {
        return (longueur * largeur * hauteur) / CM3_PAR_M3;
    }

    public String getLibelleType() {
        return type.getLibelle();
    }

    public String getDescription() {
        return description;
    }

    public double getPoids() {
        return poids;
    }

    public double getLongueur() {
        return longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public TypeMarchandise getType() {
        return type;
    }

    public double getValeurDeclaree() {
        return valeurDeclaree;
    }
}
