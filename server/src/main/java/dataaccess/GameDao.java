package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDao {
    void createGame(int GameID, String whiteUsername, String blackUsername, String gameName, ChessGame chessGame);
    GameData getGame(int GameID);
    Collection<GameData> getAllGames();
    void UpdateGame(int GameID, ChessGame game);
    void UpdateBlackUser(int GameID, String blackUsername);
    void UpdateWhiteUser(int GameID, String whiteUsername);
    void clear();
}
