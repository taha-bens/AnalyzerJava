package projet.textanalyzer;

import java.util.HashMap;
import java.util.Map;

public class EvaluateurClavier {

    private static final Map<Character, int[]> KEY_COORDINATES = new HashMap<>();

    static {
        // Ligne 1
        String row1 = "azertyuiop";
        for (int i = 0; i < row1.length(); i++) {
            KEY_COORDINATES.put(row1.charAt(i), new int[]{i, 0});
        }

        // Ligne 2
        String row2 = "qsdfghjklm";
        for (int i = 0; i < row2.length(); i++) {
            KEY_COORDINATES.put(row2.charAt(i), new int[]{i, 1});
        }

        // Ligne 3
        String row3 = "wxcvbn,;:!";
        for (int i = 0; i < row3.length(); i++) {
            KEY_COORDINATES.put(row3.charAt(i), new int[]{i, 2});
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

}
