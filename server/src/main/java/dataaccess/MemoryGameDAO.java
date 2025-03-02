package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;

public class MemoryGameDAO implements GameDao{

    private final Map<Integer, GameData> gameMemory = new HashMap<>();
    private static MemoryGameDAO instance;

    @Override
    public void createGame(String gameName) {

        GameData gameData = new GameData(gameName);
        int gameID = gameData.getGameID();
        gameMemory.put(gameID, gameData);
    }

    @Override
    public GameData getGame(int GameID) {
        return gameMemory.get(GameID);
    }

    @Override
    public GameData getGameByName(String name) {
        for(GameData game : gameMemory.values()){
            if(Objects.equals(game.getName(), name)){
                return gameMemory.get(game.getGameID());
            }
        }
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return gameMemory.values();
    }

    @Override
    public void UpdateGame(int GameID, ChessGame game) {
//        GameData gameData = gameMemory.get(GameID);
//        GameData newgameData = gameData.updateGame(game);
//        gameMemory.put(GameID, newgameData);

    }

    @Override
    public void UpdateBlackUser(int GameID, String blackUsername) {
        GameData gameData = gameMemory.get(GameID);
        gameData.updateBlackUser(blackUsername);
    }

    @Override
    public void UpdateWhiteUser(int GameID, String whiteUsername) {
        GameData gameData = gameMemory.get(GameID);
        gameData.updateWhiteUser(whiteUsername);
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
