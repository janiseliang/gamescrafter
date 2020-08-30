package gamesToZero;

/** Implementation of 25-to-0-by-1-or-3-or-4 game, where players start with 10 items
 *  and alternate picking 1, 3, or 4 items each turn. The player that takes the
 *  final item wins the game.
 */
public class TwentyFiveGame extends Game {

    public TwentyFiveGame() {
        legalMoves = new int[]{1, 3, 4};
        start = 25;
    }

    public static void main(String[] args) {
        Game g = new TwentyFiveGame();
        Solver s = new Solver(g);
        solve(s);
    }
}
