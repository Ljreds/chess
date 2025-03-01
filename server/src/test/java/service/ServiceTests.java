package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.*;
import response.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTests {
    private final MemoryAuthDao AuthDB = new MemoryAuthDao();
    private final MemoryUserDAO UserDB = new MemoryUserDAO();

    @Test
    public void RegisterSuccess(){
        UserService userService = new UserService(UserDB, AuthDB);
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult registerResult = userService.register(request);
        String resultName = registerResult.username();

        Assertions.assertEquals("ljreds", resultName);



    }

    @Test
    public void RegisterNameTaken(){
        UserDB.createUser("ljreds", "12345", "kall@gmail.com");
        UserService userService = new UserService(UserDB, AuthDB);
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");


        Exception ex = assertThrows(Exception.class, () -> userService.register(request));

        Assertions.assertEquals("Error: already taken", ex.getMessage());




    }
    @Test
    public void RegisterBadRequest(){
        UserDB.createUser("ljreds", "12345", "kall@gmail.com");
        UserService userService = new UserService(UserDB, AuthDB);
        RegisterRequest request = new RegisterRequest("ljreds", "", "JollyGoodFellow@gmail.com");


        Exception ex = assertThrows(Exception.class, () -> userService.register(request));

        Assertions.assertEquals("Error: bad request", ex.getMessage());




    }

    @Test
    public void LoginSuccess(){
        UserDB.createUser("ljreds", "12345", "kall@gmail.com");
        UserService userService = new UserService(UserDB, AuthDB);
        LoginRequest request = new LoginRequest("ljreds", "12345");

        LoginResult loginResult = userService.login(request);
        String resultName = loginResult.username();

        Assertions.assertEquals("ljreds", resultName);



    }

    @Test
    public void LoginBadRequest(){
        UserDB.createUser("ljreds", "12345", "kall@gmail.com");
        UserService userService = new UserService(UserDB, AuthDB);
        LoginRequest request = new LoginRequest("", "12345");


        Exception ex = assertThrows(Exception.class, () -> userService.login(request));

        Assertions.assertEquals("Error: bad request", ex.getMessage());




    }

    @Test
    public void LoginUnauthorized(){
        UserDB.createUser("ljreds", "12345", "kall@gmail.com");
        UserService userService = new UserService(UserDB, AuthDB);
        LoginRequest request = new LoginRequest("ljreds", "joey221");


        Exception ex = assertThrows(Exception.class, () -> userService.login(request));

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());




    }

}
