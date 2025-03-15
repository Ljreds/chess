package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import request.*;
import response.*;
import java.util.Collection;
import java.util.Objects;


public class GameService {
    private final AuthDao authDao;
    private final GameDao gameDao;

    public GameService(AuthDao authDao, GameDao gameDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public GameResult createGame(GameRequest gameRequest, String authToken) throws DataAccessException {
        String name = gameRequest.gameName();
        AuthData auth = authDao.getAuth(authToken);
        if(auth == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        if(name.isEmpty()){
            throw new RequestException("Error: bad request");
        }
        int gameId = gameDao.createGame(name);
        return new GameResult(gameId);

    }

    public ListResult listGame(String authToken) throws DataAccessException {
        AuthData auth = authDao.getAuth(authToken);
        if(auth == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        Collection<GameData> list = gameDao.listGames();
        return new ListResult(list);

    }

    public JoinResult joinGame(JoinRequest joinRequest, String authToken) throws DataAccessException {
        int gameID = joinRequest.gameID();
        String playerColor = joinRequest.playerColor();
        AuthData auth = authDao.getAuth(authToken);
        if(auth == null){
            throw new UnauthorizedException("Error: unauthorized");
        }
        GameData gameData = gameDao.getGame(gameID);
        if(gameData == null){
            throw new RequestException("Error: bad request");
        }
        if(Objects.equals(playerColor, "WHITE")){
            if(gameData.whiteUsername() == null) {
                gameDao.updateWhiteUser(gameID, auth.username());
                return new JoinResult();
            }else{
                throw new TakenException("Error: already taken");
            }
        }else if(Objects.equals(playerColor, "BLACK")){
            if(gameData.blackUsername() == null) {
                gameDao.updateBlackUser(gameID, auth.username());
                return new JoinResult();
            }else{
                throw new TakenException("Error: already taken");
            }
        }else{
            throw new RequestException("Error: bad request");
        }
}
}
