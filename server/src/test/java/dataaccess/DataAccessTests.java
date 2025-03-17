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
    private final SqlUserDao USER_DAO = SqlUserDao.getInstance();
    private final SqlAuthDao AUTH_DAO = SqlAuthDao.getInstance();
    private final SqlGameDao GAME_DAO = SqlGameDao.getInstance();
    private static String testAuth;
    private static int testGameID;



    public DataAccessTests() throws DataAccessException {
    }


    @BeforeEach
    public void setup() throws DataAccessException {
        USER_DAO.createUser("testUser", "hello1", "test@test.com");
        testAuth = AUTH_DAO.createAuth("testUser");
        testGameID = GAME_DAO.createGame("testGame");
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

        Assertions.assertEquals("Error: unable to create new user", ex.getMessage());


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
        int numDeleted = AUTH_DAO.deleteAuth(testAuth);

        AuthData answer = AUTH_DAO.getAuth("testUser");

        assertNull(answer);
        assertEquals(1, numDeleted);

    }

    @Test
    public void deleteAuthFailure() throws DataAccessException {
        int numDeleted = AUTH_DAO.deleteAuth("test");

        assertEquals(0, numDeleted);
    }

    @Test
    public void createGameSuccess() throws DataAccessException {
        int gameId = GAME_DAO.createGame("Joel");

        GameData answer = GAME_DAO.getGame(gameId);


        assertEquals(gameId, answer.gameID());
        assertNull(answer.whiteUsername());
        assertNull(answer.blackUsername());
        assertEquals("Joel", answer.gameName());
        assertNotNull(answer.game());


    }

    @Test
    public void createGameFailure() {

        Exception ex = assertThrows(Exception.class, () -> GAME_DAO.createGame(null));

        Assertions.assertEquals("Error: unable to create new game", ex.getMessage());


    }

    @Test
    public void getGameSuccess() throws DataAccessException{
        GameData answer = GAME_DAO.getGame(testGameID);

        assertEquals(testGameID, answer.gameID());
        assertNull(answer.whiteUsername());
        assertNull(answer.blackUsername());
        assertEquals("testGame", answer.gameName());
        assertNotNull(answer.game());

    }

    @Test
    public void getGameFailure() throws DataAccessException{
        GameData answer = GAME_DAO.getGame(191);

        assertNull(answer);
    }

    @Test
    public void listGameSuccess() throws DataAccessException{
        Collection<GameData> answer = GAME_DAO.listGames();

       assertNotNull(answer);

    }

    @Test
    public void listGameFailure() throws DataAccessException{
        GAME_DAO.clear();
        Collection<GameData> answer = GAME_DAO.listGames();
        Collection<GameData> expected = new ArrayList<>();
        assertEquals(expected, answer);

    }

    @Test
    public void updateUserSuccessWhite() throws DataAccessException{
        GAME_DAO.updateUser(testGameID, "testUser", "WHITE");

        GameData answer = GAME_DAO.getGame(testGameID);

        assertEquals("testUser", answer.whiteUsername());

    }

    @Test
    public void updateUserSuccessBlack() throws DataAccessException{
        GAME_DAO.updateUser(testGameID, "testUser", "BLACK");

        GameData answer = GAME_DAO.getGame(testGameID);

        assertEquals("testUser", answer.blackUsername());

    }

    @Test
    public void updateUserFailure() throws DataAccessException{
        int answer = GAME_DAO.updateUser(121, "testUser", "BLACK");

        assertEquals(0, answer);


    }

    @Test
    public void updateGameSuccess() throws DataAccessException{
        ChessGame chess = new ChessGame();
        GAME_DAO.updateGame(testGameID, chess);

        GameData answer = GAME_DAO.getGame(testGameID);

        assertEquals(chess, answer.game());

    }

    @Test
    public void updateGameFailure() throws DataAccessException{
        ChessGame chess = new ChessGame();

        int answer = GAME_DAO.updateGame(1234, chess);

        assertEquals(0, answer);

    }

    @Test
    public void userClear() throws DataAccessException{
        USER_DAO.clear();

        UserData answer = USER_DAO.getUser("testUser");

        assertNull(answer);
    }

    @Test
    public void gameClear() throws DataAccessException{
        GAME_DAO.clear();

        GameData answer = GAME_DAO.getGame(testGameID);

        assertNull(answer);
    }

    @Test
    public void authClear() throws DataAccessException{
        AUTH_DAO.clear();

        AuthData answer = AUTH_DAO.getAuth(testAuth);

        assertNull(answer);
    }


}
