package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalc extends PieceMoveCalc {
    private final Collection<ChessMove> moves = new ArrayList<>();
    private final int row;
    private final int col;
    private final ChessPiece piece;

    public RookMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.piece = board.getPiece(myPosition);
    }


    public Collection<ChessMove> rookMoves() {
        rookRight();
        rookLeft();
        rookDown();
        rookUp();
        return moves;
    }

    public void rookUp() {
        for(int i = 1; i <= 8; i++) {
            if ((i + row) <= 8) {
                ChessPiece otherPiece = board.getPiece(new ChessPosition(row + i, col));
                if (otherPiece == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + i, col), null));
                }
                else if (otherPiece.getTeamColor() == piece.getTeamColor()) {
                    break;
                }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + i, col), null));
                    break;
                }

            }
        }
    }
    public void rookDown() {
        for(int i = 1; i <= 8; i++) {
            if ((row - i) > 0) {
                ChessPiece otherPiece = board.getPiece(new ChessPosition(row - i, col));
                if (otherPiece == null){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - i, col), null));
                }
                else if (otherPiece.getTeamColor() == piece.getTeamColor()) {
                    break;
                }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - i, col), null));
                    break;
                }

            }
        }
    }
    public void rookLeft() {
        for(int i = 1; i <= 8; i++) {
            if ((col - i) > 0) {
                ChessPiece otherPiece = board.getPiece(new ChessPosition(row, col - i));
                if (otherPiece == null){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col - i), null));
                }
                else if (otherPiece.getTeamColor() == piece.getTeamColor()) {
                    break;
                }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col - i), null));
                    break;
                }

            }
        }
    }
    public void rookRight() {
        for(int i = 1; i <= 8; i++) {
            if ((col + i) <= 8) {
                ChessPiece otherPiece = board.getPiece(new ChessPosition(row, col + i));
                if (otherPiece == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col + i), null));
                }else if (otherPiece.getTeamColor() == piece.getTeamColor()) {
                    break;
                }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col + i), null));
                    break;
                }

            }
        }
    }

}