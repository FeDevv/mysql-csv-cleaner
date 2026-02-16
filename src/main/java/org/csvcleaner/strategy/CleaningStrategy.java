package org.csvcleaner.strategy;

/**
 * Interfaccia Strategy (GoF Pattern).
 * Definisce il contratto per qualsiasi algoritmo di pulizia delle righe.
 * In questo modo il "motore" che legge il file non deve sapere CHE TIPO di pulizia facciamo,
 * sa solo che deve chiamare questo metodo.
 */
public interface CleaningStrategy {

    /**
     * Pulisce una singola riga di testo.
     *
     * @param dirtyLine La riga "sporca" letta dal file originale.
     * @return La riga "pulita" pronta per essere scritta.
     */
    String clean(String dirtyLine);
}
