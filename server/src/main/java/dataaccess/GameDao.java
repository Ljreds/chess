package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDao {
    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    void updateBlackUser(int gameID, String blackUsername);
    void updateWhiteUser(int gameID, String whiteUsername);
    void updateGame(int gameID, ChessGame game);
    void clear();
}
