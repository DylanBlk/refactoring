package fr.efrei.transfret;

/**
 * Un message à envoyer au client.
 *
 * @param sujet      sujet de l'email
 * @param corps      texte de l'email
 * @param texteCourt texte du SMS
 */
public record Message(String sujet, String corps, String texteCourt) {
}
