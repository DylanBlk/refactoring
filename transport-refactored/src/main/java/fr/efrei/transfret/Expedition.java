package fr.efrei.transfret;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Une expédition : ce qui est transporté, d'où, vers où, comment, et où elle en est. */
public class Expedition {

    private String id;
    private Client client;
    private List<Colis> colis = new ArrayList<>();
    private String villeDepart;
    private String villeArrivee;
    private int distance;
    private ModeTransport mode;
    private boolean urgent;
    private boolean assurance;
    private StatutExpedition statut;
    private LocalDate dateCreation;
    private LocalDate dateLivraisonPrevue;
    private Vehicule vehicule;
    private double prixHT;
    private double prixTTC;
    private double fraisAnnulation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Colis> getColis() {
        return colis;
    }

    public void setColis(List<Colis> colis) {
        this.colis = colis;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(String villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public ModeTransport getMode() {
        return mode;
    }

    public void setMode(ModeTransport mode) {
        this.mode = mode;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public boolean isAssurance() {
        return assurance;
    }

    public void setAssurance(boolean assurance) {
        this.assurance = assurance;
    }

    public StatutExpedition getStatut() {
        return statut;
    }

    public void setStatut(StatutExpedition statut) {
        this.statut = statut;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateLivraisonPrevue() {
        return dateLivraisonPrevue;
    }

    public void setDateLivraisonPrevue(LocalDate dateLivraisonPrevue) {
        this.dateLivraisonPrevue = dateLivraisonPrevue;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }

    public double getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(double prixHT) {
        this.prixHT = prixHT;
    }

    public double getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(double prixTTC) {
        this.prixTTC = prixTTC;
    }

    public double getFraisAnnulation() {
        return fraisAnnulation;
    }

    public void setFraisAnnulation(double fraisAnnulation) {
        this.fraisAnnulation = fraisAnnulation;
    }
}
