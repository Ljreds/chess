package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDao {
    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    int updateUser(int gameID, String Username, String color) throws DataAccessException;
    int updateGame(int gameID, ChessGame game) throws DataAccessException;
    void clear() throws DataAccessException;
}
