package client;

import facade.ServerFacade;

import static client.State.SIGNEDOUT;

public class Client {
    protected static String authToken;
    protected ServerFacade server;
    protected static String serverUrl;
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
}
