package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import chess.ChessPiece.PieceType;
import chess.ChessGame.TeamColor;
import model.GameData;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static ui.EscapeSequences.*;

public class ChessUi {
    private static final int BOARD_SIZE_IN_SQUARES = 8;


    public void createBoard(ChessGame game, TeamColor playerColor){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out, playerColor);
        drawBoard(out, playerColor, game.getBoard());
        drawHeaders(out, playerColor);

    }

    public void createHighlight(ChessGame game, TeamColor playerColor, ChessPosition position){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out, playerColor);
        drawHighlight(out, playerColor, game, position);
        drawHeaders(out, playerColor);
    }

    public void createList(Collection<GameData> list){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        int n = 1;
        for(GameData game : list){
            out.print(n + ".  ");
            out.print("Game name: " + game.gameName() + EMPTY);
            if(game.whiteUsername() == null){
                out.print("White: EMPTY" + EMPTY);
            }else{
                out.print("White: " + game.whiteUsername() + EMPTY);
            }
            if(game.blackUsername() == null){
                out.print("Black: EMPTY");
            }else{
                out.print("Black: " + game.blackUsername());
            }
            n++;

            out.println();
        }

    }

    private static void drawHeaders(PrintStream out, TeamColor playerColor) {

        setRed(out);

        List<String> headers = new ArrayList<>(List.of("a", "b", "c", "d", "e", "f", "g", "h"));
        if(Objects.equals(playerColor, BLACK)){
            Collections.reverse(headers);
        }
        out.print(EMPTY);
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers.get(boardCol));

        }
        out.print(EMPTY);
        resetColor(out);

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {

        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(" " + headerText + " ");

        setRed(out);
    }

    private static void drawBoard(PrintStream out, TeamColor playerColor, ChessBoard chess){
        List<Integer> ranks = new ArrayList<>();
        for(int i = 1; i <= 8; i++){
            ranks.add(i);

        }
       if(Objects.equals(playerColor, WHITE)){
           whitePerspective(out, chess, ranks);
       }else{
           blackPerspective(out, chess, ranks);
       }
    }



    private static void blackPerspective(PrintStream out, ChessBoard chess, List<Integer> ranks){

        for(int squareRow = 0; squareRow < BOARD_SIZE_IN_SQUARES; squareRow++){
            drawRanks(out, String.valueOf(ranks.get(squareRow)));
            for(int squareCol = BOARD_SIZE_IN_SQUARES; squareCol > 0; squareCol--){
                ChessPiece piece = chess.getPiece(new ChessPosition(squareRow + 1, squareCol));
                if(squareRow % 2 == 0){
                    whiteFirst(out, squareCol, piece, false);
                }else{
                    blackFirst(out, squareCol, piece, false);
                }
            }
            drawRanks(out, String.valueOf(ranks.get(squareRow)));
            out.println();
        }
    }

    private static void whitePerspective(PrintStream out, ChessBoard chess, List<Integer> ranks){

        for(int squareRow = BOARD_SIZE_IN_SQUARES; squareRow > 0; squareRow--){
            drawRanks(out, String.valueOf(ranks.get(squareRow - 1)));
            for(int squareCol = 0; squareCol < 8; squareCol++) {
                ChessPiece piece = chess.getPiece(new ChessPosition(squareRow, squareCol + 1));
                if (squareRow % 2 == 0) {
                    whiteFirst(out, squareCol, piece, false);
                }else{
                    blackFirst(out, squareCol, piece, false);
                }
            }
            drawRanks(out, String.valueOf(ranks.get(squareRow - 1)));
            out.println();
        }
    }

    private static void whiteFirst(PrintStream out, int squareCol, ChessPiece piece, boolean valid){
        var lightBg = SET_BG_COLOR_TAN;
        String darkBg = SET_BG_COLOR_BROWN;
        String lightTxt = SET_TEXT_COLOR_TAN;
        String darkTxt = SET_TEXT_COLOR_BROWN;
        if(valid){
            lightBg = SET_BG_COLOR_GREEN;
            darkBg = SET_BG_COLOR_DARK_GREEN;
            lightTxt = SET_TEXT_COLOR_GREEN;
            darkTxt = SET_TEXT_COLOR_DARK_GREEN;
        }
        if (squareCol % 2 == 0) {
            if(piece != null){
                printPiece(out, lightBg, piece);
            }else {
                setLight(out, lightBg, lightTxt);
                out.print(EMPTY);
            }
        } else {
            if(piece != null){
                printPiece(out, darkBg, piece);
            }else {
                setDark(out, darkBg, darkTxt);
                out.print(EMPTY);
            }
        }
    }

    private static void blackFirst(PrintStream out, int squareCol, ChessPiece piece, boolean valid){
        String lightBg = SET_BG_COLOR_TAN;
        String darkBg = SET_BG_COLOR_BROWN;
        String lightTxt = SET_TEXT_COLOR_TAN;
        String darkTxt = SET_TEXT_COLOR_BROWN;
        if(valid){
            lightBg = SET_BG_COLOR_GREEN;
            darkBg = SET_BG_COLOR_DARK_GREEN;
            lightTxt = SET_TEXT_COLOR_GREEN;
            darkTxt = SET_TEXT_COLOR_DARK_GREEN;
        }
        if (squareCol % 2 == 0) {
            if(piece != null){
                printPiece(out, darkBg, piece);
            }else {
                setDark(out, darkBg, darkTxt);
                out.print(EMPTY);
            }
        } else {
            if(piece != null){
                printPiece(out, lightBg, piece);
            }else {
                setLight(out, lightBg, lightTxt);
                out.print(EMPTY);
            }
        }
    }

    private static void drawHighlight(PrintStream out, TeamColor playerColor, ChessGame chess, ChessPosition position){
        List<Integer> ranks = new ArrayList<>();
        for(int i = 1; i <= 8; i++){
            ranks.add(i);

        }
        if(Objects.equals(playerColor, WHITE)){
            whiteHighlight(out, chess, ranks, position);
        }else{
            blackHighlight(out, chess, ranks, position);
        }
    }

    private static void whiteHighlight(PrintStream out, ChessGame chess, List<Integer> ranks, ChessPosition position){
        ChessBoard board = chess.getBoard();
        Collection<ChessMove> moves = chess.validMoves(position);

        for(int squareRow = BOARD_SIZE_IN_SQUARES; squareRow > 0; squareRow--){
            drawRanks(out, String.valueOf(ranks.get(squareRow - 1)));
            for(int squareCol = 0; squareCol < 8; squareCol++) {
                ChessPiece piece = board.getPiece(new ChessPosition(squareRow, squareCol + 1));
                if(selectPiece(squareRow, squareCol + 1, moves)){
                    drawHlPiece(out, piece);
                }
                else if (squareRow % 2 == 0) {
                    whiteFirst(out, squareCol, piece, validMove(squareRow, squareCol + 1, moves));
                }else{
                    blackFirst(out, squareCol, piece, validMove(squareRow, squareCol + 1, moves));
                }
            }
            drawRanks(out, String.valueOf(ranks.get(squareRow - 1)));
            out.println();
        }
    }

    private static void blackHighlight(PrintStream out, ChessGame chess, List<Integer> ranks, ChessPosition position){
        ChessBoard board = chess.getBoard();
        Collection<ChessMove> moves = chess.validMoves(position);

        for(int squareRow = 0; squareRow < BOARD_SIZE_IN_SQUARES; squareRow++){
            drawRanks(out, String.valueOf(ranks.get(squareRow)));
            for(int squareCol = BOARD_SIZE_IN_SQUARES; squareCol > 0; squareCol--){
                ChessPiece piece = board.getPiece(new ChessPosition(squareRow + 1, squareCol));
                if(selectPiece(squareRow + 1, squareCol, moves)){
                    drawHlPiece(out, piece);
                }
                else if (squareRow % 2 == 0) {
                    whiteFirst(out, squareCol, piece, validMove(squareRow + 1, squareCol, moves));
                }else{
                    blackFirst(out, squareCol, piece, validMove(squareRow + 1, squareCol, moves));
                }
            }
            drawRanks(out, String.valueOf(ranks.get(squareRow)));
            out.println();
        }
    }

    private static void drawHlPiece(PrintStream out, ChessPiece piece){
        printPiece(out, SET_BG_COLOR_YELLOW, piece);
    }

    private static boolean validMove(int row, int col, Collection<ChessMove> moves){
        for(ChessMove move : moves){
            if(move.getEndPosition().getRow() == row){
                if(move.getEndPosition().getColumn() == col){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean selectPiece(int row, int col, Collection<ChessMove> moves){
        for(ChessMove move : moves){
            if(move.getStartPosition().getRow() == row){
                if(move.getStartPosition().getColumn() == col){
                    return true;
                }
            }
        }
        return false;
    }

    private static void printPiece(PrintStream out, String bgColor, ChessPiece piece){
        String strPiece = SYMBOLS.get(piece.getTeamColor()).get(piece.getPieceType());
        out.print(bgColor);
        if(piece.getTeamColor() == WHITE){
            out.print(SET_TEXT_COLOR_WHITE);
        }else{
            out.print(SET_TEXT_COLOR_BLACK);
        }
        out.print(strPiece);
        resetColor(out);
    }

    private static final Map<TeamColor, Map<ChessPiece.PieceType, String>> SYMBOLS = Map.of(
            WHITE, Map.of(
                    PieceType.KING, " ♔ ",
                    PieceType.QUEEN, " ♕ ",
                    PieceType.BISHOP, " ♗ ",
                    PieceType.KNIGHT, " ♘ ",
                    PieceType.ROOK, " ♖ ",
                    PieceType.PAWN, " ♙ "
            ),
            BLACK, Map.of(
                    PieceType.KING, " ♚ ",
                    PieceType.QUEEN, " ♛ ",
                    PieceType.BISHOP, " ♝ ",
                    PieceType.KNIGHT, " ♞ ",
                    PieceType.ROOK, " ♜ ",
                    PieceType.PAWN, " ♟ "
            )
    );

    private static void drawRanks(PrintStream out, String rank){
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(" " + rank + " ");

        resetColor(out);
    }




    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setDark(PrintStream out, String bg, String txt) {
        out.print(bg);
        out.print(txt);
    }

    private static void setLight(PrintStream out, String bg, String txt) {
        out.print(bg);
        out.print(txt);
    }

    private static void resetColor(PrintStream out) {
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }

}
