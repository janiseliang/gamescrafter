package gamesToZero;

/** Implements 10-to-0-by-1-or-2. Players start with 10 items
 *  and alternate picking 1 or 2 items. The player that takes the
 *  final item wins the game.
 */
public class TenGame extends GameToZero {

    public TenGame() {
        legalMoves = new int[]{1, 2};
        start = 10;
    }

    public static void main(String[] args) {
        GameToZero g = new TenGame();
        Solver s = new Solver(g);
        solve(s);
    }
}
