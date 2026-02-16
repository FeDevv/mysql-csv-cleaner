package org.csvcleaner.ui;

import org.csvcleaner.engine.CsvProcessor;
import org.csvcleaner.strategy.NbspCleaningStrategy;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Questa classe è l'entry point (Main) dell'applicazione.
 * Gestisce l'interazione con l'utente tramite terminale.
 * -
 * Responsabilità:
 * 1. Accogliere l'utente.
 * 2. Acquisire il percorso del file (gestendo il Drag & Drop del terminale).
 * 3. Validare l'input (Error Detection).
 * 4. Orchestrare la pulizia tramite il Processor.
 * 5. Gestire gli errori e fornire feedback chiaro (Error Correction/Reporting).
 */
public class ConsoleWizard {

     static void main(String[] args) {
        printBanner();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean appRunning = true;

            while (appRunning) {

                // --- SEZIONE INPUT UTENTE ---
                // 1. Info discreta per uscire (colore default)
                System.out.println("\n(Scrivi 'exit' per uscire)");

                // 2. Istruzione Principale
                System.out.println(ConsoleColors.BLUE_BOLD + "📂 Trascina qui il file CSV da pulire e premi INVIO:" + ConsoleColors.RESET);

                // 3. Prompt
                System.out.print(ConsoleColors.YELLOW + "> " + ConsoleColors.RESET);

                String input = scanner.nextLine().trim();

                // Gestione uscita rapida
                if ("exit".equalsIgnoreCase(input) || "esci".equalsIgnoreCase(input)) {
                    break;
                }

                if (input.isEmpty()) {
                    continue;
                }

                // Pulizia path
                String filePath = input.replace("'", "").replace("\"", "");
                File inputFile = new File(filePath);

                // 4. Validazione
                if (!inputFile.exists() || inputFile.isDirectory()) {
                    printError("Il file non esiste o è una cartella. Riprova.");
                    continue;
                }

                // 5. Setup Output (original_CLEANED.csv)
                String inputPath = inputFile.getAbsolutePath();
                String outputPath = inputPath.substring(0, inputPath.lastIndexOf('.'))
                        + "_CLEANED.csv";
                File outputFile = new File(outputPath);

                // 6. Esecuzione Processo
                try {
                    // Feedback di inizio
                    System.out.println(ConsoleColors.CYAN_BOLD + "\n🚀 Elaborazione in corso..." + ConsoleColors.RESET);
                    System.out.println("   Input:  " + inputFile.getName());

                    CsvProcessor processor = new CsvProcessor(new NbspCleaningStrategy());

                    // Avvio pulizia
                    processor.process(inputFile, outputFile, ConsoleWizard::drawProgressBar);

                    // Successo
                    System.out.println(ConsoleColors.GREEN_BOLD + "\n\n✅ COMPLETATO CON SUCCESSO" + ConsoleColors.RESET);
                    System.out.println("   File pronto: " + outputFile.getName());
                    System.out.println("   Percorso:    " + outputFile.getAbsolutePath());
                    System.out.println("   Puoi importarlo in MySQL (Table Data Import Wizard) senza errori.");

                } catch (IOException e) {
                    printError("Errore I/O: " + e.getMessage());
                } catch (Exception e) {
                    printError("Errore imprevisto: " + e.getMessage());
                    //e.printStackTrace();
                }

                // 5. Richiesta prosecuzione
                appRunning = chiediSeContinuare(scanner);
            }

            System.out.println(ConsoleColors.CYAN_BOLD + "\nヾ(＾-＾)ノ! 👋" + ConsoleColors.RESET);
        }
    }

    // --- Metodi Helper ---

    /**
     * Disegna una barra di avanzamento testuale che si aggiorna sulla stessa riga.
     * Usa il carattere '\r' (Carriage Return) per sovrascrivere la riga corrente.
     */
    private static void drawProgressBar(int percent) {
        StringBuilder bar = new StringBuilder("[");
        int width = 50;
        int completed = (percent * width) / 100;

        for (int i = 0; i < width; i++) {
            if (i < completed) {
                bar.append("=");
            } else if (i == completed) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }
        bar.append("] ").append(percent).append("%");

        System.out.print("\r" + ConsoleColors.CYAN_BOLD + bar + ConsoleColors.RESET);
    }

    private static void printBanner() {
        // Design pulito in Ciano
        System.out.println(ConsoleColors.CYAN_BOLD);
        System.out.println("   MySQL CSV CLEANER TOOL v1.0  ");
        System.out.println("   ---------------------------   ");
        System.out.print(ConsoleColors.RESET);
    }

    /**
     * Gestisce la domanda "Vuoi continuare?"
     */
    private static boolean chiediSeContinuare(Scanner scanner) {
        while (true) {
            System.out.println(ConsoleColors.BLUE_BOLD + "\n🔄 Vuoi pulire un altro file? (s/n)" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW + "> " + ConsoleColors.RESET);

            String risposta = scanner.nextLine().trim().toLowerCase();

            if (risposta.equals("s") || risposta.equals("si") || risposta.equals("y") || risposta.equals("yes")) {
                return true; // Ricomincia il loop principale
            } else if (risposta.equals("n") || risposta.equals("no")) {
                return false; // Esce dal loop principale
            }
            // Se scrive altro, il while(true) interno ripete la domanda
        }
    }

    private static void printError(String msg) {
        System.out.println(ConsoleColors.RED_BOLD + "\n❌ ERRORE: " + msg + ConsoleColors.RESET);
    }

}
