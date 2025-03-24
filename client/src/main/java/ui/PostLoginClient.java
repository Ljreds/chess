package ui;

import Server.ServerFacade;

import java.util.Arrays;

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
                case "quit", "q" -> "quit";
                default -> help();

            };
        }catch(Throwable ex){
            return ex.getMessage();
        }
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
