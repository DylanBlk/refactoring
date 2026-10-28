package fr.efrei.transfret;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Mise en forme des dates au format jj/mm/aaaa. */
public final class FormatDate {

    private static final DateTimeFormatter JJ_MM_AAAA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private FormatDate() {
    }

    public static String formater(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(JJ_MM_AAAA);
    }
}
