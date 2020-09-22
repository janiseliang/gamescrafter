package gamesToZero;

/** Generalized.Game solver searches game tree and finds win value of POSITION.
 *  Caches previously-found win values of POSITION to optimize time.
 *  @author janise
 */
public class Solver {

    GameToZero game;
    State[] cache;

    public Solver(GameToZero g) {
        game = g;
        cache = new State[GameToZero.start + 1];
    }

    public State solve(int position) {
        if (cache[position] != null) {
            return cache[position];
        }
        String state = GameToZero.primitiveValue(position);
        if (!state.equals("not_primitive")) {
            return setCache(position, state, 0);
        }
        int[] moves = game.generateMoves(position);
        State[] future = new State[moves.length];
        for (int i = 0; i < moves.length; i++) {
            int newPos = GameToZero.doMove(position, moves[i]);
            future[i] = solve(newPos);
        }

        char val = 'l'; // l = lose (all winning children)
                        // w = win (one losing child)
                        // t = tie (1+ tie, rest winning)
        int remoteness = future[0].rem;
        for (State child : future) {
            if (child.lose && val == 'l') {
                //first losing child
                val = 'w';
                remoteness = child.rem;
            } else if (child.tie && val == 'l') {
                //first tie, all prev winning children
                val = 't';
                remoteness = child.rem;
            } else if ((child.win && val == 'l') || (child.lose && val == 'w')
                    || (child.tie && val == 't')) {
                remoteness = Math.min(remoteness, child.rem);
            }
        }
        remoteness += 1;
        String v = val == 'l' ? "lose" : (val == 'w' ? "win" : "tie");
        return setCache(position, v, remoteness);
    }

    public State setCache(int position, String state, int remoteness) {
        this.cache[position] = new State(state, remoteness);
        return this.cache[position];
    }

    public static class State {
        boolean win, lose, tie;
        int rem;
        public State(String state, int remoteness) {
            this.rem = remoteness;
            win = state.equals("win");
            lose = !win && state.equals("lose");
            tie = !win && !lose;
        }
    }
}
