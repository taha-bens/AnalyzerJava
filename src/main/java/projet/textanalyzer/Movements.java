package projet.textanalyzer;

public enum Movements {
    SFB(0.2), LSB(0.33),
    SCISSORS(0.5), ALTERNATIONS(1),
    INSIDE_ROLLING(4), OUTSIDE_ROLLING(3),
    SKS(0.25), REDIRECTION(0.20), RDR_WITHOUT_INDEX(0.10);

    private final double weight;

    Movements(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
