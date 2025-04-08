package client;

import facade.ResponseException;
import facade.ServerFacade;
import websocket.WebSocketFacade;

import static client.State.SIGNEDOUT;

public class Client {
    protected static String authToken;
    protected ServerFacade server;
    protected static String serverUrl;
    protected static WebSocketFacade ws;
    protected State state = SIGNEDOUT;

    public Client(String serverUrl) {
        Client.serverUrl = serverUrl;
    }

    public State getState() {
        return state;
    }

    public String eval(String line) {
        return null;
    }

    public String help() {
        return null;
    }

    public void compileGames() throws ResponseException {

    }

    public void setNotificationHandler(Repl repl) {

    }
}
