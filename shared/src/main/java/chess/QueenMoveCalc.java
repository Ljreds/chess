package chess;


import java.util.Collection;

public class QueenMoveCalc extends PieceMoveCalc {

    public QueenMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }


    public Collection<ChessMove> queenMoves() {
        rightUp();
        rightDown();
        leftUp();
        leftDown();
        straightDown();
        straightUp();
        straightLeft();
        straightRight();
        return moves;
    }


}