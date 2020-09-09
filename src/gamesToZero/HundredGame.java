package gamesToZero;

public class HundredGame extends GameToZero {
    public HundredGame() {
        legalMoves = new int[]{1, 2};
        start = 100;
    }

    public static void main(String[] args) {
        GameToZero g = new HundredGame();
        Solver s = new Solver(g);
        solve(s);
    }
}
