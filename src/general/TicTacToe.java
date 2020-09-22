package general;

import java.util.*;

public class TicTacToe extends Game {

    static int start = 0;

    public int doMove(int val, int move) {
        int[] board = getBoard(val);
        int player = whoseTurn(board);
        board[move] = player;
        return getVal(board);
    }

    public int[] generateMoves(int val) {
        int[] board = getBoard(val);
        ArrayList<Integer> open = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                open.add(i);
            }
        }
        int[] a = new int[open.size()];
        for (int i = 0; i < open.size(); i++) {
            a[i] = open.get(i);
        }
        return a;
    }

    public String primitiveValue(int val) {
        int[] board = getBoard(val);
        int a = board[0], b = board[1], c = board[2];
        int d = board[3], e = board[4], f = board[5];
        int g = board[6], h = board[7], i = board[8];
        if (a != 0 && ((a == b && a == c) || (a == d && a == g)
                || (a == e && a == i))) {
            return "lose";
        } else if (b != 0 && (b == e && b == h)) {
            return "lose";
        } else if (c != 0 && ((c == f && c == i) || (c == e && c == g))) {
            return "lose";
        } else if (d != 0 && (d == e && d == f)) {
            return "lose";
        } else if (g != 0 && (g == h && g == i)) {
            return "lose";
        } else if (generateMoves(val).length == 0) {
            return "tie";
        }
        return "not_primitive";
    }

    public int[] symmetries(int val) {
        int[] board = getBoard(val);
        int a = board[0], b = board[1], c = board[2];
        int d = board[3], e = board[4], f = board[5];
        int g = board[6], h = board[7], i = board[8];

        int[] rot1 = new int[]{g, d, a, h, e, b, i, f, c}; //clockwise 90 deg
        int[] rot2 = new int[]{i, h, g, f, e, d, c, b, a}; //board, reversed
        int[] rot3 = new int[]{c, f, i, b, e, h, a, d, g}; //rot1, reversed
        int[] flip0 = new int[]{c, b, a, f, e, d, i, h, g}; //flip over vertical line
        int[] flip1 = new int[]{a, d, g, b, e, h, c, f, i}; //rotate 90 deg clockwise
        int[] flip2 = new int[]{g, h, i, d, e, f, a, b, c};
        int[] flip3 = new int[]{i, f, c, h, e, b, g, d, a};

        TreeSet<Integer> set = new TreeSet<>();
        Collections.addAll(set, getVal(rot1), getVal(rot2), getVal(rot3),
                getVal(flip0), getVal(flip1), getVal(flip2), getVal(flip3));

        int[] ret = new int[set.size()];
        int j = 0;
        for (int x : set) {
            ret[j++] = x;
        }

        return ret;
    }

    private int whoseTurn(int[] board) {
        int ones = 0;
        int twos = 0;
        for (int sq : board) {
            if (sq == 1) {
                ones++;
            } else if (sq == 2) {
                twos++;
            }
        }
        return ones > twos ? 2 : 1;
    }

    private static int[] getBoard(int val) {
        int[] b = new int[9];
        for (int i = 0; i < 9; i ++) {
            b[i] = (int) ((val / Math.pow(3, i)) % 3); //msb last
        }
        return b;
    }

    private static int getVal(int[] board) {
        assert board.length == 9;
        int val = 0;
        for (int i = 0; i < board.length; i ++) {
            val += board[i] * (Math.pow(3, i)); //msb last
        }
        return val;
    }

    public static Solver solve() {
        Game g = new TicTacToe();
        Solver s = new Solver(g);
        TreeMap<Integer, int[]> res = new TreeMap<>();

        int[] totals = new int[] {0, 0, 0, 0};

        s.solve(0/*, true*/);
        TreeMap<Integer, Solver.State> positions = s.memo;
        for (int b : positions.descendingKeySet()) {
            Solver.State x = positions.get(b);
            if (!res.containsKey(x.remote)) {
                res.put(x.remote, new int[]{0, 0, 0});
            }
            int j = x.win ? 0 : (x.lose ? 1 : 2);
            res.get(x.remote)[j] += 1;
            totals[j]++; totals[3]++;
        }

        System.out.println("Remote   Win    Lose    Tie     Total");
        System.out.println("-------------------------------------");

        for (int i : res.descendingKeySet()) {
            int[] x = res.get(i);
            System.out.printf("%-4d     %-4d   %-4d    %-4d    %-4d\n",
                    i, x[0], x[1], x[2], x[0]+x[1]+x[2]);
        }
        System.out.println("-------------------------------------");
        System.out.printf("Total    %-4d   %-4d    %-4d    %-4d\n",
                totals[0], totals[1], totals[2], totals[3]);
        return s;
    }

    public static void main(String[] args) {
        Solver s = solve();
        /*TicTacToe g = new TicTacToe();
        Solver s = new Solver(g);

        int[] b = new int[]{1, 0, 2, 0, 0, 0, 0, 0, 0};
        int bval = getVal(b);
        int turn = g.whoseTurn(b);

        s.solve(bval);
        System.out.println("win: " + s.memo.get(bval).win);
        System.out.println("lose: " + s.memo.get(bval).lose);
        System.out.println("tie: " + s.memo.get(bval).tie);
        System.out.println("remoteness: " + s.memo.get(bval).remote);
        */
    }
}
