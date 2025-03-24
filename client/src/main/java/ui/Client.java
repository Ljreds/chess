package ui;

import Server.ServerFacade;

public class Client {
    protected String authToken;
    protected ServerFacade server;
    protected String serverUrl;

    public Client(String serverUrl) {
        this.serverUrl = serverUrl;
    }


}
