package client;

import facade.ResponseException;
import facade.ServerFacade;
import model.GameData;
import request.GameRequest;
import request.JoinRequest;
import request.ListRequest;
import request.LogoutRequest;
import response.GameResult;
import response.JoinResult;
import response.ListResult;
import response.LogoutResult;
import ui.ChessUi;
import websocket.NotificationHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static client.State.SIGNEDIN;
import static client.State.SIGNEDOUT;

public class InGameClient extends Client {

    private final ChessUi chessUi;
    private static final Map<Integer, Integer> gameIds = new HashMap<>();

    private InGameClient(NotificationHandler notificationHandler){
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
                case "highlight", "h" -> highlight(params);
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



    @Override
    public String help(){
       return """
               Redraw the board: "r", "redraw"
               Make a move on your turn: "m", "move" <Piece> <Position>
               Highlight all available moves: "h", "highlight" <Piece>
               Leave game: "e", "exit"
               Resign game: "resign"
               Print this message: "h", "help"
               """;
    }

}
