package chess;


import java.util.Collection;

public class PawnMoveCalc extends PieceMoveCalc {

    public PawnMoveCalc(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }


    public Collection<ChessMove> pawnMoves() {
        if(piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            pawnBlackMoves();
            pawnBlackCapture();
        }if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            pawnWhiteMoves();
            pawnWhiteCapture();
        }
        return moves;
    }

    public void pawnWhiteMoves() {
        ChessPiece otherPiece = board.getPiece(new ChessPosition(row + 1, col));
        if ((row + 1) == 8) {
            if(otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.QUEEN));
            }else{
                return;
            }
            return;
        }

        ChessPiece newPiece = board.getPiece(new ChessPosition(row + 2, col));
        if (otherPiece == null) {
            moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
            if (newPiece == null && row == 2) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col), null));
            }

        }
    }
    public void pawnBlackMoves() {
        ChessPiece otherPiece = board.getPiece(new ChessPosition(row - 1, col));
        if ((row - 1) == 1) {
            if(otherPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), ChessPiece.PieceType.QUEEN));
            }else{
                return;
            }
            return;
        }

        ChessPiece newPiece = board.getPiece(new ChessPosition(row - 2, col));
        if (otherPiece == null) {
            moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), null));
            if (newPiece == null && row == 7) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col), null));
            }

        }


    }

    public void pawnWhiteCapture(){
        if((col - 1) > 0) {
            ChessPiece captureLeft = board.getPiece(new ChessPosition(row + 1, col - 1));
            if (captureLeft != null && captureLeft.getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (row == 7){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), ChessPiece.PieceType.QUEEN));
                }else{
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), null));
                }
            }

        }
        if((col + 1) <= 8) {
            ChessPiece captureRight = board.getPiece(new ChessPosition(row + 1, col + 1));
            if (captureRight != null && captureRight.getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (row == 7){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), ChessPiece.PieceType.QUEEN));
                }else{
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), null));
                }
            }
        }

    }

    public void pawnBlackCapture(){
        if((col - 1) > 0) {
            ChessPiece captureLeft = board.getPiece(new ChessPosition(row - 1, col - 1));
            if (captureLeft != null && captureLeft.getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (row == 2){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), ChessPiece.PieceType.QUEEN));
                }else{
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), null));
                }
            }

        }
        if((col + 1) <= 8) {
            ChessPiece captureRight = board.getPiece(new ChessPosition(row - 1, col + 1));
            if (captureRight != null && captureRight.getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (row == 2){
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), ChessPiece.PieceType.QUEEN));
                }else{
                    moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), null));
                }
            }
        }

    }


}