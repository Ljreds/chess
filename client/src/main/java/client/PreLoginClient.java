package client;

import facade.ResponseException;
import facade.ServerFacade;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResult;
import response.RegisterResult;

import java.util.Arrays;

import static client.State.SIGNEDIN;


public class PreLoginClient extends Client {

    private static PreLoginClient instance;

    private PreLoginClient() {
        super(serverUrl);
        Client.serverUrl = serverUrl;
        server = new ServerFacade(serverUrl);
    }

    @Override
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register", "r" -> register(params);
                case "login", "l" -> login(params);
                case "quit", "q" -> "quit";
                case "h", "help" -> help();
                default -> throw new IllegalStateException("Invalid command: " + cmd + ". Type 'help' for commands");
            };
        }catch(ArrayIndexOutOfBoundsException ex) {
            return "Error: Some inputs left blank";
        }catch(Throwable ex){
            return ex.getMessage();
        }
    }

    public String register(String...params) throws ResponseException {
        if(params.length == 3) {
            RegisterRequest request = new RegisterRequest(params[0], params[1], params[2]);
            RegisterResult result = server.register(request);
            authToken = result.authToken();
            state = SIGNEDIN;
            return "You are now signed in has: " + result.username();
        }else{
            return "Error: Parameters are incorrect";
        }

    }

    public String login(String...params) throws ResponseException {
        if(params.length == 2) {
            LoginRequest request = new LoginRequest(params[0], params[1]);
            LoginResult result = server.login(request);
            authToken = result.authToken();
            state = SIGNEDIN;
            return "You are now signed in has: " + result.username();
        }else{
            return "Error: Parameters are incorrect";
        }

    }

    @Override
    public String help(){
        return """
                Register new user: "r", "register" <USERNAME> <PASSWORD> <EMAIL>
                Login: "l", "login" <USERNAME> <PASSWORD>
                Quit program: "q" "quit"
                Print this message: "h", "help"
                """;


    }

    public static synchronized PreLoginClient getInstance(){
        if(instance == null){
            instance = new PreLoginClient();
        }

        return instance;
    }


}
