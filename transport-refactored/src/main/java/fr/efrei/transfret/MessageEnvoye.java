package fr.efrei.transfret;

/** Trace d'un message envoyé : à qui, et quoi (sujet de l'email ou texte du SMS). */
public record MessageEnvoye(String destinataire, String objet) {
}
