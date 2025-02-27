package service;

import dataaccess.AuthDao;
import dataaccess.UserDao;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import response.LoginResult;
import response.RegisterResult;
import response.LogoutResult;

public class UserService {

    private final UserDao userDao;
    private final AuthDao authDao;

    public UserService(UserDao userDao, AuthDao authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
    }
    public RegisterResult register(RegisterRequest registerRequest){
        String user = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        if(userDao.getUser(user) == null){
            userDao.createUser(user, password, email);
            authDao.createAuth(user);
        }else{

        }
    }
    public LoginResult login(LoginRequest loginRequest){
        return null;
    }
    public LogoutResult register(LogoutRequest logoutRequest){
        return null;
    }

}
