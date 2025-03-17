package handler;

import dataaccess.*;
import request.LoginRequest;
import response.ErrorResult;
import response.LoginResult;
import service.*;
import spark.Request;
import spark.Response;

public class LoginHandler extends Handler<LoginRequest>{

    private static LoginHandler instance;

    public LoginHandler() throws DataAccessException {
    }

    public Object loginHandle(Request request, Response response) {
       LoginRequest body = getBody(request, LoginRequest.class);
       try {
           LoginResult result = userService.login(body);

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
       }catch(DataAccessException ex){
           response.type("application/json");
           response.status(500);
           return gson.toJson(new ErrorResult(ex.getMessage()));
       }

    }


    public static synchronized LoginHandler getInstance() throws DataAccessException {
        if(instance == null){
            instance = new LoginHandler();
        }

        return instance;
    }

}
