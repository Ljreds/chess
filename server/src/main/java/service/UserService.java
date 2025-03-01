package service;

import dataaccess.AuthDao;
import dataaccess.UserDao;
import model.*;
import request.*;
import response.*;

public class UserService {

    private final UserDao userDao;
    private final AuthDao authDao;

    public UserService(UserDao userDao, AuthDao authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
    }
    public RegisterResult register(RegisterRequest registerRequest) throws Exception {
        String user = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        if(user.isEmpty() ||password.isEmpty()||email.isEmpty()){
            throw new RequestException("Error: bad request");

        }else if(userDao.getUser(user) == null){
            userDao.createUser(user, password, email);
            authDao.createAuth(user);

            AuthData auth = authDao.getAuth(user);
            return new RegisterResult(user, auth.authToken());
        }else{
            throw new TakenException("Error: already taken");
        }
    }
    public LoginResult login(LoginRequest loginRequest){
        String user = loginRequest.username();
        String password = loginRequest.password();
        UserData userData = userDao.getUser(user);
        if(userData != null){
            if(userData.password().equals(password)) {
                authDao.createAuth(user);
            }
            AuthData auth = authDao.getAuth(user);
            return new LoginResult(user, auth.authToken());
        }else{
            return null;
        }
    }
    public LogoutResult register(LogoutRequest logoutRequest){
        String auth = logoutRequest.authToken();
        if(authDao.getAuth(auth) != null){
            authDao.deleteAuth(auth);
            return new LogoutResult();
        }
        return null;
    }

}
