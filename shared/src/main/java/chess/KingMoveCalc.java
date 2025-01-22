package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoveCalc extends PieceMoveCalc {

    private final Collection<ChessMove> moves = new ArrayList<>();

    public KingMoveCalc() {
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);

        moves.add(new ChessMove(myPosition, new ChessPosition(row+1, col), myPiece.getPieceType()));
        moves.add(new ChessMove(myPosition, new ChessPosition(row+1, col+1), myPiece.getPieceType()));
        moves.add(new ChessMove(myPosition, new ChessPosition(row, col+1), myPiece.getPieceType()));
        moves.add(new ChessMove(myPosition, new ChessPosition(row-1, col+1), myPiece.getPieceType()));
        moves.add(new ChessMove(myPosition, new ChessPosition(row-1, col), myPiece.getPieceType()));
        moves.add(new ChessMove(myPosition, new ChessPosition(row-1, col-1), myPiece.getPieceType()));
        moves.add(new ChessMove(myPosition, new ChessPosition(row, col-1), myPiece.getPieceType()));
        moves.add(new ChessMove(myPosition, new ChessPosition(row+1, col-1), myPiece.getPieceType()));

        return moves;
    }
}
