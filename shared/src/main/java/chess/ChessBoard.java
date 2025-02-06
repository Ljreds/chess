package chess;

import java.util.Arrays;
import java.util.Objects;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] squares;
    public ChessBoard() {
        this.squares = new ChessPiece[8][8];
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for(int row = 0; row < 8; row++) {
            for(int column = 0; column < 8; column++) {
                squares[row][column] = null;
            }
        }
        pieceReset(0, 1, WHITE);
        pieceReset(7, 6, BLACK);
    }

    public void pieceReset(int pos, int pawnPos, ChessGame.TeamColor color){
        for(int col = 0; col < 8; col++){
            squares[pawnPos][col] = new ChessPiece(color, ChessPiece.PieceType.PAWN);
        }
        squares[pos][0] = new ChessPiece(color, ChessPiece.PieceType.ROOK);
        squares[pos][1] = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
        squares[pos][2] = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
        squares[pos][3] = new ChessPiece(color, ChessPiece.PieceType.QUEEN);
        squares[pos][4] = new ChessPiece(color, ChessPiece.PieceType.KING);
        squares[pos][5] = new ChessPiece(color, ChessPiece.PieceType.BISHOP);
        squares[pos][6] = new ChessPiece(color, ChessPiece.PieceType.KNIGHT);
        squares[pos][7] = new ChessPiece(color, ChessPiece.PieceType.ROOK);

    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }
}
