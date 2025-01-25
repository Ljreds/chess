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
        }else if(myPiece.getPieceType() == ChessPiece.PieceType.BISHOP){
            BishopMoveCalc bishopMoveCalc = new BishopMoveCalc(board, myPosition);
            moves = bishopMoveCalc.bishopMoves();
        }else if(myPiece.getPieceType() == ChessPiece.PieceType.ROOK){
            RookMoveCalc rookMoveCalc = new RookMoveCalc(board, myPosition);
            moves = rookMoveCalc.rookMoves();
        }else if(myPiece.getPieceType() == ChessPiece.PieceType.QUEEN){
            QueenMoveCalc queenMoveCalc = new QueenMoveCalc(board, myPosition);
            moves = queenMoveCalc.queenMoves();
        }else if(myPiece.getPieceType() == ChessPiece.PieceType.PAWN){
            PawnMoveCalc pawnMoveCalc = new PawnMoveCalc(board, myPosition);
            moves = pawnMoveCalc.pawnMoves();
        }else if(myPiece.getPieceType() == ChessPiece.PieceType.KNIGHT){
            KnightMoveCalc knightMoveCalc = new KnightMoveCalc(board, myPosition);
            moves = knightMoveCalc.knightMoves();
        }

        return moves;
    }
}

