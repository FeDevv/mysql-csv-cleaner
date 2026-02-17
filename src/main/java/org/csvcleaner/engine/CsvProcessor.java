package org.csvcleaner.engine;

import org.csvcleaner.strategy.CleaningStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * Questa classe gestisce il flusso I/O (Input/Output).
 * La sua responsabilità è aprire il file in lettura con la codifica corretta (Windows-1252)
 * e scriverlo in uscita con la codifica moderna (UTF-8).
 * -
 * Non sa COSA viene pulito, delega questo compito alla CleaningStrategy.
 */
public class CsvProcessor {

    private final CleaningStrategy strategy;

    // Dependency Injection
    public CsvProcessor(CleaningStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Esegue la conversione del file.
     *
     * @param inputFile  Il file sorgente (sporco).
     * @param outputFile Il file destinazione (pulito).
     * @param progressReporter Una callback per notificare la percentuale di avanzamento alla UI.
     * @throws IOException Se ci sono errori di lettura/scrittura.
     */
    public void process(File inputFile, File outputFile, Consumer<Integer> progressReporter) throws IOException {

        long totalBytes = inputFile.length();
        long readBytes = 0;

        // 1. Apro il file in lettura forzando Windows-1252 (o ISO-8859-1).
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile), "windows-1252"));

             // 2. Apro il file in scrittura forzando UTF-8.
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {

            String line;
            int linesCount = 0;

            while ((line = reader.readLine()) != null) {
                // Delega la pulizia alla strategia
                String cleanedLine = strategy.clean(line);

                // Scrivi la riga pulita
                writer.write(cleanedLine);
                writer.newLine();

                // Calcolo avanzamento (approssimativo basato sulla lunghezza della riga)
                readBytes += line.length() + 1; // +1 per il carattere a capo
                linesCount++;

                // aggiorna la barra di avanzamento ogni 100 righe per non rallentare troppo
                if (linesCount % 100 == 0) {
                    int percent = (int) ((readBytes * 100) / totalBytes);
                    progressReporter.accept(percent);
                }
            }

            // Notifica finale al 100%
            progressReporter.accept(100);
        }
    }
}
