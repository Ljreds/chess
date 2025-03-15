package dataaccess;

import model.AuthData;

public interface AuthDao {
    String createAuth(String username) throws DataAccessException;
    AuthData getAuth(String username) throws DataAccessException;
    int deleteAuth(String username) throws DataAccessException;
    void clear() throws DataAccessException;
}
