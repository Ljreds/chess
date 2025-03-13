package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.List;

public class SqlGameDao implements GameDao{
    private static SqlGameDao instance;


    @Override
    public int createGame(String gameName) {
        return 0;
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return List.of();
    }

    @Override
    public void updateBlackUser(int gameID, String blackUsername) {

    }

    @Override
    public void updateWhiteUser(int gameID, String whiteUsername) {

    }

    @Override
    public void clear() {

    }

    public static synchronized SqlGameDao getInstance(){
        if(instance == null){
            instance = new SqlGameDao();
        }

        return instance;
    }
}
