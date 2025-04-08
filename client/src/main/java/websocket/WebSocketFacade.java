package websocket;
import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facade.ResponseException;
import ui.ChessUi;
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
    private ServerMessage.ServerMessageType type;
    private ChessUi ui = new ChessUi();



    public WebSocketFacade(String url, NotificationHandler handler) throws ResponseException {
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
                    String notify = eval(notification);
                    handler.notify(notify);


                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public ServerMessage.ServerMessageType getType() {
        return type;
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void connect(String authToken, Integer gameId) throws ResponseException {
        try {
            var command = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameId);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private String eval(ServerMessage message){
        return switch (message.getServerMessageType()){
            case NOTIFICATION -> message.getMessage();
            case LOAD_GAME -> loadGame(message.getMessage(), message.getChessGame());
            case ERROR -> error(message.getException());
        };
    }

    private String loadGame(String message, ChessGame game){
        ui.createBoard(game, "WHITE");
        return null;

    }

    private String error(ResponseException ex){
        return null;
    }

}