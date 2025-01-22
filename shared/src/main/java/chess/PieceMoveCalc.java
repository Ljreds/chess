package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMoveCalc {
    private Collection<ChessMove> moves = new ArrayList<>();
    public PieceMoveCalc() {
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece myPiece = board.getPiece(myPosition);
        if(myPiece.getPieceType() == ChessPiece.PieceType.KING){
            KingMoveCalc kingMoveCalc = new KingMoveCalc();
            moves = kingMoveCalc.kingMoves(board, myPosition);
        };
        if(myPiece.getPieceType() == ChessPiece.PieceType.QUEEN){
            throw new RuntimeException("Not implemented");
        }

        return moves;
    }
}

