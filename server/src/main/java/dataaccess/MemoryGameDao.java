package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;

public class MemoryGameDao implements GameDao{

    private final Map<Integer, GameData> gameMemory = new HashMap<>();
    private static MemoryGameDao instance;

    @Override
    public int createGame(String gameName) {
        Random random = new Random();
        int gameId = 100000 + random.nextInt(900000);
        GameData gameData = new GameData(gameId, null, null, gameName, new ChessGame());
        int gameID = gameData.gameID();
        gameMemory.put(gameID, gameData);
        return gameId;
    }

    @Override
    public GameData getGame(int gameID) {
        return gameMemory.get(gameID);
    }

    public Map<Integer, GameData> getGameMemory() {
        return gameMemory;
    }

    @Override
    public Collection<GameData> listGames() {
        return gameMemory.values();
    }


    @Override
    public void updateBlackUser(int gameID, String blackUsername) {
        GameData gameData = gameMemory.get(gameID);
        GameData newGame = gameData.blackJoin(blackUsername);
        gameMemory.put(gameID, newGame);

    }

    @Override
    public void updateWhiteUser(int gameID, String whiteUsername) {
        GameData gameData = gameMemory.get(gameID);
        GameData newGame = gameData.whiteJoin(whiteUsername);
        gameMemory.put(gameID, newGame);

    }

    @Override
    public void clear() {
        gameMemory.clear();
    }

    public static synchronized MemoryGameDao getInstance(){
        if(instance == null){
            instance = new MemoryGameDao();
        }

        return instance;
    }
}
