package dataaccess;

import model.AuthData;

public interface AuthDao {
    void createAuth(String username);
    AuthData getAuth(String username);
    void deleteAuth(String username);
    void clear();
}
