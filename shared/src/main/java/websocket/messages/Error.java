package websocket.messages;

import chess.ChessGame;


public class Error extends ServerMessage {

    private final String errorMessage;


    public Error(ServerMessageType type, String errorMessage){
        super(type);
        this.serverMessageType = type;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
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
