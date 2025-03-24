package ui;

import Server.ServerFacade;

import static ui.State.SIGNEDOUT;

public class Client {
    protected String authToken;
    protected ServerFacade server;
    protected String serverUrl;
    protected State state = SIGNEDOUT;

    public Client(String serverUrl) {
        this.serverUrl = serverUrl;
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
