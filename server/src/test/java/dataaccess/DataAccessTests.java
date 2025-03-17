package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;


import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class DataAccessTests {
    private final SqlUserDao userDao = SqlUserDao.getInstance();
    private final SqlAuthDao authDao = SqlAuthDao.getInstance();
    private final SqlGameDao gameDao = SqlGameDao.getInstance();
    private static String testAuth;
    private static int testGameID;



    public DataAccessTests() throws DataAccessException {
    }


    @BeforeEach
    public void setup() throws DataAccessException {
        userDao.createUser("testUser", "hello1", "test@test.com");
        testAuth = authDao.createAuth("testUser");
        testGameID = gameDao.createGame("testGame");
    }

    @AfterEach
    public void tearDown() throws DataAccessException{
        authDao.clear();
        userDao.clear();
        gameDao.clear();
    }


    @Test
    public void createUserSuccess() throws DataAccessException {
        userDao.createUser("ljreds", "12345", "example@example.com");

        UserData answer = userDao.getUser("ljreds");


       assertEquals("ljreds", answer.username());
       assertTrue(BCrypt.checkpw("12345", answer.password()));
       assertEquals("example@example.com", answer.email());

    }

    @Test
    public void createUserFailure() {

        Exception ex = assertThrows(Exception.class, () -> userDao.createUser("testUser", "12345", "example@example.com"));

        Assertions.assertEquals("Error: unable to create new user", ex.getMessage());


    }

    @Test
    public void getUserSuccess() throws DataAccessException {
        UserData answer = userDao.getUser("testUser");

        assertEquals("testUser", answer.username());
        assertTrue(BCrypt.checkpw("hello1", answer.password()));
        assertEquals("test@test.com", answer.email());

    }

    @Test
    public void getUserFailure() throws DataAccessException {
        UserData answer = userDao.getUser("test");

        assertNull(answer);

    }

    @Test
    public void createAuthSuccess() throws DataAccessException {
        String authToken = authDao.createAuth("ljreds");

        AuthData answer = authDao.getAuth(authToken);

        AuthData expected = new AuthData(authToken,"ljreds");

        assertEquals(expected, answer);

    }

    @Test
    public void createAuthFailure() {

        Exception ex = assertThrows(Exception.class, () -> authDao.createAuth(null));

        Assertions.assertEquals("Error: unable to create new authToken", ex.getMessage());


    }

    @Test
    public void getAuthSuccess() throws DataAccessException {
        AuthData answer = authDao.getAuth(testAuth);

        AuthData expected = new AuthData(testAuth, "testUser");

        assertEquals(expected, answer);

    }

    @Test
    public void getAuthFailure() throws DataAccessException {
        AuthData answer = authDao.getAuth("test");

        assertNull(answer);

    }

    @Test
    public void deleteAuthSuccess() throws DataAccessException{
        int numDeleted = authDao.deleteAuth(testAuth);

        AuthData answer = authDao.getAuth("testUser");

        assertNull(answer);
        assertEquals(1, numDeleted);

    }

    @Test
    public void deleteAuthFailure() throws DataAccessException {
        int numDeleted = authDao.deleteAuth("test");

        assertEquals(0, numDeleted);
    }

    @Test
    public void createGameSuccess() throws DataAccessException {
        int gameId = gameDao.createGame("Joel");

        GameData answer = gameDao.getGame(gameId);


        assertEquals(gameId, answer.gameID());
        assertNull(answer.whiteUsername());
        assertNull(answer.blackUsername());
        assertEquals("Joel", answer.gameName());
        assertNotNull(answer.game());


    }

    @Test
    public void createGameFailure() {

        Exception ex = assertThrows(Exception.class, () -> gameDao.createGame(null));

        Assertions.assertEquals("Error: unable to create new game", ex.getMessage());


    }

    @Test
    public void getGameSuccess() throws DataAccessException{
        GameData answer = gameDao.getGame(testGameID);

        assertEquals(testGameID, answer.gameID());
        assertNull(answer.whiteUsername());
        assertNull(answer.blackUsername());
        assertEquals("testGame", answer.gameName());
        assertNotNull(answer.game());

    }

    @Test
    public void getGameFailure() throws DataAccessException{
        GameData answer = gameDao.getGame(191);

        assertNull(answer);
    }

    @Test
    public void listGameSuccess() throws DataAccessException{
        Collection<GameData> answer = gameDao.listGames();

       assertNotNull(answer);

    }

    @Test
    public void listGameFailure() throws DataAccessException{
        gameDao.clear();
        Collection<GameData> answer = gameDao.listGames();
        Collection<GameData> expected = new ArrayList<>();
        assertEquals(expected, answer);

    }

    @Test
    public void updateUserSuccessWhite() throws DataAccessException{
        gameDao.updateUser(testGameID, "testUser", "WHITE");

        GameData answer = gameDao.getGame(testGameID);

        assertEquals("testUser", answer.whiteUsername());

    }

    @Test
    public void updateUserSuccessBlack() throws DataAccessException{
        gameDao.updateUser(testGameID, "testUser", "BLACK");

        GameData answer = gameDao.getGame(testGameID);

        assertEquals("testUser", answer.blackUsername());

    }

    @Test
    public void updateUserFailure() throws DataAccessException{
        int answer = gameDao.updateUser(121, "testUser", "BLACK");

        assertEquals(0, answer);


    }

    @Test
    public void updateGameSuccess() throws DataAccessException{
        ChessGame chess = new ChessGame();
        gameDao.updateGame(testGameID, chess);

        GameData answer = gameDao.getGame(testGameID);

        assertEquals(chess, answer.game());

    }

    @Test
    public void updateGameFailure() throws DataAccessException{
        ChessGame chess = new ChessGame();

        int answer = gameDao.updateGame(1234, chess);

        assertEquals(0, answer);

    }

    @Test
    public void userClear() throws DataAccessException{
        userDao.clear();

        UserData answer = userDao.getUser("testUser");

        assertNull(answer);
    }

    @Test
    public void gameClear() throws DataAccessException{
        gameDao.clear();

        GameData answer = gameDao.getGame(testGameID);

        assertNull(answer);
    }

    @Test
    public void authClear() throws DataAccessException{
        authDao.clear();

        AuthData answer = authDao.getAuth(testAuth);

        assertNull(answer);
    }


}
