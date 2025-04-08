package websocket.messages;

import chess.ChessGame;

import java.util.Objects;


public class LoadGame extends ServerMessage {

    private final ChessGame chessGame;

    public LoadGame(ServerMessageType type, ChessGame chess) {
        super(type);
        this.serverMessageType = type;
        this.chessGame = chess;
    }

    public ChessGame getChessGame() {
        return chessGame;
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
