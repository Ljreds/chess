package handler;

import dataaccess.*;
import request.*;
import response.ErrorResult;
import response.RegisterResult;
import service.*;
import spark.Request;
import spark.Response;

public class RegisterHandler extends Handler<RegisterRequest>{

    private static RegisterHandler instance;

    public RegisterHandler() throws DataAccessException {
    }

    public Object registerHandle(Request request, Response response){
       RegisterRequest regBody = getBody(request, RegisterRequest.class);
       try {

           RegisterResult result = userService.register(regBody);

           response.type("application/json");
           response.status(200);
           return gson.toJson(result);
       }catch(TakenException ex){
           response.type("application/json");
           response.status(403);
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


    public static synchronized RegisterHandler getInstance() throws DataAccessException {
        if(instance == null){
            instance = new RegisterHandler();
        }

        return instance;
    }

}
