package dataaccess;

import model.AuthData;

import java.sql.SQLException;

public class SqlAuthDao implements AuthDao{
    private static SqlAuthDao instance;

    @Override
    public String createAuth(String username) {
        return "";
    }

    @Override
    public AuthData getAuth(String username) {
        return null;
    }

    @Override
    public void deleteAuth(String username) {

    }

    @Override
    public void clear() {

    }

    public static synchronized SqlAuthDao getInstance(){
        if(instance == null){
            instance = new SqlAuthDao();
        }

        return instance;
    }

}
