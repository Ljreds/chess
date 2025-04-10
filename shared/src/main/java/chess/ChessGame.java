package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard gameBoard;

    public ChessGame() {
        this.gameBoard = new ChessBoard();
        gameBoard.resetBoard();
        this.teamTurn = TeamColor.WHITE;

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    public TeamColor switchTurn(TeamColor currentTurn) {
        if(currentTurn == TeamColor.WHITE) {
            return TeamColor.BLACK;
        }else{
            return TeamColor.WHITE;
        }
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = gameBoard.getPiece(startPosition);
        if(piece != null) {
            Collection<ChessMove> moves = piece.pieceMoves(gameBoard, startPosition);
            return moveThroughCheck(moves);

        }

       return null;
    }

    public Collection<ChessMove> moveThroughCheck(Collection<ChessMove> moves) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        for(ChessMove move : moves) {
            ChessBoard board = gameBoard.clone();
            ChessGame virtualChess = new ChessGame();
            virtualChess.setBoard(board);

            ChessPiece piece = board.getPiece(move.getStartPosition());
            board.setPiece(move, board.getPiece(move.getStartPosition()));

            if(!virtualChess.isInCheck(piece.getTeamColor())) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        ChessPiece movePiece = gameBoard.getPiece(move.getStartPosition());
        if(movePiece != null) {
            if (invalidHandler(movePiece)) {
                for (ChessMove validMove : validMoves) {
                    if (move.equals(validMove) && movePiece.getTeamColor() == teamTurn) {
                        confirmMove(move, movePiece);
                        TeamColor color = switchTurn(movePiece.getTeamColor());
                        setTeamTurn(color);
                    }

                }
            }
        }
        throw new InvalidMoveException("Error: Invalid Move");
    }

    private boolean invalidHandler(ChessPiece piece) throws InvalidMoveException {
        if(isInCheckmate(piece.getTeamColor())){
            throw new InvalidMoveException(piece.getTeamColor().toString() + "is in checkmate.");
        }else if(isInStalemate(piece.getTeamColor())){
            throw new InvalidMoveException(piece.getTeamColor().toString() + "is in stalemate.");
        }else{
            return true;
        }

    }


    public void confirmMove(ChessMove move, ChessPiece movePiece){
        if(movePiece.getPieceType() == ChessPiece.PieceType.PAWN){
            pawnMove(move, movePiece);
        }else {
            gameBoard.setPiece(move, movePiece);
        }
    }

    public void pawnMove(ChessMove move, ChessPiece movePiece){
        if(pawnPromotes(move)) {
            ChessPiece promoPiece = new ChessPiece(movePiece.getTeamColor(), move.getPromotionPiece());
            gameBoard.setPiece(move, promoPiece);
        }else{
            gameBoard.setPiece(move, movePiece);
        }
    }

    public boolean pawnPromotes(ChessMove move){
        ChessPiece pawn = gameBoard.getPiece(move.getStartPosition());
        if(pawn != null) {
            if(pawn.getTeamColor() == TeamColor.WHITE) {
                return move.getEndPosition().getRow() == 8;
            }else{
                return move.getEndPosition().getRow() == 1;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(gameBoard, chessGame.gameBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, gameBoard);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for(int row = 1; row <= 8; row++) {
            for(int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = gameBoard.getPiece(position);
                if(piece != null && piece.getTeamColor() != teamColor){
                   if (checkMoves(piece, teamColor, position)){
                       return true;
                   }
                }

            }
        }
        return false;
    }

    public boolean checkMoves(ChessPiece piece, TeamColor teamColor, ChessPosition position){
        Collection<ChessMove> moves = piece.pieceMoves(gameBoard, position);
        for(ChessMove move : moves) {
            ChessPosition end = move.getEndPosition();
            ChessPiece endPiece = gameBoard.getPiece(end);
            if(endPiece != null && endPiece.getTeamColor() == teamColor){
                if(endPiece.getPieceType() == ChessPiece.PieceType.KING){
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessPiece> pieceWithMoves = new ArrayList<>();
        if (isInCheck(teamColor)){
            return searchPieces(teamColor, pieceWithMoves);
        }
        return false;
    }

    private boolean searchPieces(TeamColor teamColor, Collection<ChessPiece> pieceWithMoves) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = gameBoard.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = validMoves(position);
                    if(!moves.isEmpty()){
                        pieceWithMoves.add(piece);
                    }
                }

            }
        }
        return pieceWithMoves.isEmpty();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        Collection<ChessPiece> pieceWithMoves = new ArrayList<>();
        if(!isInCheck(teamColor)) {
            return searchPieces(teamColor, pieceWithMoves);
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameBoard;
    }
}
