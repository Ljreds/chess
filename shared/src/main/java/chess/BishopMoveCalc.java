package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalc extends PieceMoveCalc {
    private final Collection<ChessMove> moves = new ArrayList<>();
    private final int row;
    private final int col;
    private final ChessPiece piece;

    public BishopMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.piece = board.getPiece(myPosition);
    }


    public Collection<ChessMove> bishopMoves() {
        rightUpMoves();
        rightDownMoves();
        leftUpMoves();
        leftDownMoves();
        return moves;
    }

    public void rightUpMoves() {
        for(int i = 1; i <= 8; i++) {
            if ((i + row) <= 8 && (i + col) <= 8) {
                ChessPiece otherPiece = board.getPiece(new ChessPosition(row + i, col + i));
                if (otherPiece == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + i, col + i), null));
                }
                else if (otherPiece.getTeamColor() == piece.getTeamColor()) {
                    break;
                }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + i, col + i), null));
                    break;
                }

            }
        }
    }
    public void leftUpMoves() {
        for(int i = 1; i <= 8; i++) {
            if ((row - i) > 0 && (i + col) <= 8) {
                ChessPiece otherPiece = board.getPiece(new ChessPosition(row - i, col + i));
                if (otherPiece == null){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - i, col + i), null));
                }
                else if (otherPiece.getTeamColor() == piece.getTeamColor()) {
                    break;
                }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - i, col + i), null));
                    break;
                }

            }
        }
    }
    public void leftDownMoves() {
        for(int i = 1; i <= 8; i++) {
            if ((row - i) > 0 && (col - i) > 0) {
                ChessPiece otherPiece = board.getPiece(new ChessPosition(row - i, col - i));
                if (otherPiece == null){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - i, col - i), null));
                }
                else if (otherPiece.getTeamColor() == piece.getTeamColor()) {
                    break;
                }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - i, col - i), null));
                    break;
                }

            }
        }
    }
    public void rightDownMoves() {
        for(int i = 1; i <= 8; i++) {
            if ((i + row) <= 8 && (col - i) > 0) {
                ChessPiece otherPiece = board.getPiece(new ChessPosition(row + i, col - i));
                if (otherPiece == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + i, col - i), null));
                }else if (otherPiece.getTeamColor() == piece.getTeamColor()) {
                    break;
                }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + i, col - i), null));
                    break;
                }

            }
        }
    }

}