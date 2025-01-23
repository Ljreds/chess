package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMoveCalc {
    protected ChessPosition myPosition;
    protected ChessBoard board;
    private Collection<ChessMove> moves = new ArrayList<>();
    public PieceMoveCalc(ChessBoard board, ChessPosition myPosition) {
        this.board = board;
        this.myPosition = myPosition;
    }

    public Collection<ChessMove> pieceMoves() {
        ChessPiece myPiece = board.getPiece(myPosition);
        if(myPiece.getPieceType() == ChessPiece.PieceType.KING){
            KingMoveCalc kingMoveCalc = new KingMoveCalc(board, myPosition);
            moves = kingMoveCalc.kingMoves(board, myPosition);
        };
        if(myPiece.getPieceType() == ChessPiece.PieceType.BISHOP){
            BishopMoveCalc bishopMoveCalc = new BishopMoveCalc(board, myPosition);
            moves = bishopMoveCalc.bishopMoves();
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

