package server.websocket;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.SqlAuthDao;
import facade.ResponseException;
import model.AuthData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.UnauthorizedException;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;


import java.io.IOException;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private SqlAuthDao authDao = SqlAuthDao.getInstance();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            String username = getUsername(command.getAuthToken());

            switch (command.getCommandType()) {
                case CONNECT -> connect(username, session, command);
                case MAKE_MOVE -> makeMove(command.getAuthToken(), session);
                case LEAVE -> closeConnection();
                case RESIGN -> resign();

            }
        } catch(UnauthorizedException | ResponseException ex) {

        }
    }

    private void connect(String visitorName, Session session, UserGameCommand command) throws IOException {
        connections.add(visitorName, session, command.getGameID());
        var message = String.format("%s is in the shop", visitorName);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        connections.broadcast(visitorName, notification);
    }

    private String getUsername(String authToken) throws ResponseException {
        try {
            AuthData authData = authDao.getAuth(authToken);
            return authData.username();

        } catch (DataAccessException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


}