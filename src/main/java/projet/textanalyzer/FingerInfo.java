package projet.textanalyzer;

public class FingerInfo {

    private final int finger;
    private final char hand;

    public FingerInfo(int f, char h) {
        this.finger = f;
        this.hand = h;
    }
    public int getFinger() { return finger; }
    public char getHand() { return hand; }
    public String toString() { return finger + "" + hand; }

}
