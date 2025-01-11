package projet.textanalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NGram {



    /**
     * Analyse un texte pour compter les fréquences des N-grammes
     *
     * @param text Le texte à analyser
     * @return     Une map associant chaque N-gramme à sa fréquence
     */
    public static Map<String, Integer> analyzeNGrams(String text, int nGramSize) {
        Map<String, Integer> nGrams = new HashMap<>();
        for (int i = 0; i <= text.length() - nGramSize; i++) {
            // si ca contient un espace on passe au ngramme suivant
            if (text.substring(i, i + nGramSize).contains(" ")) {
                continue;
            }
            String nGram = text.substring(i, i + nGramSize);
            nGrams.put(nGram, nGrams.getOrDefault(nGram, 0) + 1);
        }
        return nGrams;
    }

}
