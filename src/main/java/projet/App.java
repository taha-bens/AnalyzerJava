package projet;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import projet.textanalyzer.EvaluateurClavier;
import projet.textanalyzer.Movements;
import projet.textanalyzer.TextAnalyzer;

public class App {

    public static void main(String[] args) throws Exception {
        int nGramSize = 2;
        String path = "src/main/resources/corpus.txt";
        TextAnalyzer textAnalyzer = new TextAnalyzer();
        EvaluateurClavier clavier = new EvaluateurClavier();
        Map<String, Integer> nGrams = textAnalyzer.analyzeFile(path, nGramSize);
        Map<Movements, Integer> moves = clavier.analyze(nGrams);

        // Print sorted n-grams
        nGrams.forEach((key, value) -> {
            System.out.printf("%s: %d  ", key, value);
            System.out.println(EvaluateurClavier.calculateTypingDistance(key));
        }
        );

        System.out.println();
        // afficher mouvements + occurrences
        moves.forEach((key, val) -> {
            System.out.println("Mouvement : " + key + ", occurrence du mouvement : " + val);
        });
       
    }
}
