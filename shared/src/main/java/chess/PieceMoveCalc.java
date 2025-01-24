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
            moves = kingMoveCalc.kingMoves();
        };
        if(myPiece.getPieceType() == ChessPiece.PieceType.BISHOP){
            BishopMoveCalc bishopMoveCalc = new BishopMoveCalc(board, myPosition);
            moves = bishopMoveCalc.bishopMoves();
        }

        return moves;
    }
}

