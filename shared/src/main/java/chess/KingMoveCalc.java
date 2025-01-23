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

        return PieceMoveCalc.borderPatrol(board, pieceBlockKing(board, moves));
    }

    public static Collection<ChessMove> pieceBlockKing(ChessBoard board, Collection<ChessMove> moves) {
        Collection<ChessMove> result = new ArrayList<>();
        for (ChessMove move : moves) {
            ChessPosition start = move.getStartPosition();
            ChessPosition end = move.getEndPosition();
            ChessPiece piece = board.getPiece(start);
            ChessPiece endPiece = board.getPiece(end);
            if(endPiece == null){
                result.add(move);
            }else if(piece.getTeamColor() == endPiece.getTeamColor()){
                continue;

            }else if(endPiece.getTeamColor() != piece.getTeamColor()){
                result.add(move);
            }
        }
        return result;
    }
}
