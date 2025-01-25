package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalc extends PieceMoveCalc {
    private final Collection<ChessMove> moves = new ArrayList<>();
    private final int row;
    private final int col;
    private final ChessPiece piece;

    public QueenMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.piece = board.getPiece(myPosition);
    }


    public Collection<ChessMove> queenMoves() {
        queenRightUp();
        queenLeftDown();
        queenLeftUp();
        queenRightDown();
        queenLeft();
        queenDown();
        queenUp();
        queenRight();
        return moves;
    }

    public void queenRightUp() {
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
    public void queenRightDown() {
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
    public void queenLeftDown() {
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
    public void queenLeftUp() {
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

    public void queenUp() {
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
    public void queenDown() {
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
    public void queenLeft() {
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
    public void queenRight() {
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