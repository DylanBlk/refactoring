package fr.efrei.transfret;

/** Le type de marchandise d'un colis. Le libellé sert à l'affichage (facture). */
public enum TypeMarchandise {
    STANDARD("Standard"),
    FRAGILE("Fragile"),
    DANGEREUX("Dangereux"),
    REFRIGERE("Réfrigéré");

    private final String libelle;

    TypeMarchandise(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
