import chess.*;
import client.Client;
import client.Repl;
import ui.ChessUi;

public class ClientMain {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        var url = "http://localhost:8080";
        Client client = new Client(url);
        Repl repl = new Repl(client);
        repl.run();
    }
}