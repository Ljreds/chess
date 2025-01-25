package chess;



import java.util.Collection;

public class KnightMoveCalc extends PieceMoveCalc {


    public KnightMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.piece = board.getPiece(myPosition);
    }

    public Collection<ChessMove> knightMoves() {
        knightDown();
        knightUp();
        knightLeft();
        knightRight();
        return moves;
    }

    public void knightUp(){
        if ((row + 2) <= 8 && (col - 1) > 0) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row + 2, col - 1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col - 1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col - 1), null));

            }

        }
        if ((row + 2) <= 8 && (col + 1) <= 8){
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row + 2, col + 1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col + 1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col + 1), null));

            }
        }


    }
    public void knightDown(){
        if ((row - 2) > 0 && (col - 1) > 0) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row - 2, col - 1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col - 1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col - 1), null));

            }

        }
        if ((row - 2) > 0 && (col + 1) <= 8){
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row - 2, col + 1));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col + 1), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col + 1), null));

            }
        }


    }
    public void knightRight(){
        if ((row + 1) <= 8 && (col + 2) <= 8) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row + 1, col + 2));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 2), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 2), null));

            }

        }
        if ((row - 1) > 0 && (col + 2) <= 8){
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row - 1, col + 2));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 2), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 2), null));

            }
        }


    }
    public void knightLeft(){
        if ((row + 1) <= 8 && (col - 2) > 0) {
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row + 1, col - 2));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 2), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 2), null));

            }

        }
        if ((row - 1) > 0 && (col - 2) > 0){
            ChessPiece otherPiece = board.getPiece(new ChessPosition(row - 1, col  - 2));
            if (otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 2), null));
            }else if(otherPiece.getTeamColor() != piece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col  - 2), null));

            }
        }


    }


}
