package dataaccess;

import model.AuthData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class SqlAuthDao implements AuthDao{
    private static SqlAuthDao instance;

    @Override
    public String createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        var statement = "INSERT into AuthData (authToken, username) VALUES (?, ?)";
        try(var conn = DatabaseManager.getConnection()) {
            try(PreparedStatement stmt = conn.prepareStatement(statement)){

                stmt.setString(1, authToken);
                stmt.setString(2, username);
                stmt.executeUpdate();

            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException("Error: unable to create new authToken");
        }
        return authToken;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException{
        var statement = "SELECT authToken, username from AuthData WHERE authToken =?";
        try(var conn = DatabaseManager.getConnection()){
            try(PreparedStatement stmt = conn.prepareStatement(statement)){
                stmt.setString(1, authToken);
                try(var rs = stmt.executeQuery()) {
                    if(rs.next()) {
                        var auth = rs.getString("authToken");
                        var user = rs.getString("username");

                        return new AuthData(auth, user);
                    }
                }
            }
        }catch(SQLException | DataAccessException ex) {
            throw new DataAccessException("Error: unable to access database");

        }
        return null;
    }

    @Override
    public int deleteAuth(String username) throws DataAccessException {
        var statement = "DELETE FROM AuthData WHERE username =?";
        try(var conn = DatabaseManager.getConnection()){
            try(PreparedStatement stmt = conn.prepareStatement(statement)){
                stmt.setString(1, username);
                return stmt.executeUpdate();
            }
        }catch(SQLException | DataAccessException ex) {
            throw new DataAccessException("Error: unable to access database");

        }
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE TABLE AuthData";
        try(var conn = DatabaseManager.getConnection()){
            try(PreparedStatement stmt = conn.prepareStatement(statement)){
                stmt.executeUpdate();

            }

        }catch(SQLException | DataAccessException ex) {
            throw new DataAccessException("unable to clear database");

        }

    }

    public static synchronized SqlAuthDao getInstance(){
        if(instance == null){
            instance = new SqlAuthDao();
        }

        return instance;
    }

}
