package chess;


import java.util.Collection;

public class BishopMoveCalc extends PieceMoveCalc {

    public BishopMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }


    public Collection<ChessMove> bishopMoves() {
        rightUp();
        rightDown();
        leftUp();
        leftDown();
        return moves;
    }

}