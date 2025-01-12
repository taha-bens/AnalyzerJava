package projet.textanalyzer;

import java.util.Map;

public interface MovementAnalyzer {
    void analyze(String nGram, int freq, Map<Movements, Integer> movementStats);
}
