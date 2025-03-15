package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;


import static org.junit.jupiter.api.Assertions.*;

public class DataAccessTests {
    private final SqlUserDao USER_DAO = SqlUserDao.getInstance();
    private final SqlAuthDao AUTH_DAO = SqlAuthDao.getInstance();
    private final SqlGameDao GAME_DAO = SqlGameDao.getInstance();
    private static String testAuth;

    public DataAccessTests() throws DataAccessException {
    }


    @BeforeEach
    public void setup() throws DataAccessException {
        USER_DAO.createUser("testUser", "hello1", "test@test.com");
        testAuth = AUTH_DAO.createAuth("testUser");
    }

    @AfterEach
    public void tearDown() throws DataAccessException{
        AUTH_DAO.clear();
        USER_DAO.clear();
        GAME_DAO.clear();
    }


    @Test
    public void createUserSuccess() throws DataAccessException {
        USER_DAO.createUser("ljreds", "12345", "example@example.com");

        UserData answer = USER_DAO.getUser("ljreds");


       assertEquals("ljreds", answer.username());
       assertTrue(BCrypt.checkpw("12345", answer.password()));
       assertEquals("example@example.com", answer.email());

    }

    @Test
    public void createUserFailure() {

        Exception ex = assertThrows(Exception.class, () -> USER_DAO.createUser("testUser", "12345", "example@example.com"));

        Assertions.assertEquals("unable to create new user", ex.getMessage());


    }

    @Test
    public void getUserSuccess() throws DataAccessException {
        UserData answer = USER_DAO.getUser("testUser");

        assertEquals("testUser", answer.username());
        assertTrue(BCrypt.checkpw("hello1", answer.password()));
        assertEquals("test@test.com", answer.email());

    }

    @Test
    public void getUserFailure() throws DataAccessException {
        UserData answer = USER_DAO.getUser("test");

        assertNull(answer);

    }

    @Test
    public void createAuthSuccess() throws DataAccessException {
        String authToken = AUTH_DAO.createAuth("ljreds");

        AuthData answer = AUTH_DAO.getAuth(authToken);

        AuthData expected = new AuthData(authToken,"ljreds");

        assertEquals(expected, answer);

    }

    @Test
    public void createAuthFailure() {

        Exception ex = assertThrows(Exception.class, () -> AUTH_DAO.createAuth(null));

        Assertions.assertEquals("Error: unable to create new authToken", ex.getMessage());


    }

    @Test
    public void getAuthSuccess() throws DataAccessException {
        AuthData answer = AUTH_DAO.getAuth(testAuth);

        AuthData expected = new AuthData(testAuth, "testUser");

        assertEquals(expected, answer);

    }

    @Test
    public void getAuthFailure() throws DataAccessException {
        AuthData answer = AUTH_DAO.getAuth("test");

        assertNull(answer);

    }

    @Test
    public void deleteAuthSuccess() throws DataAccessException{
        int numDeleted = AUTH_DAO.deleteAuth("testUser");

        AuthData answer = AUTH_DAO.getAuth("testUser");

        assertNull(answer);
        assertEquals(1, numDeleted);

    }

    @Test
    public void deleteAuthFailure() throws DataAccessException {
        int numDeleted = AUTH_DAO.deleteAuth("test");

        assertEquals(0, numDeleted);
    }


}
