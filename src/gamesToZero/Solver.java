package gamesToZero;

/** Game solver searches game tree and finds win value of POSITION.
 *  Caches previously-found win values of POSITION to optimize time.
 *  @author janise
 */
public class Solver {

    GameToZero game;
    String[] cache;

    public Solver(GameToZero g) {
        game = g;
        cache = new String[g.start() + 1];
    }

    public String solve(int position) {
        if (cache[position] != null) {
            return cache[position];
        }
        String state = GameToZero.primitiveValue(position);
        if (!state.equals("not_primitive")) {
            return setCache(position, state);
        }
        int[] moves = game.generateMoves(position);
        String[] future = new String[moves.length];
        for (int i = 0; i < moves.length; i++) {
            int newPos = GameToZero.doMove(position, moves[i]);
            future[i] = solve(newPos);
        }
        boolean flag = true;
        for (String f : future) {
            if (f.equals("lose")) {
                return setCache(position, "win");
            } else {
                flag = flag && f.equals("win");
            }
        }
        return setCache(position, flag ? "lose" : "tie");
    }

    String setCache(int pos, String val) {
        cache[pos] = val;
        return val;
    }
}
