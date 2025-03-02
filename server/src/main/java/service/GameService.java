package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import request.*;
import response.*;

import java.util.Objects;


public class GameService {
    private final UserDao userDao;
    private final AuthDao authDao;
    private final GameDao gameDao;

    public GameService(UserDao userDao, AuthDao authDao, GameDao gameDao) {
        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public GameResult createGame(GameRequest gameRequest) throws DataAccessException {
        String auth = gameRequest.authToken();
        String name = gameRequest.gameName();
        AuthData authData = authDao.getAuthByToken(auth);
        if(name.isEmpty()){
            throw new RequestException("Error: bad request");
        }else if(authData != null){
            gameDao.createGame(name);
            GameData gameData = gameDao.getGameByName(name);
            return new GameResult(gameData.getGameID());
        }else{
            throw new UnauthorizedException("Error: unauthorized");
        }

    }

    public ListResult listGame(ListRequest listRequest) throws DataAccessException {
        String auth = listRequest.authToken();
        AuthData authData = authDao.getAuthByToken(auth);
        if(authData != null){
            return new ListResult(gameDao.listGames());
        }else{
            throw new UnauthorizedException("Error: unauthorized");
        }

    }

    public JoinResult joinGame(JoinRequest joinRequest) throws DataAccessException {
        String auth = joinRequest.authToken();
        int gameID = joinRequest.gameID();
        String playerColor = joinRequest.playerColor();
        AuthData authData = authDao.getAuthByToken(auth);
        if(authData != null){
            GameData gameData = gameDao.getGame(gameID);
            if(Objects.equals(playerColor, "White")){
                gameData.updateWhiteUser(authData.username());
                return new JoinResult();
            }else if(Objects.equals(playerColor, "Black")){
                gameData.updateBlackUser(authData.username());
                return new JoinResult();
            }else{
                throw new RequestException("Error: bad request");
            }
        }else{
            throw new UnauthorizedException("Error: Unauthorized");
        }
    }
}
