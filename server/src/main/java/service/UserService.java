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
    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        String user = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        if(user == null ||password == null||email == null){
            throw new RequestException("Error: bad request");

        }else if(userDao.getUser(user) == null){
            userDao.createUser(user, password, email);
            String authToken = authDao.createAuth(user);

            return new RegisterResult(user, authToken);
        }else{
            throw new TakenException("Error: already taken");
        }
    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        String user = loginRequest.username();
        String password = loginRequest.password();
        UserData userData = userDao.getUser(user);
        if(user.isEmpty()||password.isEmpty()){
            throw new RequestException("Error: bad request");
        }
        else if(userData != null){
            if(userData.password().equals(password)) {
                String authToken = authDao.createAuth(user);
                return new LoginResult(user, authToken);
            }else{
                throw new UnauthorizedException("Error: unauthorized");
            }
        }else{
            throw new UnauthorizedException("Error: unauthorized");
        }
    }

    public LogoutResult logout(LogoutRequest logoutRequest){
        String authToken = logoutRequest.authToken();
        AuthData auth = authDao.getAuth(authToken);
        if(auth == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        authDao.deleteAuth(authToken);
        return new LogoutResult("");

    }

}
