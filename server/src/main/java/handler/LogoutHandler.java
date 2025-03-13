package handler;

import request.LogoutRequest;
import response.ErrorResult;
import response.LogoutResult;
import service.UnauthorizedException;
import spark.Request;
import spark.Response;

public class LogoutHandler extends Handler<LogoutRequest>{

    private static LogoutHandler instance;



    public Object logoutHandle(Request request, Response response) {
       String auth = getAuth(request);
       LogoutRequest logoutRequest = new LogoutRequest(auth);
       try {
           LogoutResult result = service.logout(logoutRequest);

           response.type("application/json");
           response.status(200);
           return gson.toJson(result);
       }catch(UnauthorizedException ex){
           response.type("application/json");
           response.status(401);
           return gson.toJson(new ErrorResult(ex.getMessage()));
       }

    }


    public static synchronized LogoutHandler getInstance(){
        if(instance == null){
            instance = new LogoutHandler();
        }

        return instance;
    }

}
