package ticTacToe;
import java.util.ArrayList;
import java.util.Arrays;

import static ticTacToe.Piece.*;

public class Board {

    Piece[] state;
    Piece turn;

    public Board() {
        state = new Piece[9];
        for (int i = 0; i < 9; i++) {
            state[i] = EMP;
        }
        turn = X;
    }

    public Board(Board b) {
        state = new Piece[9];
        System.arraycopy(b.state, 0, state, 0, 9);
        turn = b.turn;
    }

    public Board makeMoveCopy(int pos) {
        assert pos >= 0 && pos < 9;
        Board copy = new Board(this);
        copy.state[pos] = turn;
        copy.turn = turn.opposite();
        return copy;
    }

    /** Returns EMP if tie (all squares filled but no winner)
     *  Possible win patterns: 012, 345, 678, 036, 147, 258, 048, 246 */
    public Piece winner() {
        Piece a = state[0], b = state[1], c = state[2];
        Piece d = state[3], e = state[4], f = state[5];
        Piece g = state[6], h = state[7], i = state[8];
        if (a != EMP && ((a == b && a == c) || (a == d && a == g)
                || (a == e && a == i))) {
            return a;
        } else if (b != EMP && (b == e && b == h)) {
            return b;
        } else if (c != EMP && ((c == f && c == i) || (c == e && c == g))) {
            return c;
        } else if (d != EMP && (d == e && d == f)) {
            return d;
        } else if (g != EMP && (g == h && g == i)) {
            return g;
        } else if (empties().length == 0) {
            return EMP;
        }
        return null;
    }

    public int[] empties() {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 0; i < state.length; i++) {
            if (state[i] == EMP) {
                arr.add(i);
            }
        }
        int[] res = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            res[i] = arr.get(i);
        }
        return res;
    }

    /*public String toString() {
        String s = "";
        s += String.format("%s %s %s\n%s %s %s\n%s %s %s\n\n",
                state[0], state[1], state[2],
                state[3], state[4], state[5],
                state[6], state[7], state[8]);

        s += "Player " + turn + "'s turn\n---------------\n";
        return s;
    }*/

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != Board.class) return false;
        return Arrays.equals(state, ((Board) o).state);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(state);
    }
}
