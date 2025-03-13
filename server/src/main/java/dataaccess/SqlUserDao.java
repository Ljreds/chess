package dataaccess;

import model.UserData;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlUserDao implements UserDao{

    private static SqlUserDao instance;

    public SqlUserDao() throws DataAccessException {
        configureDatabase();
    }

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
                var rs = stmt.executeQuery();
                var user = rs.getString("username");
                var password = rs.getString("password");
                var email = rs.getString("email");

                return new UserData(user, password, email);

            }

        }catch(SQLException | DataAccessException ex) {
            throw new DataAccessException("Unable to configure database: %s");

        }
    }

    @Override
    public void clear() {

    }

    public static synchronized SqlUserDao getInstance() throws DataAccessException {
        if(instance == null){
            instance = new SqlUserDao();
        }

        return instance;
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  UserData (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256),
              PRIMARY KEY (`username`)
            )
            """,
            """
            CREATE TABLE IF NOT EXISTS  AuthData (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            )
            """,
            """
            CREATE TABLE IF NOT EXISTS  AuthData (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            )
            """,

    };

    public void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

}
