package general;

import java.util.TreeMap;

public class Solver {

    TreeMap<Integer, State> memo;

    Game game;

    public Solver(Game g) {
        memo = new TreeMap<>();
        game = g;
    }

    public State solve(int board) {
        return solve(board, false);
    }

    public State solve(int board, boolean symmetries) {
        if (memo.containsKey(board)) {
            return memo.get(board);
        }
        String pVal = game.primitiveValue(board);
        if (!pVal.equals("not_primitive")) { //primitive state
            return memoize(board, pVal, 0);
        }
        int[] moves = game.generateMoves(board);
        State[] future = new State[moves.length];
        for (int i = 0; i < moves.length; i++) {
            int newBoard = game.doMove(board, moves[i]);
            future[i] = solve(newBoard, symmetries);
        }

        char val = 'l'; // l = lose (all winning children)
                        // w = win (one losing child)
                        // t = tie (1+ tie, rest winning)
        int remoteness = future[0].remote;
        boolean winFlag = true; //whether all children are winning children -> this is losing position
        for (State child : future) {
            if (child.lose && val != 'w') {
                //first losing child
                val = 'w';
                remoteness = child.remote;
            } else if (child.tie && val == 'l') {
                //first tie, all prev winning children
                val = 't';
                remoteness = child.remote;
            } else if (child.win && val == 'l' || (child.tie && val == 't')) {
                //maximize # of moves for lose/tie position - greater chance of blunder
                remoteness = Math.max(remoteness, child.remote);
            } else if (child.lose) { //minimize # of moves for winning position
                remoteness = Math.min(remoteness, child.remote);
            }
        }
        remoteness += 1;
        String v = val == 'l' ? "lose" : (val == 'w' ? "win" : "tie");

        if (symmetries) {
            int[] sym = game.symmetries(board);
            for (int b : sym) {
                memoize(b, v, remoteness);
            }
        }

        return memoize(board, v, remoteness);
    }

    public State memoize(int b, String value, int remoteness) {
        State st = new State(value, remoteness);
        memo.put(b, st);
        return st;
    }

    /** Subclass for keeping track of win value-remoteness tuples. */
    public static class State {
        boolean win, lose, tie;
        int remote;
        public State(String state, int remoteness) {
            this.remote = remoteness;
            win = state.equals("win");
            lose = !win && state.equals("lose");
            tie = !win && !lose;
        }
    }
}
