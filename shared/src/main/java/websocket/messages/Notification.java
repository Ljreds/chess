package websocket.messages;


import chess.ChessGame;
import facade.ResponseException;

public class Notification extends ServerMessage {

    private final String message;


    public Notification(ServerMessageType type, String message){
        super(type);
        this.serverMessageType = type;
        this.message = message;
    }

    @Override
    public ResponseException getException() {
        return null;
    }

    @Override
    public ChessGame getChessGame() {
        return null;
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
        if (!(o instanceof Notification that)) {
            return false;
        }
        return getServerMessageType() == that.getServerMessageType();
    }

}
