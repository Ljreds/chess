package client;

import Server.ResponseException;
import Server.ServerFacade;
import model.GameData;
import request.*;
import response.*;
import ui.ChessUi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static client.State.SIGNEDIN;
import static client.State.SIGNEDOUT;

public class PostLoginClient extends Client {

    private final ChessUi chessUi;
    private static Map<Integer, Integer> gameIds;
    private static final PostLoginClient instance = new PostLoginClient();

    private PostLoginClient() {
        super(serverUrl);
        server = new ServerFacade(serverUrl);
        chessUi = new ChessUi();
        gameIds = new HashMap<>();

    }


    @Override
    public String eval(String input) {
        try {
            compileGames();
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "logout" -> logout();
                case "create", "c" -> createGame(params);
                case "join", "j" -> joinGame(params);
                case "list" -> listGame();
                case "spectate", "s" -> spectate(params);
                case "quit", "q" -> "quit";
                case "h" -> help();
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
        GameRequest request = new GameRequest(params[0], authToken);
        GameResult result = server.createGame(request);
        state = SIGNEDIN;
        if(result != null) {
            gameIds.put(gameIds.size() + 1, result.gameID());
            return "New game created: " + params[0];
        }
        return null;
    }

    private String listGame() throws ResponseException {
        ListRequest request = new ListRequest(authToken);
        ListResult result = server.listGame(request);

        state = SIGNEDIN;

        chessUi.createList(result.games());
        return "";

    }

    private void compileGames() throws ResponseException {
        ListRequest request = new ListRequest(authToken);
        ListResult result = server.listGame(request);
        int n = 1;
        for(GameData game : result.games()){
            gameIds.put(n, game.gameID());
        }
    }

    private String joinGame(String... params) {
        try {
            int gameId = Integer.parseInt(params[1]);
            String color = params[0].toUpperCase();
            JoinRequest request = new JoinRequest(color, gameIds.get(gameId), authToken);
            JoinResult result = server.joinGame(request);
            state = SIGNEDIN;

            chessUi.createBoard(result.chessGame(), color);

            return "";

        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid gameID:" + params[1]);
        } catch (ResponseException ex) {
            throw new RuntimeException(ex);
        }

    }

    private String spectate(String... params) {
        try {
            int gameId = Integer.parseInt(params[0]);
            JoinRequest request = new JoinRequest("SPECTATE", gameIds.get(gameId), authToken);
            JoinResult result = server.joinGame(request);

            state = SIGNEDIN;

            chessUi.createBoard(result.chessGame(), "WHITE");

            return "";

        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid gameID:" + params[1]);
        } catch (ResponseException ex) {
            throw new RuntimeException(ex);
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
               """;
    }

    public static synchronized PostLoginClient getInstance(){
        return instance;
    }
}
