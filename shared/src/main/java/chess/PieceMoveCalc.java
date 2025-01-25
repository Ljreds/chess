package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMoveCalc {
    protected ChessPosition myPosition;
    protected ChessBoard board;
    protected Collection<ChessMove> moves = new ArrayList<>();
    protected int row;
    protected int col;
    protected ChessPiece piece;
    public PieceMoveCalc(ChessBoard board, ChessPosition myPosition) {
        this.board = board;
        this.myPosition = myPosition;
        this.row = myPosition.getRow();
        this.col = myPosition.getColumn();
        this.piece = board.getPiece(myPosition);
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


    public void rightUp() {
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
    public void rightDown() {
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
    public void leftDown() {
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
    public void leftUp() {
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

    public void straightUp() {
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
    public void straightDown() {
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
    public void straightLeft() {
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
    public void straightRight() {
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

