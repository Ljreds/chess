package ui;

import Server.ResponseException;
import Server.ServerFacade;
import request.*;
import response.*;

import java.util.Arrays;

import static ui.State.SIGNEDOUT;

public class PostLoginClient extends Client {

    public PostLoginClient(String serverUrl) {
        super(serverUrl);
        this.serverUrl = serverUrl;
        server = new ServerFacade(serverUrl);
    }


    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "logout" -> logout();
                case "create", "c" -> createGame(params);
                case "join", "j" -> joinGame(params);
                case "list", "l" -> listGame(params);
                case "spectate", "s" -> spectate(params);
                case "quit", "q" -> "quit";
                default -> help();

            };
        }catch(Throwable ex){
            return ex.getMessage();
        }
    }

    private String logout() throws ResponseException {
        System.out.println(authToken);
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
        if(result != null) {
            return "New game created: " + params[0];
        }
        return null;
    }

    private String listGame(String... params) {
        return null;
    }
    private String joinGame(String... params) {
        return null;
    }

    private String spectate(String... params) {
        return null;
    }

    public String help(){
       return """
               Logout: "logout";
               Create a new game: "c", "create" <GAME NAME>
               List all games: "l", "list"
               Join a game: "j", "join" <gameID> <Player Color>
               Spectate a game: "s", "spectate" <gameID>
               """;
    }
}
