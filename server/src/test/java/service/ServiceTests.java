package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.*;
import response.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTests {
    private final MemoryAuthDao AuthDB = new MemoryAuthDao();
    private final MemoryUserDAO UserDB = new MemoryUserDAO();
    private final MemoryGameDAO GameDB = new MemoryGameDAO();


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

    @Test
    public void LogoutSuccess(){
        UserService userService = new UserService(UserDB, AuthDB);
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        userService.register(request);
        AuthData auth = AuthDB.getAuth("ljreds");
        LogoutRequest outRequest = new LogoutRequest(auth.authToken());

        LogoutResult logoutResult = userService.logout(outRequest);

        Assertions.assertEquals(new LogoutResult("Thank You"), logoutResult);

    }

    @Test
    public void LogoutUnauthorized(){
        UserService userService = new UserService(UserDB, AuthDB);


        LogoutRequest outRequest = new LogoutRequest(UUID.randomUUID().toString());


        Exception ex = assertThrows(Exception.class, () -> userService.logout(outRequest));

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());




    }

    @Test
    public void createGameSuccess(){
        UserService userService = new UserService(UserDB, AuthDB);
        GameService gameService = new GameService(AuthDB, GameDB);
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        userService.register(request);
        AuthData auth = AuthDB.getAuth("ljreds");
        GameRequest outRequest = new GameRequest("newGame");

        GameResult gameResult = gameService.createGame(outRequest, auth.authToken());
        int gameID = gameResult.gameID();
        Map<Integer,GameData> map = GameDB.getGameMemory();

        Assertions.assertTrue(map.containsKey(gameID));

    }

    @Test
    public void createGameUnauthorized(){
        GameService gameService = new GameService(AuthDB, GameDB);

        GameRequest outRequest = new GameRequest("newGame");

        Exception ex = assertThrows(Exception.class, () -> gameService.createGame(outRequest, UUID.randomUUID().toString()));

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());
    }

    @Test
    public void createGameBadRequest(){
        UserService userService = new UserService(UserDB, AuthDB);
        GameService gameService = new GameService(AuthDB, GameDB);
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        userService.register(request);
        AuthData auth = AuthDB.getAuth("ljreds");
        GameRequest outRequest = new GameRequest("");

        Exception ex = assertThrows(Exception.class, () -> gameService.createGame(outRequest, auth.authToken()));

        Assertions.assertEquals("Error: bad request", ex.getMessage());
    }

    @Test
    public void listGameSuccess(){
        UserService userService = new UserService(UserDB, AuthDB);
        GameService gameService = new GameService(AuthDB, GameDB);
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        userService.register(request);
        AuthData auth = AuthDB.getAuth("ljreds");

        Collection<GameData> list = GameDB.listGames();
        ListResult listResult = gameService.listGame(auth.authToken());


        Assertions.assertEquals(new ListResult(list), listResult);



    }

    @Test
    public void listGameUnauthorized(){
        GameService gameService = new GameService(AuthDB, GameDB);

        Exception ex = assertThrows(Exception.class, () -> gameService.listGame(UUID.randomUUID().toString()));

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());
    }

}
