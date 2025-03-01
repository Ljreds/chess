package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDao;
import dataaccess.MemoryUserDAO;
import request.LoginRequest;
import response.LoginResult;
import service.*;
import spark.Request;
import spark.Response;

public class LoginHandler extends Handler<LoginRequest>{

    private static LoginHandler instance;

    public LoginHandler(){
        this.authMemory = MemoryAuthDao.getInstance();
        this.userMemory = MemoryUserDAO.getInstance();
        this.gson = new Gson();
        this.service = new UserService(userMemory, authMemory);
    }



    public Object LoginHandle(Request request, Response response) {
       LoginRequest regBody = getBody(request, LoginRequest.class);
       try {
           LoginResult result = service.login(regBody);

           response.type("application/json");
           response.status(200);
           return gson.toJson(result);
       }catch(UnauthorizedException ex){
           response.type("application/json");
           response.status(401);
           return gson.toJson(ex.getMessage());
       }catch(RequestException ex){
           response.type("application/json");
           response.status(400);
           return gson.toJson(ex.getMessage());
       }

    }


    public static synchronized LoginHandler getInstance(){
        if(instance == null){
            instance = new LoginHandler();
        }

        return instance;
    }

}
