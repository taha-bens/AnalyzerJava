package projet.textanalyzer;

import java.util.HashMap;
import java.util.Map;

public class ClavierLayout {

    private static ClavierLayout instance;
    private final Map<Character, int[]> keyCoordinates = new HashMap<>();
    private final Map<Character, FingerInfo> keyHand = new HashMap<>();

    private ClavierLayout() {
        initializeLayout();
    }

    public static ClavierLayout getInstance() {
        if (instance == null) {
            instance = new ClavierLayout();
        }
        return instance;
    }

    private void initializeLayout() {
        String[] rows = {"azertyuiop", "qsdfghjklm", "wxcvbn,;:!"};
        FingerInfo[] fingers = {new FingerInfo(4, 'L'), new FingerInfo(3, 'L'), new FingerInfo(2, 'L'),
                new FingerInfo(1, 'L'), new FingerInfo(1, 'L'), new FingerInfo(1, 'R'),
                new FingerInfo(1, 'R'), new FingerInfo(2, 'R'), new FingerInfo(3, 'R'),
                new FingerInfo(4, 'R')};

        for (int row = 0; row < rows.length; row++) {
            for (int col = 0; col < rows[row].length(); col++) {
                char key = rows[row].charAt(col);
                keyCoordinates.put(key, new int[]{col, row});
                keyHand.put(key, fingers[col]);
            }
        }
    }

    public int[] getCoordinates(char key) { return keyCoordinates.get(key); }
    public boolean getContainsKey(char key) { return keyCoordinates.containsKey(key); }
    public FingerInfo getFingerInfo(char key) { return keyHand.get(key); }
}
