package projet.textanalyzer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EvaluateurClavier {

    private static final Map<Character, int[]> KEY_COORDINATES = new HashMap<>();
    private static final Map<Character, FingerInfo> KEY_HAND = new HashMap<>();
    private static final FingerInfo[] fingers = {new FingerInfo(4, 'L'),
        new FingerInfo(3, 'L'), new FingerInfo(2, 'L'), new FingerInfo(1, 'L'),
        new FingerInfo(1, 'L'), new FingerInfo(1, 'R'), new FingerInfo(1, 'R'),
        new FingerInfo(2, 'R'), new FingerInfo(3, 'R'), new FingerInfo(4, 'R')};

    static {
        // Ligne 1
        String row1 = "azertyuiop";
        for (int i = 0; i < row1.length(); i++) {
            KEY_COORDINATES.put(row1.charAt(i), new int[]{i, 0});
            KEY_HAND.put(row1.charAt(i), fingers[i]);
        }

        // Ligne 2
        String row2 = "qsdfghjklm";
        for (int i = 0; i < row2.length(); i++) {
            KEY_COORDINATES.put(row2.charAt(i), new int[]{i, 1});
            KEY_HAND.put(row2.charAt(i), fingers[i]);
        }

        // Ligne 3
        String row3 = "wxcvbn,;:!";
        for (int i = 0; i < row3.length(); i++) {
            KEY_COORDINATES.put(row3.charAt(i), new int[]{i, 2});
            KEY_HAND.put(row3.charAt(i), fingers[i]);
        }
    }

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

            int[] currentPos = KEY_COORDINATES.get(current);
            int[] nextPos = KEY_COORDINATES.get(next);
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

    // analyseur de mouvements avec les n-grammes donnés
    public Map<Movements, Integer> analyze(Map<String, Integer> nGrams) {
        // Map avec les différents mouvements analysés et leurs occurrences.
        Map<Movements, Integer> movementStats = new HashMap<>();

        for (Map.Entry<String, Integer> entry : nGrams.entrySet()) {
            // prend le ngramme et sa fréquence
            String nGram = entry.getKey();
            int freq = entry.getValue();

            //TEST SUR BIGRAMMES UNIQUEMENT POUR LE MOMENT
            if (nGram.length() != 2) {
                continue;
            }

            // disposition pour bigrammes uniquement
            char fst = nGram.charAt(0);
            char snd = nGram.charAt(1);
            // si les caracteres ne sont pas reconnus → ignorer
            if (!KEY_COORDINATES.containsKey(fst) || !KEY_COORDINATES.containsKey(snd)) continue;

            FingerInfo f1 = KEY_HAND.get(fst);
            FingerInfo f2 = KEY_HAND.get(snd);

            if (f1.equals(f2))
                movementStats.put(Movements.SFB, movementStats.getOrDefault(Movements.SFB, 0) + freq);

            else if (f1.getHand() == f2.getHand() && KEY_COORDINATES.get(fst)[1] != KEY_COORDINATES.get(snd)[1])
                movementStats.put(Movements.SCISSORS, movementStats.getOrDefault(Movements.SCISSORS, 0) + freq);

            // manque mouvements LBS et roulements
            else if (f1.getHand() != f2.getHand())
                movementStats.put(Movements.ALTERNATIONS, movementStats.getOrDefault(Movements.ALTERNATIONS, 0) + freq);

        }


        return movementStats;
    }

}
