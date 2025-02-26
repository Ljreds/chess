package dataaccess;

import model.AuthData;

import java.util.Map;
import java.util.UUID;

public class MemoryAuthDao implements AuthDao {
    private Map<String, AuthData> authMemory;

    @Override
    public void createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        authMemory.put(username, authData);
    }

    @Override
    public AuthData getAuth(String username) {
        return authMemory.get(username);
    }

    @Override
    public void deleteAuth(String username) {
        authMemory.remove(username);
    }

    @Override
    public void clear() {
        authMemory.clear();
    }
}
