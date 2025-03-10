package dataaccess;


import model.UserData;

public interface UserDao {
    void createUser(String username, String password, String email);
    UserData getUser(String username);
    void clear();
}
