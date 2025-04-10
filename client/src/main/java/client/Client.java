package client;

import chess.ChessGame;
import facade.ResponseException;
import facade.ServerFacade;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.HashMap;
import java.util.Map;

import static client.State.SIGNEDOUT;

public class Client {
    protected static String authToken;
    protected ServerFacade server;
    protected static String serverUrl;
    protected static WebSocketFacade ws;
    protected State state = SIGNEDOUT;
    protected static NotificationHandler notificationHandler;
    protected static final Map<Integer, Integer> GAME_IDS = new HashMap<>();
    protected static ChessGame.TeamColor teamColor = null;
    protected static int saveGameId;

    public static void setGame(ChessGame game) {
        Client.game = game;
    }

    protected static ChessGame game = null;

    public static ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

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

    public void setNotificationHandler(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }
}
