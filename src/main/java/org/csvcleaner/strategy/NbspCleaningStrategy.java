package org.csvcleaner.strategy;

/**
 * Implementazione concreta della Strategy.
 * Questa classe risolve specificamente l'errore "ascii codec can't decode byte 0xa0".
 * Il byte 0xa0 in Latin1/Windows-1252 è il NO-BREAK SPACE (\u00A0 in Unicode).
 */
public class NbspCleaningStrategy implements CleaningStrategy {

    @Override
    public String clean(String dirtyLine) {
        if (dirtyLine == null) {
            return null;
        }

        // Sostituisce il carattere Unicode \u00A0 (che corrisponde al byte 0xA0 problematico)
        // con uno spazio normale (ASCII 32).
        return dirtyLine.replace('\u00A0', ' ');
    }
}
