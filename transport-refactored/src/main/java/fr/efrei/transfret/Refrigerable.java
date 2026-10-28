package fr.efrei.transfret;

/** Un véhicule capable de garder des marchandises au froid. */
public interface Refrigerable {

    double TEMPERATURE_FROID_C = 4;

    void activerRefrigeration(double temperatureC);
}
