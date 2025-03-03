package dataaccess;

import model.AuthData;
import service.UnauthorizedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MemoryAuthDao implements AuthDao {
    private final Map<String, AuthData> authMemory = new HashMap<>();
    private static MemoryAuthDao instance;


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
    public AuthData getAuthByToken(String authToken) {
        for(AuthData auth : authMemory.values()){
            if(Objects.equals(auth.authToken(), authToken)){
                return auth;
            }
        }
        throw new UnauthorizedException("Error: unauthorized");
    }

    @Override
    public void deleteAuth(String authToken) {
        for(AuthData auth : authMemory.values()){
            if(Objects.equals(auth.authToken(), authToken)){
                authMemory.remove(auth.username());
            }
        }
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
