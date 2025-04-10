package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

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
                    whiteFirst(out, squareCol, piece);
                }else{
                    blackFirst(out, squareCol, piece);
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
                    whiteFirst(out, squareCol, piece);
                }else{
                    blackFirst(out, squareCol, piece);
                }
            }
            drawRanks(out, String.valueOf(ranks.get(squareRow - 1)));
            out.println();
        }
    }


    private static void whiteFirst(PrintStream out, int squareCol, ChessPiece piece){
            if (squareCol % 2 == 0) {
                if(piece != null){
                   printPiece(out, SET_BG_COLOR_TAN, piece);
                }else {
                    setTan(out);
                    out.print(EMPTY);
                }
            } else {
                if(piece != null){
                    printPiece(out, SET_BG_COLOR_BROWN, piece);
                }else {
                    setBrown(out);
                    out.print(EMPTY);
                }
            }
    }
    private static void blackFirst(PrintStream out, int squareCol, ChessPiece piece){
        if (squareCol % 2 == 0) {
            if(piece != null){
                printPiece(out, SET_BG_COLOR_BROWN, piece);
            }else {
                setBrown(out);
                out.print(EMPTY);
            }
        } else {
            if(piece != null){
                printPiece(out, SET_BG_COLOR_TAN, piece);
            }else {
                setTan(out);
                out.print(EMPTY);
            }
        }
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

    private static void setBrown(PrintStream out) {
        out.print(SET_BG_COLOR_BROWN);
        out.print(SET_TEXT_COLOR_BROWN);
    }

    private static void setTan(PrintStream out) {
        out.print(SET_BG_COLOR_TAN);
        out.print(SET_TEXT_COLOR_TAN);
    }

    private static void resetColor(PrintStream out) {
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }

}
