package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoveCalc extends PieceMoveCalc {

    public KingMoveCalc(ChessPiece myPiece, Collection<ChessMove> moves) {
        super(myPiece, moves);
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }
}
