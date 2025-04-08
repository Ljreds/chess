package client;

import facade.ResponseException;
import facade.ServerFacade;
import model.GameData;
import request.*;
import response.*;
import ui.ChessUi;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static client.State.SIGNEDIN;
import static client.State.SIGNEDOUT;

public class PostLoginClient extends Client {

    private final ChessUi chessUi;
    private static final Map<Integer, Integer> gameIds = new HashMap<>();
    private static final PostLoginClient INSTANCE = new PostLoginClient();
    private NotificationHandler notificationHandler = null;

    private PostLoginClient(){
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
                case "logout" -> logout();
                case "create", "c" -> createGame(params);
                case "join", "j" -> joinGame(params);
                case "list" -> listGame();
                case "spectate", "s" -> spectate(params);
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

    private String logout() throws ResponseException {
        LogoutRequest request = new LogoutRequest(authToken);
        LogoutResult result = server.logout(request);
        if(result != null) {
            authToken = null;
            state = SIGNEDOUT;
            return "You are signed out";
        }
        return null;
    }
    private String createGame(String... params) throws ResponseException {
        if(params.length == 1) {
            GameRequest request = new GameRequest(params[0], authToken);
            GameResult result = server.createGame(request);
            state = SIGNEDIN;
            if (result != null) {
                gameIds.put(gameIds.size() + 1, result.gameID());
                return "New game created: " + params[0];
            }
            return null;
        }else{
            return "Error: Parameters are incorrect";
        }
    }

    private String listGame() throws ResponseException {
        ListRequest request = new ListRequest(authToken);
        ListResult result = server.listGame(request);
        compileGames();

        state = SIGNEDIN;

        chessUi.createList(result.games());
        return "";

    }

    @Override
    public void compileGames() throws ResponseException {
        ListRequest request = new ListRequest(authToken);
        ListResult result = server.listGame(request);
        int n = 1;
        for(GameData game : result.games()){
            gameIds.put(n, game.gameID());
            n++;
        }
    }

    private String joinGame(String... params) throws ResponseException {
        if(params.length == 2) {
            try {
                int gameId = Integer.parseInt(params[1]);
                String color = params[0].toUpperCase();
                JoinRequest request = new JoinRequest(color, gameIds.get(gameId), authToken);
                JoinResult result = server.joinGame(request);
                ws = new WebSocketFacade(serverUrl, notificationHandler);
                ws.connect(authToken, result.gameId());
                state = SIGNEDIN;

                chessUi.createBoard(result.chessGame(), color);

                return "";

            }catch(ResponseException ex){
                throw ex;
            } catch(Throwable ex){
                return "Invalid gameID: " + params[1];
            }
        }else{
            return "Error: Parameters are incorrect";
        }
    }

    private String spectate(String... params) throws ResponseException{
        if(params.length == 1) {
            try {
                int gameId = Integer.parseInt(params[0]);
                JoinRequest request = new JoinRequest("SPECTATE", gameIds.get(gameId), authToken);
                JoinResult result = server.joinGame(request);

                state = SIGNEDIN;

                chessUi.createBoard(result.chessGame(), "WHITE");

                return "";

            }catch(ResponseException ex){
                throw ex;
            }catch(Throwable ex){
                return "Invalid gameID: " + params[0];
            }
        }else{
            return "Error: Parameters are incorrect";
        }
    }

    @Override
    public String help(){
       return """
               Logout: "logout";
               Create a new game: "c", "create" <GAME NAME>
               List all games: "list"
               Join a game: "j", "join" <Player Color> <gameID>
               Spectate a game: "s", "spectate" <gameID>
               Print this message: "h", "help"
               """;
    }

    public static synchronized PostLoginClient getInstance(){
        return INSTANCE;
    }

    public NotificationHandler getNotificationHandler() {
        return notificationHandler;
    }

    public void setNotificationHandler(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }
}
