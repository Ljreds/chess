package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GameData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SqlGameDao implements GameDao{
    private static SqlGameDao instance;


    @Override
    public int createGame(String gameName) throws DataAccessException {
        var statement = "INSERT into GameData  (gameID, gameName, game) VALUES (?, ?, ?)";
        ChessGame chessGame = new ChessGame();
        String game = gameSerialize(chessGame);
        Random random = new Random();
        int gameId = 100000 + random.nextInt(900000);
        try(var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement(statement)){
                preparedStatement.setInt(1, gameId);
                preparedStatement.setString(2, gameName);
                preparedStatement.setString(3, game);

                preparedStatement.executeUpdate();
                return gameId;

            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException("unable to create new user");
        }
    }

    private String gameSerialize(ChessGame game){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(game);
    }

    private ChessGame gameDeserialize(String game){
        Gson gson = new Gson();
        return gson.fromJson(game, ChessGame.class);
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
    public void updateGame(int gameID, ChessGame game) {

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
