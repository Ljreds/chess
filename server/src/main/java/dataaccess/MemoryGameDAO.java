package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDao{

    private final Map<Integer, GameData> gameMemory = new HashMap<>();

    @Override
    public void createGame(int GameID, String whiteUsername, String blackUsername, String gameName, ChessGame chessGame) {
        GameData gameData = new GameData(GameID, whiteUsername, blackUsername, gameName, chessGame);
        gameMemory.put(GameID, gameData);
    }

    @Override
    public GameData getGame(int GameID) {
        return gameMemory.get(GameID);
    }

    @Override
    public Collection<GameData> getAllGames() {
        return gameMemory.values();
    }

    @Override
    public void UpdateGame(int GameID, ChessGame game) {
        GameData gameData = gameMemory.get(GameID);
        GameData newgameData = gameData.updateGame(game);
        gameMemory.put(GameID, newgameData);

    }

    @Override
    public void UpdateBlackUser(int GameID, String blackUsername) {
        GameData gameData = gameMemory.get(GameID);
        GameData newgameData = gameData.updateBlackUser(blackUsername);
        gameMemory.put(GameID, newgameData);
    }

    @Override
    public void UpdateWhiteUser(int GameID, String whiteUsername) {
        GameData gameData = gameMemory.get(GameID);
        GameData newgameData = gameData.updateWhiteUser(whiteUsername);
        gameMemory.put(GameID, newgameData);
    }

    @Override
    public void clear() {
        gameMemory.clear();
    }
}
