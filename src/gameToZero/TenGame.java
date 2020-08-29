package gameToZero;
import java.util.ArrayList;
import gameToZero.Game.*;

/** Implementation of 10-to-0-by-1-or-2 game, where players start with 10 items
 *  and alternate picking 1 or 2 items each turn. The player that takes the
 *  final item wins the game.
 *  @author janise
 */
public class TenGame extends Game {

    public TenGame() {
        legalMoves = new int[]{1, 2};
        start = 10;
    }

    public static void solve() {
        Game ten = new TenGame();
        Solver s = new Solver(ten);
        for (int i = 0; i <= start; i += 1) {
            System.out.println(i + ": " + s.solve(i));
        }
    }

    public static void main(String[] args) {
        solve();
    }
}
