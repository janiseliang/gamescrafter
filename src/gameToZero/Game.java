package gameToZero;

import java.util.ArrayList;

public abstract class Game {
    static int[] legalMoves;
    static int start;

    static int doMove(int position, int move) {
        return position - move;
    }

    int[] generateMoves(int position) {
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

    static String primitiveValue(int position) {
        return position == 0 ? "lose" : "not_primitive";
    }

}
