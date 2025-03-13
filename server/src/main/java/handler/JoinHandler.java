package handler;

import dataaccess.DataAccessException;
import request.JoinRequest;
import response.ErrorResult;
import response.JoinResult;
import service.RequestException;
import service.TakenException;
import service.UnauthorizedException;
import spark.Request;
import spark.Response;

public class JoinHandler extends Handler<JoinRequest>{

    private static JoinHandler instance;

    public JoinHandler() throws DataAccessException {
    }


    public Object joinHandle(Request request, Response response) {
       JoinRequest body = getBody(request, JoinRequest.class);
       String auth = getAuth(request);
       try{
           JoinResult result = gameService.joinGame(body, auth);
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
       }catch(TakenException ex){
           response.type("application/json");
           response.status(403);
           return gson.toJson(new ErrorResult(ex.getMessage()));

       }

    }


    public static synchronized JoinHandler getInstance() throws DataAccessException {
        if(instance == null){
            instance = new JoinHandler();
        }

        return instance;
    }

}
