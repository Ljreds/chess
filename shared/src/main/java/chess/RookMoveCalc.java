package chess;



import java.util.Collection;

public class RookMoveCalc extends PieceMoveCalc {

    public RookMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }


    public Collection<ChessMove> rookMoves() {
        straightDown();
        straightUp();
        straightLeft();
        straightRight();
        return moves;
    }

}