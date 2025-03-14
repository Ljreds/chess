package dataaccess;

import model.UserData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DataAccessTests {
    private final SqlUserDao USER_DAO = SqlUserDao.getInstance();
    private final SqlAuthDao AUTH_DAO = SqlAuthDao.getInstance();
    private final SqlGameDao GAME_DAO = SqlGameDao.getInstance();

    public DataAccessTests() throws DataAccessException {
    }


    @BeforeEach
    public void setup() throws DataAccessException {
        AUTH_DAO.clear();
        USER_DAO.clear();
        GAME_DAO.clear();
        USER_DAO.createUser("testUser", "hello1", "test@test.com");
    }

    @Test
    public void createUserSuccess() throws DataAccessException {
        USER_DAO.createUser("ljreds", "12345", "example@example.com");

        UserData answer = USER_DAO.getUser("ljreds");

        UserData expected = new UserData("ljreds", "12345", "example@example.com");

       assertEquals(expected, answer);

    }

    @Test
    public void createUserFailure() {

        Exception ex = assertThrows(Exception.class, () -> USER_DAO.createUser("testUser", "12345", "example@example.com"));

        Assertions.assertEquals("unable to create new user", ex.getMessage());


    }

    @Test
    public void getUserSuccess() throws DataAccessException {
        UserData answer = USER_DAO.getUser("testUser");

        UserData expected = new UserData("testUser", "hello1", "test@test.com");

        assertEquals(expected, answer);

    }

    @Test
    public void getUserFailure() throws DataAccessException {
        UserData answer = USER_DAO.getUser("test");

        assertNull(answer);

    }


}
