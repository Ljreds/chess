package dataaccess;

import handler.RegisterHandler;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDao{
    private final Map<String, UserData> users = new HashMap<>();
    private static MemoryUserDAO instance;

    @Override
    public void createUser(String username, String password, String email) {
        UserData user = new UserData(username, password, email);
        users.put(username, user);
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public void clear() {
        users.clear();
    }

    public static synchronized MemoryUserDAO getInstance(){
        if(instance == null){
            instance = new MemoryUserDAO();
        }

        return instance;
    }
}
