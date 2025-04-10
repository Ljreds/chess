package websocket;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facade.ResponseException;
import websocket.commands.CommandAdapter;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.MessageAdapter;
import websocket.messages.ServerMessage;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    private final NotificationHandler notificationHandler;



    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        this.notificationHandler = notificationHandler;
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(ServerMessage.class, new MessageAdapter());
                    Gson gson = builder.create();

                    ServerMessage notification = gson.fromJson(message, ServerMessage.class);
                    eval(notification);


                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }


    public void connect(String authToken, Integer gameId) throws ResponseException {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(UserGameCommand.class, new CommandAdapter());
            Gson gson = builder.create();

            var command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameId);
            this.session.getBasicRemote().sendText(gson.toJson(command));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void makeMove(String authToken, Integer gameId, ChessMove move) throws ResponseException {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(UserGameCommand.class, new CommandAdapter());
            Gson gson = builder.create();

            var command = new MakeMoveCommand(UserGameCommand.CommandType.MAKE_MOVE, authToken, gameId, move);
            this.session.getBasicRemote().sendText(gson.toJson(command));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void leave(String authToken, Integer gameId) throws ResponseException {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(UserGameCommand.class, new CommandAdapter());
            Gson gson = builder.create();

            var command = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameId);
            this.session.getBasicRemote().sendText(gson.toJson(command));
            this.session.close();
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void eval(ServerMessage message){
        switch (message.getServerMessageType()){
            case NOTIFICATION -> notify(message.getMessage());
            case LOAD_GAME -> loadGame(message.getChessGame());
            case ERROR -> error(message.getErrorMessage());
        }
    }

    private void notify(String message) {
        notificationHandler.notify(message);
    }

    private void loadGame(ChessGame game){
        notificationHandler.load(game);

    }

    private void error(String errorMessage){notificationHandler.notify(errorMessage);
    }

}