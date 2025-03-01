package handler;

import com.google.gson.Gson;
import dataaccess.*;
import request.*;
import response.RegisterResult;
import service.*;
import spark.Request;
import spark.Response;

public class RegisterHandler extends Handler{

    private static RegisterHandler instance;

    public  RegisterHandler(){
        this.authMemory = MemoryAuthDao.getInstance();
        this.userMemory = MemoryUserDAO.getInstance();
        this.gson = new Gson();
    }



    public Object RegisterHandle(Request request, Response response) throws Exception {
       RegisterRequest regBody = getBody(request, RegisterRequest.class);
       UserService service = new UserService(userMemory, authMemory);
       try {
           RegisterResult result = service.register(regBody);

           response.type("application/json");
           response.status(200);
           return gson.toJson(result);
       }catch(TakenException ex){
           response.type("application/json");
           response.status(403);
           return gson.toJson(ex.getMessage());
       }catch(RequestException ex){
           response.type("application/json");
           response.status(400);
           return gson.toJson(ex.getMessage());
       }

    }


    public static synchronized RegisterHandler getInstance(){
        if(instance == null){
            instance = new RegisterHandler();
        }

        return instance;
    }

}
