package websocket;

import chess.ChessGame;
import websocket.messages.*;

public interface NotificationHandler {

    void notify(String message);

    void load(ChessGame chess);
}
