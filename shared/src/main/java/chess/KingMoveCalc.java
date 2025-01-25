package chess;


import java.util.Collection;

public class KingMoveCalc extends PieceMoveCalc {


    public KingMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.piece = board.getPiece(myPosition);
    }

    public Collection<ChessMove> kingMoves() {

        kingDown();
        kingLeft();
        kingRight();
        kingUp();
        kingDownLeft();
        kingDownRight();
        kingUpLeft();
        kingUpRight();
        return moves;
    }

    public void kingLeft(){
        if ((col - 1) > 0) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row, col - 1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col - 1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col - 1), null));

            }

        }

    }
    public void kingRight(){
        if ((col + 1) <= 8) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row, col+1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col+1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col+1), null));

            }

        }

    }
    public void kingDown(){
        if ((row - 1) > 0) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row-1, col));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row-1, col), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row-1, col), null));

            }

        }

    }
    public void kingUp(){
        if ((row + 1) <= 8) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row +1, col));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row +1, col), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row+1, col), null));

            }

        }

    }
    public void kingDownLeft(){
        if ((col - 1) > 0 && (row - 1) > 0) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row - 1, col - 1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), null));

            }

        }

    }
    public void kingUpLeft(){
        if ((col - 1) > 0 && (row + 1) <= 8) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row + 1, col - 1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), null));

            }

        }

    }

    public void kingDownRight(){
        if ((col + 1) <= 8 && (row - 1) > 0) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row - 1, col + 1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), null));

            }

        }

    }
    public void kingUpRight(){
        if ((col + 1) <= 8 && (row + 1) <= 8) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row + 1, col + 1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), null));

            }

        }

    }

}
