package ticTacToe;
import static ticTacToe.Game.*;

import java.util.HashMap;

public class Solver {

    HashMap<Board, String> memo;

    /** 0 - LOSE, 1 - primitive LOSE, 2 - WIN, 3 - primitive WIN, 4 - TIE, 5 - primitive TIE*/
    int[] counter;

    public Solver() {
        memo = new HashMap<>();
        counter = new int[6];
    }

    public String solve(Board board) {
        if (memo.containsKey(board)) {
            return memo.get(board);
        }
        String pVal = primitiveValue(board);
        if (!pVal.equals("not_primitive")) {
            if (pVal.equals("lose")) {
                counter[1] += 1;
            } else if (pVal.equals("win")) {
                System.out.println("this should never happen");
                counter[3] += 1;
            } else {
                counter[5] += 1;
            }
            return memoize(board, pVal);
        }
        int[] moves = generateMoves(board);
        String[] future = new String[moves.length];
        for (int i = 0; i < moves.length; i++) {
            Board newBoard = doMove(board, moves[i]);
            future[i] = solve(newBoard);
        }
        boolean winFlag = true; //whether all children are winning children -> this is losing position
        for (String f : future) {
            if (f.equals("lose")) {
                return memoize(board, "win");
            } else {
                winFlag = winFlag && f.equals("win");
            }
        }
        return memoize(board, winFlag ? "lose" : "tie");
    }

    public String memoize(Board b, String value) {
        if (!memo.containsKey(b)) {
            memo.put(b, value);
            switch(value) {
                case "lose":
                    counter[0] += 1;
                    break;
                case "win":
                    counter[2] += 1;
                    break;
                case "tie":
                    counter[4] += 1;
                    break;
            }
        }
        return value;
    }

    public void printCounts() {
        System.out.printf("Lose: %d (%d primitive)\n", counter[0], counter[1]);
        System.out.printf("Win: %d (%d primitive)\n", counter[2], counter[3]);
        System.out.printf("Tie: %d (%d primitive)\n", counter[4], counter[5]);
        System.out.printf("Total: %d (%d primitive)\n\n",
                counter[0] + counter[2] + counter[4],
                counter[1] + counter[3] + counter[5]);
    }

    public static void main(String[] args) {
        Board b = new Board();
        Solver s = new Solver();
        long t = System.currentTimeMillis();
        s.solve(b);
        s.printCounts();
        System.out.println("Time: " + (System.currentTimeMillis() - t) + " ms");
    }
}
