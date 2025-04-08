package websocket.messages;

import facade.ResponseException;


public class Error extends ServerMessage {

    private final ResponseException exception;


    public Error(ServerMessageType type, ResponseException exception){
        super(type);
        this.serverMessageType = type;
        this.exception = exception;
    }

    public ResponseException getException() {
        return exception;
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
