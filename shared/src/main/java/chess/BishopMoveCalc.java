package chess;


import java.util.Collection;

public class BishopMoveCalc extends PieceMoveCalc {

    public BishopMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.piece = board.getPiece(myPosition);
    }


    public Collection<ChessMove> bishopMoves() {
        rightUp();
        rightDown();
        leftUp();
        leftDown();
        return moves;
    }

}