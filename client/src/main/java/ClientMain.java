import chess.*;
import client.Repl;
import ui.ChessUi;

public class ClientMain {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        var url = "http://localhost:8080";
        Repl repl = new Repl(url);
        repl.run();
        ChessUi chessUi = new ChessUi();
        chessUi.createBoard(new ChessGame(), "BLACK");
    }
}