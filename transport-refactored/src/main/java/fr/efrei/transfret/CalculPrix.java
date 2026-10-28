package fr.efrei.transfret;

/** Interface commune au prix de base et à tous les décorateurs (suppléments, remise, assurance). */
public interface CalculPrix {

    double calculer(CommandeTransport commande);
}
