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


            if (nGram.length() != 2 && nGram.length() != 3) {
                continue;
            }

            if (!KEY_COORDINATES.containsKey(nGram.charAt(0)) || !KEY_COORDINATES.containsKey(nGram.charAt(1))) {
                continue;
            }

            if (nGram.length() == 3) {
                if (KEY_COORDINATES.containsKey(nGram.charAt(2))) break;
                // coupe le trigramme en deux bigrammes
                /*String fstBiGram = nGram.substring(0,2);
                String sndBiGram = nGram.substring(1,3);

                analyzeBiGram(fstBiGram, freq, movementStats);
                analyzeBiGram(sndBiGram, freq, movementStats);*/
                analyzeTriGram(nGram, freq, movementStats);
            } else {
                analyzeBiGram(nGram, freq, movementStats);
            }
        }
        return movementStats;
    }

    public void analyzeTriGram(String triGram, int freq, Map<Movements, Integer> movementStats) {
        char fst = triGram.charAt(0);
        char snd = triGram.charAt(1);
        char trd = triGram.charAt(2);

        FingerInfo f1 = KEY_HAND.get(fst);
        FingerInfo f2 = KEY_HAND.get(snd);
        FingerInfo f3 = KEY_HAND.get(trd);

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
    public void analyzeBiGram(String biGram, int freq, Map<Movements, Integer> movementStats) {
        // disposition pour bigrammes uniquement
        char fst = biGram.charAt(0);
        char snd = biGram.charAt(1);

        FingerInfo f1 = KEY_HAND.get(fst);
        FingerInfo f2 = KEY_HAND.get(snd);

        // SFB : enchainement de deux touches avec le même doigt
        if (f1.equalHands(f2) && f1.equalFingers(f2))
            movementStats.put(Movements.SFB, movementStats.getOrDefault(Movements.SFB, 0) + freq);

        // LSB : enchainement de deux touches du même doigt sur deux rangées différentes
        if (f1.equalHands(f2) && f1.equalFingers(f2) && KEY_COORDINATES.get(fst)[1] != KEY_COORDINATES.get(snd)[1])
            movementStats.put(Movements.LSB, movementStats.getOrDefault(Movements.LSB, 0) + freq);

        // Ciseaux : enchainement de deux touches de la même main sur deux rangées différentes
        if (f1.equalHands(f2) && KEY_COORDINATES.get(fst)[1] != KEY_COORDINATES.get(snd)[1])
            movementStats.put(Movements.SCISSORS, movementStats.getOrDefault(Movements.SCISSORS, 0) + freq);

        // Alternances : mouvement de deux touches des deux mains (main droite ensuite gauche ou main gauche ensuite droite)
        if (!f1.equalHands(f2))
            movementStats.put(Movements.ALTERNATIONS, movementStats.getOrDefault(Movements.ALTERNATIONS, 0) + freq);

        // Roulement intérieur : mouvement de deux touches de deux doigts qui vont de l'auriculaire vers l'index
        if (f1.equalHands(f2) && f1.getFinger() > f2.getFinger())
            movementStats.put(Movements.INSIDE_ROLLING, movementStats.getOrDefault(Movements.INSIDE_ROLLING, 0) + freq);

        // Roulement extérieur : même principe que le roulement intérieur, mais de l'index vers l'auriculaire (un peu moins confortable)
        if (f1.equalHands(f2) && f1.getFinger() < f2.getFinger())
            movementStats.put(Movements.OUTSIDE_ROLLING, movementStats.getOrDefault(Movements.OUTSIDE_ROLLING, 0) + freq);
    }

}
