package websocket.messages;

import chess.ChessGame;
import facade.ResponseException;


public class LoadGame extends ServerMessage {

    private final ChessGame chessGame;
    private final String message;

    public LoadGame(ServerMessageType type, String message, ChessGame chess) {
        super(type);
        this.serverMessageType = type;
        this.message = message;
        this.chessGame = chess;
    }

    @Override
    public ResponseException getException() {
        return null;
    }

    @Override
    public ChessGame getChessGame() {
        return chessGame;
    }

    @Override
    public String getMessage() {
        return message;
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
