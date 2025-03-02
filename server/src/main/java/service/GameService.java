package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import request.*;
import response.*;


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
}
