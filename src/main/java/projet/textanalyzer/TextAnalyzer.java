package projet.textanalyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TextAnalyzer {

   

    /**
     * Analyse un fichier texte pour compter les fréquences des N-grammes
     *
     * @param filePath Le chemin du fichier texte
     * @return Une map associant chaque N-gramme à sa fréquence
     * @throws Exception Si une erreur survient lors de la lecture du fichier
     */
    public Map<String, Integer> analyzeFile(String filePath, int nGramSize) throws Exception {
        return NGram.analyzeNGrams(FileLoader.loadFile(filePath), nGramSize);
    }


}
