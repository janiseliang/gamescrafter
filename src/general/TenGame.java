package general;

import java.util.ArrayList;
import java.util.TreeMap;

/** Parent class of TenGame and TwentyFiveGame. */
public class TenGame extends Game{
    static int[] legalMoves = new int[]{1, 2};

    public TenGame() {
        start = 10;
        name = "10-to-0-by-1-or-2";
    }

    public int doMove(int position, int move) {
        return position - move;
    }

    public int[] generateMoves(int position) {
        ArrayList<Integer> moves = new ArrayList<>();
        for (int mv : legalMoves) {
            if (mv <= position) {
                moves.add(mv);
            }
        }
        int[] res = new int[moves.size()];
        for (int i = 0; i < moves.size(); i++) {
            res[i] = moves.get(i);
        }
        return res;
    }

    public String primitiveValue(int position) {
        return position == 0 ? "lose" : "not_primitive";
    }

    public int[] symmetries(int position) {
        return new int[]{position};
    }

    public static void main(String[] args) {
        Game g = new TenGame();
        solve(g, false);
    }
}
