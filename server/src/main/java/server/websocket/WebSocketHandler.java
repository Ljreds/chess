package server.websocket;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataaccess.DataAccessException;
import dataaccess.SqlAuthDao;
import dataaccess.SqlGameDao;
import facade.ResponseException;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.UnauthorizedException;
import websocket.commands.CommandAdapter;
import websocket.commands.UserGameCommand;
import websocket.messages.*;
import websocket.messages.Error;


import java.io.IOException;
import java.util.Objects;

import static chess.ChessGame.TeamColor.WHITE;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final SqlAuthDao authDao = SqlAuthDao.getInstance();
    private final SqlGameDao gameDao = SqlGameDao.getInstance();


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        String username = null;
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(UserGameCommand.class, new CommandAdapter());
            Gson gson = builder.create();

            UserGameCommand command = gson.fromJson(message, UserGameCommand.class);
            username = getUsername(command.getAuthToken());
            if(username == null){
                throw new UnauthorizedException("Error: unauthorized");
            }

            switch (command.getCommandType()) {
                case CONNECT -> connect(username, session, command);
                case MAKE_MOVE -> makeMove(username, command);
                case LEAVE -> closeConnection(username, command);
                case RESIGN -> resign(username, session, command);

            }
        } catch (Throwable ex) {
            var error = new Error(ServerMessage.ServerMessageType.ERROR, ex.getMessage());
            connections.configure(username, error);
        }
    }

    private void resign(String username, Session session, UserGameCommand command) {
    }

    private void closeConnection(String visitorName, UserGameCommand command) throws DataAccessException, IOException {
        connections.remove(visitorName);
        int gameId = command.getGameID();
        GameData gameData = gameDao.getGame(gameId);

        if(Objects.equals(gameData.whiteUsername(), visitorName)){
            gameDao.updateUser(gameId, null, "WHITE");

        }else if(Objects.equals(gameData.blackUsername(), visitorName)){
            gameDao.updateUser(gameId, null, "BLACK");

        }

        String message = String.format("%s has left the game.", visitorName);

        var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(visitorName, notification, command.getGameID());
    }

    private void makeMove(String visitorName, UserGameCommand command) throws ResponseException, IOException {
        try {
            int gameId = command.getGameID();
            GameData gameData = gameDao.getGame(gameId);
            ChessGame game = gameData.game();
            ChessMove move = command.getChessMove();
            ChessPosition start = move.getStartPosition();
            ChessPosition end = move.getEndPosition();
            ChessPiece piece = game.getBoard().getPiece(start);
            if(game.getGameStatus() == ChessGame.Status.GAME_OVER){
                throw new InvalidMoveException("Error: Game is over");
            }else{
                game.makeMove(command.getChessMove());
            }
            gameDao.updateGame(gameId, game);

            ChessGame.TeamColor teamColor = null;
            String message;

            if(game.isInCheckmate(WHITE)){
                message = gameData.whiteUsername() + " is in checkmate.";
            }else if(game.isInCheckmate(ChessGame.TeamColor.BLACK)){
                message = gameData.blackUsername() + " is in checkmate.";
            }else if(game.isInCheck(WHITE)){
                message = gameData.whiteUsername() + " is in check.";
            }else if(game.isInCheck(ChessGame.TeamColor.BLACK)){
                message = gameData.blackUsername() + " is in check.";
            }else if(game.isInStalemate(WHITE)){
                message = gameData.whiteUsername() + " is in stalemate.";
            }else if(game.isInStalemate(ChessGame.TeamColor.BLACK)){
                message = gameData.blackUsername() + " is in stalemate.";
            }else{
                String first = positionTranslator(start);
                String last = positionTranslator(end);
                message = String.format("%s moves from %s to %s", piece, first, last);
            }


            var loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, gameData.game());
            connections.broadcast("", loadGame, gameId);

            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast("", notification, command.getGameID());
        } catch (DataAccessException ex) {
            throw new ResponseException(500, ex.getMessage());
        } catch(InvalidMoveException ex){
            var error = new Error(ServerMessage.ServerMessageType.ERROR, ex.getMessage());
            connections.configure(visitorName, error);

        }
    }

    private String positionTranslator(ChessPosition position){
        int row = position.getRow();
        int col = position.getColumn();
        char file = (char) (col - 1 + 'A');
        String fileString = String.valueOf(file);
        String rowString = String.valueOf(row);
        return fileString.toLowerCase() + rowString;
    }


    private void connect(String visitorName, Session session, UserGameCommand command) throws IOException {
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

            var loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME, gameData.game());
            connections.configure(visitorName, loadGame);
            var notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(visitorName, notification, command.getGameID());
        }catch(Throwable ex){
            var error = new Error(ServerMessage.ServerMessageType.ERROR, "Error: Cannot connect to game");
            connections.configure(visitorName, error);
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