package projet.textanalyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TextAnalyzer {
    private final int nGramSize;

    /**
     * Constructeur pour initialiser la taille des N-grammes
     *
     * @param nGramSize La taille des N-grammes
     */
    public TextAnalyzer(int nGramSize) {
        if (nGramSize <= 0) {
            throw new IllegalArgumentException("La taille des N-grammes doit être positive");
        }
        this.nGramSize = nGramSize;
    }

    /**
     * Analyse un fichier texte pour compter les fréquences des N-grammes
     *
     * @param filePath Le chemin du fichier texte
     * @return         Une map associant chaque N-gramme à sa fréquence
     * @throws Exception Si une erreur survient lors de la lecture du fichier
     */
    public Map<String, Integer> analyzeFile(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        String content = Files.readString(path);
        return analyzeNGrams(content);
    }

    /**
     * Analyse un texte pour compter les fréquences des N-grammes
     *
     * @param text Le texte à analyser
     * @return     Une map associant chaque N-gramme à sa fréquence
     */
    private Map<String, Integer> analyzeNGrams(String text) {
        Map<String, Integer> nGrams = new HashMap<>();
        for (int i = 0; i <= text.length() - nGramSize; i++) {
            String nGram = text.substring(i, i + nGramSize);
            nGrams.put(nGram, nGrams.getOrDefault(nGram, 0) + 1);
        }
        return nGrams;
    }
}
