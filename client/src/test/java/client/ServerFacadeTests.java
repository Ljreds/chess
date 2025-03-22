package client;

import dataaccess.*;
import org.junit.jupiter.api.*;
import request.*;
import response.*;
import server.Server;
import Server.*;


import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    private String authToken;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void setUp() throws DataAccessException {
        SqlUserDao.getInstance().createUser("testUser", "testPassword", "test@test.com");
        authToken = SqlAuthDao.getInstance().createAuth("testUser");

    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        SqlAuthDao.getInstance().clear();
        SqlGameDao.getInstance().clear();
        SqlUserDao.getInstance().clear();

    }


    @Test
    public void registerSuccess() throws ResponseException {
        RegisterRequest request = new RegisterRequest("joe_bob", "12345", "joe@byu.edu");
        RegisterResult result = facade.register(request);

        assertTrue(result.authToken().length() > 10);
    }

    @Test
    public void registerFailure(){
        RegisterRequest request = new RegisterRequest(null, "12345", "joe@byu.edu");
        Exception ex = assertThrows(Exception.class, () -> facade.register(request));
        assertEquals("Error: bad request", ex.getMessage());
    }

    @Test
    public void loginSuccess() throws ResponseException {
        LoginRequest request = new LoginRequest("testUser", "testPassword");
        LoginResult result = facade.login(request);

        assertTrue(result.authToken().length() > 10);
    }

    @Test
    public void loginFailure() {
        LoginRequest request = new LoginRequest("testUser", "12345");
        Exception ex = assertThrows(Exception.class, () -> facade.login(request));
        assertEquals("Error: unauthorized", ex.getMessage());
    }

    @Test
    public void logoutSuccess() throws ResponseException {
        LogoutRequest request = new LogoutRequest(authToken);
        LogoutResult result = facade.logout(request);

        assertNotNull(result);
    }

    @Test
    public void logoutFailure() {
        LogoutRequest request = new LogoutRequest(null);
        Exception ex = assertThrows(Exception.class, () -> facade.logout(request));
        assertEquals("Error: unauthorized", ex.getMessage());
    }

    @Test
    public void createGameSuccess() throws ResponseException {
        GameRequest request = new GameRequest("newGame", authToken);
        GameResult result = facade.createGame(request);

        assertInstanceOf(Integer.class, result.gameID());
    }

    @Test
    public void createGameFailure() {
        GameRequest request = new GameRequest("newGame", "1213112");

        Exception ex = assertThrows(Exception.class, () -> facade.createGame(request));
        assertEquals("Error: unauthorized", ex.getMessage());
    }

}
