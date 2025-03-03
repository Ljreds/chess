package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;

public class MemoryGameDAO implements GameDao{

    private final Map<Integer, GameData> gameMemory = new HashMap<>();
    private static MemoryGameDAO instance;

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
    public GameData getGame(int GameID) {
        return gameMemory.get(GameID);
    }

    public Map<Integer, GameData> getGameMemory() {
        return gameMemory;
    }

    @Override
    public Collection<GameData> listGames() {
        return gameMemory.values();
    }

    @Override
    public void updateGame(int GameID, ChessGame game) {
//        GameData gameData = gameMemory.get(GameID);
//        GameData newGameData = gameData.updateGame(game);
//        gameMemory.put(GameID, newGameData);

    }

    @Override
    public void updateBlackUser(int GameID, String blackUsername) {
        GameData gameData = gameMemory.get(GameID);
        GameData newGame = gameData.blackJoin(blackUsername);
        gameMemory.put(GameID, newGame);

    }

    @Override
    public void updateWhiteUser(int GameID, String whiteUsername) {
        GameData gameData = gameMemory.get(GameID);
        GameData newGame = gameData.whiteJoin(whiteUsername);
        gameMemory.put(GameID, newGame);

    }

    @Override
    public void clear() {
        gameMemory.clear();
    }

    public static synchronized MemoryGameDAO getInstance(){
        if(instance == null){
            instance = new MemoryGameDAO();
        }

        return instance;
    }
}
