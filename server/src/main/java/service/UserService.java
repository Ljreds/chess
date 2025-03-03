package service;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
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
    public RegisterResult register(RegisterRequest registerRequest) {
        String user = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        if(user == null ||password == null||email == null){
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
        if(user.isEmpty()||password.isEmpty()){
            throw new RequestException("Error: bad request");
        }
        else if(userData != null){
            if(userData.password().equals(password)) {
                authDao.createAuth(user);
            }else{
                throw new UnauthorizedException("Error: unauthorized");
            }
            AuthData auth = authDao.getAuth(user);
            return new LoginResult(user, auth.authToken());

        }else{
            throw new UnauthorizedException("Error: unauthorized");
        }
    }

    public LogoutResult logout(LogoutRequest logoutRequest) throws UnauthorizedException {
        String auth = logoutRequest.authToken();
        authDao.getAuthByToken(auth);
        authDao.deleteAuth(auth);
        return new LogoutResult("Thank You");

    }

}
