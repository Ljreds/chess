package server.websocket;

import com.google.gson.Gson;
import facade.ResponseException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.UnauthorizedException;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;


import java.io.IOException;
import java.util.Timer;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        try {
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            String username = getUsername(command.getAuthToken());

            switch (command.getCommandType()) {
                case CONNECT -> connect(command.getAuthToken(), session);
                case MAKE_MOVE -> makeMove(command.getAuthToken(), session);
                case LEAVE -> closeConnection()

            }
        }catch(UnauthorizedException ex){

        }
    }