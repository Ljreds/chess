package dataaccess;

import model.AuthData;
import service.UnauthorizedException;

public interface AuthDao {
    void createAuth(String username);
    AuthData getAuth(String username);
    AuthData getAuthByToken(String authToken) throws UnauthorizedException;
    void deleteAuth(String username);
    void clear();
}
