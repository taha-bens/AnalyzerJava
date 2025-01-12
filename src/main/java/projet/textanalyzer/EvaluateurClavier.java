package projet.textanalyzer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EvaluateurClavier {
    private static final ClavierLayout layout = ClavierLayout.getInstance();

    /**
     * Calcule la distance totale pour taper un mot.
     * 
     * @param word Le mot à évaluer.
     * @return La distance totale de frappe.
     */
    public static double calculateTypingDistance(String word) {
        if (word == null || word.length() < 2) {
            return 0.0;
        }
        double totalDistance = 0.0;

        for (int i = 0; i < word.length() - 1; i++) {
            char current = word.charAt(i);
            char next = word.charAt(i + 1);

            int[] currentPos = layout.getCoordinates(current);
            int[] nextPos = layout.getCoordinates(next);
            if (currentPos == null || nextPos == null) {
                System.out.println("Caractère non reconnu: " + current);
            }
            if (currentPos != null && nextPos != null) {
                // Calcul de la distance euclidienne
                double distance = Math.sqrt(
                    Math.pow(nextPos[0] - currentPos[0], 2) +
                    Math.pow(nextPos[1] - currentPos[1], 2)
                );
                totalDistance += distance;
            }
        }

        return totalDistance;
    }

    public double calculateScore(Map<Movements, Integer> movementStats) {
        return movementStats.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getWeight() * entry.getValue()).sum();
    }

    // analyseur de mouvements avec les n-grammes donnés
    public Map<Movements, Integer> analyze(Map<String, Integer> nGrams) {
        // Map avec les différents mouvements analysés et leurs occurrences.
        Map<Movements, Integer> movementStats = new HashMap<>();

        for (Map.Entry<String, Integer> entry : nGrams.entrySet()) {
            // prend le ngramme et sa fréquence
            String nGram = entry.getKey();
            int freq = entry.getValue();


            if (nGram.length() != 2 && nGram.length() != 3) {
                continue;
            }

            if (!layout.getContainsKey(nGram.charAt(0)) || !layout.getContainsKey(nGram.charAt(1))) {
                continue;

            }

            // on utilise une interface MovementAnalyzer
            MovementAnalyzer analyzer = null;
            if (nGram.length() == 2) {
                analyzer = new BigramAnalyzer();
            } else {
                if (!layout.getContainsKey(nGram.charAt(2))) break;
                analyzer = new TrigramAnalyzer();
            }

            analyzer.analyze(nGram, freq, movementStats);
        }
        return movementStats;
    }
}
