package dataaccess;

import model.AuthData;

public interface AuthDao {
    void createAuth(String username);
    AuthData getAuth(String username);
    AuthData getAuthByToken(String authToken) throws DataAccessException;
    void deleteAuth(String username);
    void clear();
}
