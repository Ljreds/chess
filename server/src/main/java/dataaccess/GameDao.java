package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDao {
    int createGame(String gameName);
    GameData getGame(int gameID);
    Collection<GameData> listGames();
    void updateBlackUser(int gameID, String blackUsername);
    void updateWhiteUser(int gameID, String whiteUsername);
    void clear();
}
