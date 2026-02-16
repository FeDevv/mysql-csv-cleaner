package org.csvcleaner.ui;

/**
 * Utility per gestire i colori ANSI nel terminale.
 * Funziona nativamente su macOS, Linux e Windows 10/11.
 */
public class ConsoleColors {
    public static final String RESET = "\033[0m";  // Colore di default del terminale

    // Color Palette
    public static final String CYAN_BOLD = "\033[1;36m";  // Banner / Titoli
    public static final String BLUE_BOLD = "\033[1;34m";  // Istruzioni utente
    public static final String YELLOW = "\033[0;33m";     // Prompt Input (>)
    public static final String GREEN_BOLD = "\033[1;32m"; // Successo
    public static final String RED_BOLD = "\033[1;31m";   // Errore critico
}
