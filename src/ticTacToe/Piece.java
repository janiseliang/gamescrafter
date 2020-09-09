package ticTacToe;

/** Representation of X's and O's in each square. */
enum Piece {
    X, O, EMP;

    Piece opposite() {
        switch (this) {
            case X:
                return O;
            case O:
                return X;
            default:
                return null;
        }
    }
}