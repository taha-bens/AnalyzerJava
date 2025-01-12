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

    public boolean equalHands(FingerInfo f) {
        return f.hand == this.hand;
    }
    public boolean equalHands(FingerInfo f1, FingerInfo f2) {
        return (this.hand == f1.hand) == (this.hand == f2.hand);
    }
    public boolean equalFingers(FingerInfo f) {
        return f.finger == this.finger;
    }


}
