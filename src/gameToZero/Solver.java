package gameToZero;

import static gameToZero.TenGame.*;

/** Game solver searches game tree and finds win value of POSITION.
 *  @author janise
 */
public class Solver {

    Game game;

    public Solver(Game g) {
        game = g;
    }

    //todo: check that result is correct?
    //todo: look into cache-ing for more efficiency
    public String solve(int position) {
        String state = primitiveValue(position);
        if (!state.equals("not_primitive")) {
            return state;
        }
        int[] moves = game.generateMoves(position);
        String[] future = new String[moves.length];
        for (int i = 0; i < moves.length; i++) {
            int newPos = doMove(position, moves[i]);
            future[i] = solve(newPos);
        }
        boolean flag = true;
        for (String f : future) {
            if (f.equals("lose")) {
                return "win";
            } else {
                flag = flag && f.equals("win");
            }
        }
        return flag ? "lose" : "tie";
    }
}
