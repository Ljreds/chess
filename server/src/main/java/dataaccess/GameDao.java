package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDao {
    void createGame(String gameName);
    GameData getGame(int GameID);

    GameData getGameByName(String name);

    Collection<GameData> listGames();
    void UpdateGame(int GameID, ChessGame game);
    void UpdateBlackUser(int GameID, String blackUsername);
    void UpdateWhiteUser(int GameID, String whiteUsername);
    void clear();
}
