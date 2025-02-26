package dataaccess;

import model.UserData;

import java.util.Collection;
import java.util.Map;

public class MemoryUserDAO implements UserDao{
    private Map<String, UserData> users;
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
}
