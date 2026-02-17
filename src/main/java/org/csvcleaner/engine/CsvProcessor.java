package org.csvcleaner.engine;

import org.csvcleaner.strategy.CleaningStrategy;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
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

    public CsvProcessor(CleaningStrategy strategy) {
        this.strategy = strategy;
    }

    public void process(File inputFile, File outputFile, Consumer<Integer> progressReporter) throws IOException {

        long totalBytes = inputFile.length();
        long readBytes = 0;

        // 1. Rilevamento Intelligente del Charset
        // Se il file è già UTF-8 valido, usa quello. Altrimenti fallback su Windows-1252.
        Charset inputCharset = detectCharset(inputFile);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile), inputCharset));

             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {

            String line;
            int linesCount = 0;

            while ((line = reader.readLine()) != null) {
                String cleanedLine = strategy.clean(line);

                writer.write(cleanedLine);
                writer.newLine();

                // Calcolo avanzamento (byte stimati)
                readBytes += line.getBytes(inputCharset).length + 1;
                linesCount++;

                if (linesCount % 100 == 0) {
                    int percent = (totalBytes > 0) ? (int) ((readBytes * 100) / totalBytes) : 0;
                    progressReporter.accept(Math.min(percent, 99)); // Cap a 99% finché non finito
                }
            }
            progressReporter.accept(100);
        }
    }

    /**
     * Legge TUTTO il file tentando di interpretarlo come UTF-8.
     * Se trova anche solo un byte non valido, assume che sia Windows-1252.
     * Usa InputStreamReader per gestire correttamente i caratteri "tagliati" tra i buffer.
     */
    private Charset detectCharset(File file) {
        // il decoder lancia eccezioni se trova errori (caratteri non UTF-8)
        var decoder = StandardCharsets.UTF_8.newDecoder();
        decoder.onMalformedInput(CodingErrorAction.REPORT);
        decoder.onUnmappableCharacter(CodingErrorAction.REPORT);

        try (InputStream is = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(is);
             // InputStreamReader gestisce automaticamente i chunk e i caratteri multibyte
             InputStreamReader reader = new InputStreamReader(bis, decoder)) {

            char[] buffer = new char[8192]; // Buffer di lettura al char

            // Legge tutto il file fino alla fine (-1)
            while (reader.read(buffer) != -1) {
                // Non dobbiamo fare nulla con i dati, solo assicurarci che vengano letti.
                // Se c'è un errore, il metodo read() lancerà MalformedInputException.
            }

            // Se siamo arrivati qui, tutto il file è UTF-8 valido
            return StandardCharsets.UTF_8;

        } catch (IOException e) {
            // MalformedInputException (o altri errori I/O) -> Fallback a Windows-1252
            return Charset.forName("windows-1252");
        }
    }
}
