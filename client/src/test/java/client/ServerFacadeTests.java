package client;

import dataaccess.*;
import org.junit.jupiter.api.*;
import request.RegisterRequest;
import response.RegisterResult;
import server.Server;
import Server.*;


import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

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

    @AfterEach
    public void tearDown() throws DataAccessException {
        SqlAuthDao.getInstance().clear();
        SqlGameDao.getInstance().clear();
        SqlUserDao.getInstance().clear();

    }


    @Test
    public void registerSuccess() throws ResponseException {
        RegisterRequest request = new RegisterRequest("testUser", "12345", "joe@byu.edu");
        RegisterResult result = facade.register(request);

        assertTrue(result.authToken().length() > 10);
    }

    @Test
    public void registerFailure() throws ResponseException {
        RegisterRequest request = new RegisterRequest(null, "12345", "joe@byu.edu");
        Exception ex = assertThrows(Exception.class, () -> facade.register(request));
        assertEquals("Error: bad request", ex.getMessage());
    }

}
