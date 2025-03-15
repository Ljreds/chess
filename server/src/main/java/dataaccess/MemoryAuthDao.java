package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MemoryAuthDao implements AuthDao {
    private final Map<String, AuthData> authMemory = new HashMap<>();
    private static MemoryAuthDao instance;


    @Override
    public String createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        authMemory.put(authToken, authData);
        return authToken;
    }

    @Override
    public AuthData getAuth(String authToken) {
        return authMemory.get(authToken);
    }

    @Override
    public int deleteAuth(String authToken) {
        authMemory.remove(authToken);
        return 1;
    }

    @Override
    public void clear() {
        authMemory.clear();
    }

    public static synchronized MemoryAuthDao getInstance(){
        if(instance == null){
            instance = new MemoryAuthDao();
        }

        return instance;
    }
}
