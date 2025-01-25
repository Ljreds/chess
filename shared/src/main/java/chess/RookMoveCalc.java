package chess;



import java.util.Collection;

public class RookMoveCalc extends PieceMoveCalc {

    public RookMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.piece = board.getPiece(myPosition);
    }


    public Collection<ChessMove> rookMoves() {
        straightDown();
        straightUp();
        straightLeft();
        straightRight();
        return moves;
    }

}