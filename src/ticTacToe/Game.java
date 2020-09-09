package ticTacToe;
import static ticTacToe.Piece.*;

public class Game {

    static int boardSize = 9;

    public static Board doMove(Board board, int move) {
        return board.makeMoveCopy(move);
    }

    public static int[] generateMoves(Board board) {
        if (!primitiveValue(board).equals("not_primitive")) {
            return new int[0];
        }
        return board.empties();
    }

    public static String primitiveValue(Board board) {
        Piece winner = board.winner();
        if (winner == EMP) {
            return "tie";
        } else if (winner != null) {
            return "lose";
        }
        return "not_primitive";
    }
}
