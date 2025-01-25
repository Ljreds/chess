package chess;


import java.util.Collection;

public class QueenMoveCalc extends PieceMoveCalc {

    public QueenMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.piece = board.getPiece(myPosition);
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