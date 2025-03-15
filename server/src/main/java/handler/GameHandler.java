package handler;

import dataaccess.DataAccessException;
import request.GameRequest;
import response.ErrorResult;
import response.GameResult;
import service.RequestException;
import service.UnauthorizedException;
import spark.Request;
import spark.Response;

public class GameHandler extends Handler<GameRequest>{

    private static GameHandler instance;

    public GameHandler() throws DataAccessException {
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
       } catch (DataAccessException ex) {
           response.type("application/json");
           response.status(500);
           return gson.toJson(new ErrorResult(ex.getMessage()));
       }

    }


    public static synchronized GameHandler getInstance() throws DataAccessException {
        if(instance == null){
            instance = new GameHandler();
        }

        return instance;
    }

}
