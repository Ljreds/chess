package server.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.DataAccessException;
import dataaccess.SqlAuthDao;
import dataaccess.SqlGameDao;
import facade.ResponseException;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.UnauthorizedException;
import websocket.commands.UserGameCommand;
import websocket.messages.*;


import javax.websocket.OnOpen;
import java.io.IOException;
import java.util.Objects;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final SqlAuthDao authDao = SqlAuthDao.getInstance();
    private SqlGameDao gameDao = SqlGameDao.getInstance();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket connected");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        try {
            System.out.println(message);
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(ServerMessage.class, new MessageAdapter());
            Gson gson = builder.create();

            UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
            String username = getUsername(command.getAuthToken());

            switch (command.getCommandType()) {
                case CONNECT -> connect(username, session, command);
                case MAKE_MOVE -> makeMove(username, session, command);
                case LEAVE -> closeConnection(username, session, command);
                case RESIGN -> resign(username, session, command);

            }
        } catch(UnauthorizedException | ResponseException ex) {

        }
    }

    private void resign(String username, Session session, UserGameCommand command) {
    }

    private void closeConnection(String username, Session session, UserGameCommand command) {
    }

    private void makeMove(String username, Session session, UserGameCommand command) {
    }


    private void connect(String visitorName, Session session, UserGameCommand command) throws ResponseException {
        try {
            connections.add(visitorName, session, command.getGameID());
            GameData gameData = gameDao.getGame(command.getGameID());
            String teamColor = "";
            if(Objects.equals(gameData.whiteUsername(), visitorName)){
                teamColor = " white team";
            }else if(Objects.equals(gameData.blackUsername(), visitorName)){
                teamColor = " black team";
            }
            String message = String.format("%s has joined %s.", visitorName, teamColor);
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(visitorName, notification, command.getGameID());
        }catch(Throwable ex){
            throw new ResponseException(500, ex.getMessage());
        }
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