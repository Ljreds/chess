package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDao;
import dataaccess.MemoryGameDAO;
import request.GameRequest;
import response.ErrorResult;
import response.GameResult;
import service.GameService;
import service.RequestException;
import service.UnauthorizedException;
import spark.Request;
import spark.Response;

public class GameHandler extends Handler<GameRequest>{

    private static GameHandler instance;

    public GameHandler(){
        this.authMemory = MemoryAuthDao.getInstance();
        this.gameMemory = MemoryGameDAO.getInstance();
        this.gson = new Gson();
        this.gameService = new GameService(authMemory, gameMemory);
    }



    public Object gameHandle(Request request, Response response) {
       GameRequest body = getBody(request, GameRequest.class);
       String auth = getAuth(request);
       try{
           GameResult result = gameService.createGame(body, auth);
           response.type("application/json");
           response.status(200);
           return gson.toJson(result);
       }catch(UnauthorizedException ex){
           response.type("application/json");
           response.status(401);
           return gson.toJson(new ErrorResult(ex.getMessage()));
       }catch(RequestException ex){
           response.type("application/json");
           response.status(400);
           return gson.toJson(new ErrorResult(ex.getMessage()));
       }

    }


    public static synchronized GameHandler getInstance(){
        if(instance == null){
            instance = new GameHandler();
        }

        return instance;
    }

}
