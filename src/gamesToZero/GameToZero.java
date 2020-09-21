package gamesToZero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/** Parent class of TenGame and TwentyFiveGame. */
public abstract class GameToZero {
    static int[] legalMoves;
    static int start;

    public static int doMove(int position, int move) {
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

    public static String primitiveValue(int position) {
        return position == 0 ? "lose" : "not_primitive";
    }

    public static void solve(Solver s) {
        TreeMap<Integer, int[]> res = new TreeMap<>();

        int[] totals = new int[] {0, 0, 0, 0};

        for (int i = start; i >= 0; i -= 1) {
            Solver.State x = s.solve(i);
            if (!res.containsKey(x.rem)) {
                res.put(x.rem, new int[]{0, 0, 0});
            }
            int j = x.win ? 0 : (x.lose ? 1 : 2);
            res.get(x.rem)[j] += 1;
            totals[j]++; totals[3]++;
        }

        System.out.println("Remote  Win   Lose   Tie    Total\n---------------------------------");

        for (int i : res.descendingKeySet()) {
            int[] x = res.get(i);
            System.out.printf("%-3d     %-3d   %-3d    %-3d    %-3d\n", i, x[0], x[1], x[2], x[0]+x[1]+x[2]);
        }
        System.out.println("---------------------------------");
        System.out.printf("Total   %-3d   %-3d    %-3d    %-3d\n", totals[0], totals[1], totals[2], totals[3]);
    }
}
