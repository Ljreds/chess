package websocket.messages;

import chess.ChessGame;
import facade.ResponseException;


public class Error extends ServerMessage {

    private final ResponseException exception;


    public Error(ServerMessageType type, ResponseException exception){
        super(type);
        this.serverMessageType = type;
        this.exception = exception;
    }

    @Override
    public ResponseException getException() {
        return exception;
    }

    @Override
    public ChessGame getChessGame() {
        return null;
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
        if (!(o instanceof Error that)) {
            return false;
        }
        return getServerMessageType() == that.getServerMessageType();
    }

}
