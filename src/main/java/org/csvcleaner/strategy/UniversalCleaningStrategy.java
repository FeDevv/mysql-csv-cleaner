package org.csvcleaner.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Questa strategia applica una pulizia "universale".
 * Converte i caratteri speciali di Windows-1252 (Smart Quotes, Dashes, ecc.)
 * nei loro equivalenti ASCII sicuri per MySQL.
 */
public class UniversalCleaningStrategy implements CleaningStrategy {

    private static final Map<Character, String> REPLACEMENTS = new HashMap<>();

    private long totalFixedCharacters = 0;

    static {
        // --- 1. SPAZI ---
        REPLACEMENTS.put('\u00A0', " ");  // Non-breaking space
        REPLACEMENTS.put('\u2007', " ");  // Figure space
        REPLACEMENTS.put('\u202F', " ");  // Narrow no-break space

        // --- 2. VIRGOLETTE (Smart Quotes) ---
        REPLACEMENTS.put('\u2018', "'");  // Left Single Quote (‘)
        REPLACEMENTS.put('\u2019', "'");  // Right Single Quote (’)
        REPLACEMENTS.put('\u201A', "'");  // Single Low-9 Quote (‚)
        REPLACEMENTS.put('\u201B', "'");  // Single High-Reversed-9 Quote (‛)

        REPLACEMENTS.put('\u201C', "\""); // Left Double Quote (“)
        REPLACEMENTS.put('\u201D', "\""); // Right Double Quote (”)
        REPLACEMENTS.put('\u201E', "\""); // Double Low-9 Quote („)
        REPLACEMENTS.put('\u201F', "\""); // Double High-Reversed-9 Quote (‟)

        // --- 3. TRATTINI (Dashes) ---
        REPLACEMENTS.put('\u2010', "-");  // Hyphen
        REPLACEMENTS.put('\u2011', "-");  // Non-breaking hyphen
        REPLACEMENTS.put('\u2012', "-");  // Figure dash
        REPLACEMENTS.put('\u2013', "-");  // En Dash (–)
        REPLACEMENTS.put('\u2014', "-");  // Em Dash (—)
        REPLACEMENTS.put('\u2015', "-");  // Horizontal bar

        // --- 4. ALTRO ---
        REPLACEMENTS.put('\u2026', "..."); // Ellipsis (…) -> tre punti
    }

    @Override
    public String clean(String line) {
        if (line == null || line.isEmpty()) {
            return line;
        }

        StringBuilder cleanLine = new StringBuilder(line.length());

        // Scansiona la riga carattere per carattere
        for (char c : line.toCharArray()) {
            if (REPLACEMENTS.containsKey(c)) {
                // Se è un carattere "cattivo", appende il sostituto
                totalFixedCharacters++;
                cleanLine.append(REPLACEMENTS.get(c));
            } else {
                // Altrimenti tiene il carattere originale
                cleanLine.append(c);
            }
        }

        return cleanLine.toString();
    }

    public long getTotalFixedCharacters() {
        return totalFixedCharacters;
    }
}
