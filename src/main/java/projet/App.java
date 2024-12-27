package projet;

import java.util.Map;

import projet.textanalyzer.TextAnalyzer;

public class App {

    public static void main(String[] args) throws Exception {
        int nGramSize = 2;
        String path = "src/main/resources/corpus.txt";
        TextAnalyzer textAnalyzer = new TextAnalyzer(nGramSize);

        Map<String, Integer> nGrams = textAnalyzer.analyzeFile(path);

        nGrams.forEach((nGram, frequency) -> {
            if (frequency > 1500) {
                
                System.out.println(nGram + ": " + frequency);
            }
        });
    }
}
