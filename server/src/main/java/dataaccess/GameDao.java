package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDao {
    int createGame(String gameName);
    GameData getGame(int GameID);
    Collection<GameData> listGames();
    void updateGame(int GameID, ChessGame game);
    void updateBlackUser(int GameID, String blackUsername);
    void updateWhiteUser(int GameID, String whiteUsername);
    void clear();
}
