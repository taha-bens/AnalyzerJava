package projet.textanalyzer;

import java.util.Map;

public class BigramAnalyzer implements MovementAnalyzer {

    private final ClavierLayout layout = ClavierLayout.getInstance();
    @Override
    public void analyze(String biGram, int freq, Map<Movements, Integer> movementStats) {
        char fst = biGram.charAt(0);
        char snd = biGram.charAt(1);

        FingerInfo f1 = layout.getFingerInfo(fst);
        FingerInfo f2 = layout.getFingerInfo(snd);

        // SFB : enchainement de deux touches avec le même doigt
        if (f1.equalHands(f2) && f1.equalFingers(f2))
            movementStats.put(Movements.SFB, movementStats.getOrDefault(Movements.SFB, 0) + freq);

        // LSB : enchainement de deux touches du même doigt sur deux rangées différentes
        if (f1.equalHands(f2) && f1.equalFingers(f2) && layout.getCoordinates(fst)[1] != layout.getCoordinates(snd)[1])
            movementStats.put(Movements.LSB, movementStats.getOrDefault(Movements.LSB, 0) + freq);

        // Ciseaux : enchainement de deux touches de la même main sur deux rangées différentes
        if (f1.equalHands(f2) && layout.getCoordinates(fst)[1] != layout.getCoordinates(snd)[1])
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
