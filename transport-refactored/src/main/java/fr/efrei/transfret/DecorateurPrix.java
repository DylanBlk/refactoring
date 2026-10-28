package fr.efrei.transfret;

/** Decorator : enveloppe un autre calcul de prix et y ajoute quelque chose. */
public abstract class DecorateurPrix implements CalculPrix {

    protected final CalculPrix enveloppe;

    protected DecorateurPrix(CalculPrix enveloppe) {
        this.enveloppe = enveloppe;
    }
}
