package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoveCalc extends PieceMoveCalc {

    private final Collection<ChessMove> moves = new ArrayList<>();
    private int row = myPosition.getRow();
    private int col = myPosition.getColumn();

    public KingMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece myPiece = board.getPiece(myPosition);

        moves.add(new ChessMove(myPosition, new ChessPosition(row+1, col), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(row+1, col+1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(row, col+1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(row-1, col+1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(row-1, col), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(row-1, col-1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(row, col-1), null));
        moves.add(new ChessMove(myPosition, new ChessPosition(row+1, col-1), null));

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
