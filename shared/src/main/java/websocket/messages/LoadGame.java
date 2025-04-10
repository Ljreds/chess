package websocket.messages;

import chess.ChessGame;
import facade.ResponseException;


public class LoadGame extends ServerMessage {

    private final ChessGame game;

    public LoadGame(ServerMessageType type, ChessGame chess) {
        super(type);
        this.serverMessageType = type;
        this.game = chess;
    }


    @Override
    public String getErrorMessage() {
        return "";
    }

    @Override
    public ChessGame getChessGame() {
        return game;
    }

    @Override
    public String getMessage() {
        return "";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoadGame that)) {
            return false;
        }
        return getServerMessageType() == that.getServerMessageType();
    }

}
