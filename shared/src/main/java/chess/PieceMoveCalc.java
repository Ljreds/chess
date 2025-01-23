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
    public static Collection<ChessMove> borderPatrol(ChessBoard board, Collection<ChessMove> moves) {
        Collection<ChessMove> borderPatrolMoves = new ArrayList<>();
        for (ChessMove move : moves) {
            ChessPosition end = move.getEndPosition();
            int row = end.getRow();
            int col = end.getColumn();
            if(row >= 8|| row < 0 || col >= 8 || col < 0){
                continue;
            }else{
                borderPatrolMoves.add(move);
            }
        }
        return borderPatrolMoves;
    }
}

