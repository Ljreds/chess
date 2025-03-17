package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GameData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
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
            throw new DataAccessException("Error: unable to create new game");
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
    public GameData getGame(int gameID) throws DataAccessException {
        var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game from GameData WHERE gameID =?";
        try(var conn = DatabaseManager.getConnection()){
            try(PreparedStatement stmt = conn.prepareStatement(statement)){
                stmt.setInt(1, gameID);
                try(var rs = stmt.executeQuery()) {
                    if(rs.next()) {
                        return createGameData(rs);
                    }
                }
            }

        }catch(SQLException | DataAccessException ex) {
            throw new DataAccessException("Error: unable to access database");

        }
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> gameList = new ArrayList<>();
        var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game from GameData";
        try(var conn = DatabaseManager.getConnection()){
            try(PreparedStatement stmt = conn.prepareStatement(statement)){
                try(var rs = stmt.executeQuery()) {
                    while(rs.next()) {
                        GameData gameData = createGameData(rs);

                        gameList.add(gameData);
                    }

                    return gameList;
                }
            }

        }catch(SQLException | DataAccessException ex) {
            throw new DataAccessException("Error: unable to access database");

        }
    }

    private GameData createGameData(ResultSet rs) throws DataAccessException {
        try{
                var gameId = rs.getInt("gameID");
                var white = rs.getString("whiteUsername");
                var black = rs.getString("blackUsername");
                var name = rs.getString("gameName");
                var game = rs.getString("game");

                ChessGame chess = gameDeserialize(game);

                return new GameData(gameId, white, black, name, chess);

        } catch(SQLException ex) {
            throw new DataAccessException("Error: unable to access database");
        }

    }

    @Override
    public void updateUser(int gameID, String username, String color) throws DataAccessException{
        var statement = "";
        try(var conn = DatabaseManager.getConnection()) {
        if(Objects.equals(color, "WHITE")){
           statement = "UPDATE GameData SET whiteUsername = ? where gameID = ?";
        }else{
            statement = "UPDATE GameData SET blackUsername = ? where gameID = ?";
        }
            try(var preparedStatement = conn.prepareStatement(statement)){
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, gameID);

                preparedStatement.executeUpdate();

            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException("Error: unable to update user");
        }
    }

    @Override
    public void updateGame(int gameID, ChessGame chessGame) throws DataAccessException {
        var statement = "UPDATE GameData SET game = ? where gameID = ?";

        String game = gameSerialize(chessGame);
        try(var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement(statement)){
                preparedStatement.setString(1, game);
                preparedStatement.setInt(2, gameID);

                preparedStatement.executeUpdate();

            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException("Error: unable to update user");
        }
    }


    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE TABLE GameData";
        try(var conn = DatabaseManager.getConnection()){
            try(PreparedStatement stmt = conn.prepareStatement(statement)){
                stmt.executeUpdate();

            }

        }catch(SQLException | DataAccessException ex) {
            throw new DataAccessException("unable to clear database");

        }

    }

    public static synchronized SqlGameDao getInstance(){
        if(instance == null){
            instance = new SqlGameDao();
        }

        return instance;
    }
}
