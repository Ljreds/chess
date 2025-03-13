package dataaccess;

import model.UserData;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlUserDao implements UserDao{

    private static SqlUserDao instance;

    @Override
    public void createUser(String username, String password, String email) throws DataAccessException {
        var statement = "INSERT into UserData (username, password, email) VALUES (?, ?, ?)";
        try(var conn = DatabaseManager.getConnection()) {
            try(var preparedStatement = conn.prepareStatement(statement)){
                preparedStatement.setString(0, username);
                preparedStatement.setString(1, password);
                preparedStatement.setString(2, email);

                preparedStatement.executeUpdate();

            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException("Unable to configure database: %s");
        }

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        var statement = "SELECT username, password, email from UserData WHERE username =?";
        try(var conn = DatabaseManager.getConnection()){
            try(PreparedStatement stmt = conn.prepareStatement(statement)){


                var user = stmt.getString("username");

            }

        }catch(SQLException | DataAccessException ex) {
            throw new DataAccessException("Unable to configure database: %s");

        }
    }

    @Override
    public void clear() {

    }

    public static synchronized SqlUserDao getInstance(){
        if(instance == null){
            instance = new SqlUserDao();
        }

        return instance;
    }

}
