package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.*;
import response.*;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceTests {

    private static final SqlAuthDao AUTH_MEMORY = new SqlAuthDao();
    private static final SqlUserDao USER_MEMORY;

    static {
        try {
            USER_MEMORY = new SqlUserDao();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static final SqlGameDao GAME_MEMORY = new SqlGameDao();
    private static UserService userService;
    private static GameService gameService;


    @BeforeEach
    public void setup() throws DataAccessException {
        AUTH_MEMORY.clear();
        USER_MEMORY.clear();
        GAME_MEMORY.clear();
        userService = new UserService(USER_MEMORY, AUTH_MEMORY);
        gameService = new GameService(AUTH_MEMORY, GAME_MEMORY);
    }
    @AfterEach
    public void tearDown() throws DataAccessException{
        AUTH_MEMORY.clear();
        USER_MEMORY.clear();
        GAME_MEMORY.clear();
    }



    @Test
    public void registerSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult registerResult = userService.register(request);
        String resultName = registerResult.username();

        assertEquals("ljreds", resultName);



    }

    @Test
    public void registerNameTaken() throws DataAccessException {
        USER_MEMORY.createUser("ljreds", "12345", "kall@gmail.com");
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");


        Exception ex = assertThrows(Exception.class, () -> userService.register(request));

        assertEquals("Error: already taken", ex.getMessage());




    }
    @Test
    public void registerBadRequest(){
        RegisterRequest request = new RegisterRequest("ljreds", null, "JollyGoodFellow@gmail.com");


        Exception ex = assertThrows(Exception.class, () -> userService.register(request));

        assertEquals("Error: bad request", ex.getMessage());




    }

    @Test
    public void loginSuccess() throws DataAccessException {
        USER_MEMORY.createUser("ljreds", "12345", "kall@gmail.com");
        LoginRequest request = new LoginRequest("ljreds", "12345");

        LoginResult loginResult = userService.login(request);
        String resultName = loginResult.username();

        assertEquals("ljreds", resultName);



    }

    @Test
    public void loginBadRequest() throws DataAccessException {
        USER_MEMORY.createUser("ljreds", "12345", "kall@gmail.com");
        LoginRequest request = new LoginRequest(null, "12345");


        Exception ex = assertThrows(Exception.class, () -> userService.login(request));

        assertEquals("Error: bad request", ex.getMessage());




    }

    @Test
    public void loginUnauthorized() throws DataAccessException {
        USER_MEMORY.createUser("ljreds", "12345", "kall@gmail.com");
        LoginRequest request = new LoginRequest("ljreds", "joey221");


        Exception ex = assertThrows(Exception.class, () -> userService.login(request));

        assertEquals("Error: unauthorized", ex.getMessage());




    }

    @Test
    public void logoutSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult result = userService.register(request);
        LogoutRequest outRequest = new LogoutRequest(result.authToken());

        LogoutResult logoutResult = userService.logout(outRequest);

        assertEquals(new LogoutResult(""), logoutResult);

    }

    @Test
    public void logoutUnauthorized(){

        LogoutRequest outRequest = new LogoutRequest(UUID.randomUUID().toString());


        Exception ex = assertThrows(Exception.class, () -> userService.logout(outRequest));

        assertEquals("Error: unauthorized", ex.getMessage());




    }

    @Test
    public void createGameSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult result = userService.register(request);
        GameRequest outRequest = new GameRequest("newGame", result.authToken());

        GameResult gameResult = gameService.createGame(outRequest, result.authToken());
        int gameID = gameResult.gameID();
        GameData gameData = GAME_MEMORY.getGame(gameID);

        assertEquals(gameID, gameData.gameID());
        assertEquals("newGame", gameData.gameName());

    }

    @Test
    public void createGameUnauthorized(){
        GameRequest outRequest = new GameRequest("newGame", UUID.randomUUID().toString());

        Exception ex = assertThrows(Exception.class, () -> gameService.createGame(outRequest, UUID.randomUUID().toString()));

        assertEquals("Error: unauthorized", ex.getMessage());
    }

    @Test
    public void createGameBadRequest() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult regResult = userService.register(request);
        AuthData auth = AUTH_MEMORY.getAuth(regResult.authToken());
        GameRequest outRequest = new GameRequest(null, regResult.authToken());

        Exception ex = assertThrows(Exception.class, () -> gameService.createGame(outRequest, auth.authToken()));

        assertEquals("Error: bad request", ex.getMessage());
    }

    @Test
    public void listGameSuccess() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");

        RegisterResult result = userService.register(request);

        Collection<GameData> list = GAME_MEMORY.listGames();
        ListResult listResult = gameService.listGame(result.authToken());


        assertEquals(new ListResult(list), listResult);



    }

    @Test
    public void listGameUnauthorized(){
        Exception ex = assertThrows(Exception.class, () -> gameService.listGame(UUID.randomUUID().toString()));

        assertEquals("Error: unauthorized", ex.getMessage());
    }

    @Test
    public void joinGameSuccess() throws DataAccessException {
        RegisterRequest regRequest = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");
        RegisterResult regResult = userService.register(regRequest);

        GameResult gameResult = gameService.createGame(new GameRequest("game1", regResult.authToken()), regResult.authToken());

        gameService.joinGame(new JoinRequest("WHITE", gameResult.gameID(), regResult.authToken()), regResult.authToken());
        GameData gameData = GAME_MEMORY.getGame(gameResult.gameID());

        assertEquals("ljreds", gameData.whiteUsername());


    }

    @Test
    public void joinGameBadRequest() throws DataAccessException {
        RegisterRequest regRequest = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");
        RegisterResult regResult = userService.register(regRequest);

        GameResult gameResult = gameService.createGame(new GameRequest("game1", regResult.authToken()), regResult.authToken());

        JoinRequest request = new JoinRequest("W", gameResult.gameID(), regResult.authToken());
        Exception ex = assertThrows(Exception.class, () -> gameService.joinGame(request, regResult.authToken()));

        assertEquals("Error: bad request", ex.getMessage());


    }
    @Test
    public void joinGameUnauthorized() throws DataAccessException {

        RegisterRequest regRequest = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");
        RegisterResult regResult = userService.register(regRequest);

        GameResult gameResult = gameService.createGame(new GameRequest("game1", regResult.authToken()), regResult.authToken());

        String auth = UUID.randomUUID().toString();

        JoinRequest request = new JoinRequest("W", gameResult.gameID(), regResult.authToken());

        Exception ex = assertThrows(Exception.class, () -> gameService.joinGame(request, auth));

        assertEquals("Error: unauthorized", ex.getMessage());


    }

    @Test
    public void clearSuccess() throws DataAccessException {
        RegisterRequest regRequest = new RegisterRequest("ljreds", "12345", "JollyGoodFellow@gmail.com");
        RegisterResult regResult = userService.register(regRequest);

        gameService.createGame(new GameRequest("game1", regResult.authToken()), regResult.authToken());

        ClearService service = new ClearService(USER_MEMORY, AUTH_MEMORY, GAME_MEMORY);

        service.clear();

        assertEquals(0, GAME_MEMORY.listGames().size());




    }

}
