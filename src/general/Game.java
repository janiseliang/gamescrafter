package general;

public abstract class Game {

        abstract int doMove(int board, int move);

        abstract int[] generateMoves(int board);

        abstract String primitiveValue(int board);

        abstract int[] symmetries(int board);
}
