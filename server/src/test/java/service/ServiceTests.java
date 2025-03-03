package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.*;
import response.*;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTests {

    private static final MemoryAuthDao authDB = new MemoryAuthDao();
    private static final MemoryUserDAO userDB = new MemoryUserDAO();
    private static final MemoryGameDAO gameDB = new MemoryGameDAO();;
    private static UserService userService;
    private static GameService gameService;


    @BeforeEach
    public void setup() {
        authDB.clear();
        userDB.clear();
        gameDB.clear();
        userService = new UserService(userDB, authDB);
        gameService = new GameService(authDB, gameDB);
    }



    @Test
    public void RegisterSuccess(){
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult registerResult = userService.register(request);
        String resultName = registerResult.username();

        Assertions.assertEquals("ljreds", resultName);



    }

    @Test
    public void RegisterNameTaken(){
        userDB.createUser("ljreds", "12345", "kall@gmail.com");
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");


        Exception ex = assertThrows(Exception.class, () -> userService.register(request));

        Assertions.assertEquals("Error: already taken", ex.getMessage());




    }
    @Test
    public void RegisterBadRequest(){
        RegisterRequest request = new RegisterRequest("ljreds", null, "JollyGoodFellow@gmail.com");


        Exception ex = assertThrows(Exception.class, () -> userService.register(request));

        Assertions.assertEquals("Error: bad request", ex.getMessage());




    }

    @Test
    public void LoginSuccess(){
        userDB.createUser("ljreds", "12345", "kall@gmail.com");
        LoginRequest request = new LoginRequest("ljreds", "12345");

        LoginResult loginResult = userService.login(request);
        String resultName = loginResult.username();

        Assertions.assertEquals("ljreds", resultName);



    }

    @Test
    public void LoginBadRequest(){
        userDB.createUser("ljreds", "12345", "kall@gmail.com");
        LoginRequest request = new LoginRequest("", "12345");


        Exception ex = assertThrows(Exception.class, () -> userService.login(request));

        Assertions.assertEquals("Error: bad request", ex.getMessage());




    }

    @Test
    public void LoginUnauthorized(){
        userDB.createUser("ljreds", "12345", "kall@gmail.com");
        LoginRequest request = new LoginRequest("ljreds", "joey221");


        Exception ex = assertThrows(Exception.class, () -> userService.login(request));

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());




    }

    @Test
    public void LogoutSuccess(){
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult result = userService.register(request);
        LogoutRequest outRequest = new LogoutRequest(result.authToken());

        LogoutResult logoutResult = userService.logout(outRequest);

        Assertions.assertEquals(new LogoutResult(""), logoutResult);

    }

    @Test
    public void LogoutUnauthorized(){

        LogoutRequest outRequest = new LogoutRequest(UUID.randomUUID().toString());


        Exception ex = assertThrows(Exception.class, () -> userService.logout(outRequest));

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());




    }

    @Test
    public void createGameSuccess(){
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult result = userService.register(request);
        GameRequest outRequest = new GameRequest("newGame");

        GameResult gameResult = gameService.createGame(outRequest, result.authToken());
        int gameID = gameResult.gameID();
        Map<Integer,GameData> map = gameDB.getGameMemory();

        Assertions.assertTrue(map.containsKey(gameID));

    }

    @Test
    public void createGameUnauthorized(){
        GameRequest outRequest = new GameRequest("newGame");

        Exception ex = assertThrows(Exception.class, () -> gameService.createGame(outRequest, UUID.randomUUID().toString()));

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());
    }

    @Test
    public void createGameBadRequest(){
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        userService.register(request);
        AuthData auth = authDB.getAuth("ljreds");
        GameRequest outRequest = new GameRequest("");

        Exception ex = assertThrows(Exception.class, () -> gameService.createGame(outRequest, auth.authToken()));

        Assertions.assertEquals("Error: bad request", ex.getMessage());
    }

    @Test
    public void listGameSuccess(){
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult result = userService.register(request);

        Collection<GameData> list = gameDB.listGames();
        ListResult listResult = gameService.listGame(result.authToken());


        Assertions.assertEquals(new ListResult(list), listResult);



    }

    @Test
    public void listGameUnauthorized(){
        Exception ex = assertThrows(Exception.class, () -> gameService.listGame(UUID.randomUUID().toString()));

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());
    }

    @Test
    public void joinGameSuccess(){
        RegisterRequest regRequest = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");
        RegisterResult regResult = userService.register(regRequest);

        GameResult gameResult = gameService.createGame(new GameRequest("game1"), regResult.authToken());

        gameService.joinGame(new JoinRequest("WHITE", gameResult.gameID()), regResult.authToken());
        GameData gameData = gameDB.getGame(gameResult.gameID());

        Assertions.assertEquals("ljreds", gameData.whiteUsername());


    }

    @Test
    public void joinGameBadRequest(){
        RegisterRequest regRequest = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");
        RegisterResult regResult = userService.register(regRequest);

        GameResult gameResult = gameService.createGame(new GameRequest("game1"), regResult.authToken());

        Exception ex = assertThrows(Exception.class, () -> gameService.joinGame(new JoinRequest("W", gameResult.gameID()), regResult.authToken()));

        Assertions.assertEquals("Error: bad request", ex.getMessage());


    }
    @Test
    public void joinGameUnauthorized(){

        RegisterRequest regRequest = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");
        RegisterResult regResult = userService.register(regRequest);

        GameResult gameResult = gameService.createGame(new GameRequest("game1"), regResult.authToken());

        String authToken =  UUID.randomUUID().toString();

        Exception ex = assertThrows(Exception.class, () -> gameService.joinGame(new JoinRequest("W", gameResult.gameID()), authToken));

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());


    }

    @Test
    public void clearSuccess(){
        RegisterRequest regRequest = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");
        RegisterResult regResult = userService.register(regRequest);

        gameService.createGame(new GameRequest("game1"), regResult.authToken());

        ClearService service = new ClearService(userDB, authDB, gameDB);

        service.clear();

        Assertions.assertEquals(0, gameDB.listGames().size());




    }

}
