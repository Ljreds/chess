package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMoveCalc {

    private Collection<ChessMove> moves = new ArrayList<>();
    private final ChessPiece myPiece;
    public PieceMoveCalc(ChessPiece myPiece, Collection<ChessMove> moves) {
        this.myPiece = myPiece;
        this.moves = moves;

    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if(myPiece.getPieceType() == ChessPiece.PieceType.KING){
            KingMoveCalc kingMoveCalc = new KingMoveCalc(myPiece, moves);
            moves = kingMoveCalc.kingMoves(board, myPosition);
        };
        if(myPiece.getPieceType() == ChessPiece.PieceType.QUEEN){
            throw new RuntimeException("Not implemented");
        }

        return moves;
    }
}

