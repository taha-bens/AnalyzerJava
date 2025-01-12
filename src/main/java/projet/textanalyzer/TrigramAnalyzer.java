package projet.textanalyzer;

import java.util.Map;

public class TrigramAnalyzer implements MovementAnalyzer {

    private final ClavierLayout layout = ClavierLayout.getInstance();
    @Override
    public void analyze(String triGram, int freq, Map<Movements, Integer> movementStats) {
        char fst = triGram.charAt(0);
        char snd = triGram.charAt(1);
        char trd = triGram.charAt(2);

        FingerInfo f1 = layout.getFingerInfo(fst);
        FingerInfo f2 = layout.getFingerInfo(snd);
        FingerInfo f3 = layout.getFingerInfo(trd);

        if (f1.equalHands(f3) && f1.equalFingers(f3) && !f2.equalHands(f1))
            movementStats.put(Movements.SKS, movementStats.getOrDefault(Movements.SKS, 0) + freq);

        if (f1.equalHands(f2, f3) && ((f1.getFinger() < f2.getFinger() && f2.getFinger() > f3.getFinger()) || (f1.getFinger() > f2.getFinger() && f2.getFinger() < f3.getFinger()))) {
            if (f1.getFinger() != 1 && f2.getFinger() != 1 && f3.getFinger() != 1) {
                movementStats.put(Movements.RDR_WITHOUT_INDEX, movementStats.getOrDefault(Movements.RDR_WITHOUT_INDEX, 0) + freq);
            } else {
                movementStats.put(Movements.REDIRECTION, movementStats.getOrDefault(Movements.REDIRECTION, 0) + freq);
            }
        }
    }
}
