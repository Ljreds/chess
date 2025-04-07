package client;

import facade.ServerFacade;
import ui.ChessUi;

import java.util.Arrays;

import static client.State.SIGNEDIN;

public class InGameClient extends Client {

    private final ChessUi chessUi;

    private InGameClient(){
        super(serverUrl);
        server = new ServerFacade(serverUrl);
        chessUi = new ChessUi();

    }


    @Override
    public String eval(String input) {
        try {

            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            state = SIGNEDIN;
            return switch (cmd) {
                case "move", "m" -> makeMove(params);
                case "highlight", "hl" -> highlight(params);
                case "redraw", "r" -> redraw();
                case "exit", "e" -> leave();
                case "resign" -> resign();
                case "quit", "q" -> "quit";
                case "h", "help" -> help();
                default -> throw new IllegalStateException("Unexpected value: " + cmd);
            };
        }catch(ArrayIndexOutOfBoundsException ex) {
            return "Error: Some inputs left blank";
        }catch(Throwable ex){
            return ex.getMessage();
        }
    }

    private String resign() {
        return null;
    }

    private String leave() {
        return null;
    }

    private String redraw() {
        return null;
    }

    private String highlight(String[] params) {
        return null;
    }

    private String makeMove(String[] params) {
        return null;
    }


    @Override
    public String help(){
       return """
               Redraw the board: "r", "redraw"
               Make a move on your turn: "m", "move" <Piece> <Position>
               Highlight all available moves: "hl", "highlight" <Piece>
               Leave game: "e", "exit"
               Resign game: "resign"
               Print this message: "h", "help"
               """;
    }

}
