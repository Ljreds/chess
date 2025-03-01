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
    public void RegisterTestSuccess() throws Exception {
        UserService userService = new UserService(UserDB, AuthDB);
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult registerResult = userService.register(request);
        String resultName = registerResult.username();

        Assertions.assertEquals("ljreds", resultName);



    }

    @Test
    public void RegisterTestNameTaken(){
        UserDB.createUser("ljreds", "12345", "kall@gmail.com");
        UserService userService = new UserService(UserDB, AuthDB);
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");


        Exception ex = assertThrows(Exception.class, () -> userService.register(request), "Error: already taken");

        Assertions.assertEquals("Error: already taken", ex.getMessage());




    }

}
