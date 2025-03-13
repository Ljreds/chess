package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataAccessTests {
    private final SqlUserDao USER_DAO = SqlUserDao.getInstance();
    private final SqlAuthDao AUTH_DAO = SqlAuthDao.getInstance();
    private final SqlGameDao GAME_DAO = SqlGameDao.getInstance();

    public DataAccessTests() throws DataAccessException {
    }


    @BeforeEach
    public void setup() {
        AUTH_DAO.clear();
        USER_DAO.clear();
        GAME_DAO.clear();

    }

    @Test
    public void CreateUserSuccess() throws DataAccessException {
        USER_DAO.createUser("ljreds", "12345", "example@example.com");

        UserData answer = USER_DAO.getUser("ljreds");

        UserData expected = new UserData("ljreds", "12345", "example@example.com");

       assertEquals(expected, answer);

    }


}
