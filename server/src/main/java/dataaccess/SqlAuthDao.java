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
            throw new DataAccessException("unable to create new authToken");
        }
        return authToken;
    }

    @Override
    public AuthData getAuth(String username) throws DataAccessException{
        var statement = "SELECT authToken, username from AuthData WHERE username =?";
        try(var conn = DatabaseManager.getConnection()){
            try(PreparedStatement stmt = conn.prepareStatement(statement)){
                stmt.setString(1, username);
                try(var rs = stmt.executeQuery()) {
                    if(rs.next()) {
                        var auth = rs.getString("username");
                        var user = rs.getString("username");

                        return new AuthData(auth, user);
                    }
                }
            }
        }catch(SQLException | DataAccessException ex) {
            throw new DataAccessException("unable to access database");

        }
        return null;
    }

    @Override
    public void deleteAuth(String username) {

    }

    @Override
    public void clear() {

    }

    public static synchronized SqlAuthDao getInstance(){
        if(instance == null){
            instance = new SqlAuthDao();
        }

        return instance;
    }

}
