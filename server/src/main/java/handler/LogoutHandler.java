package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDao;
import dataaccess.MemoryUserDAO;
import request.LogoutRequest;
import response.LogoutResult;
import service.UnauthorizedException;
import service.UserService;
import spark.Request;
import spark.Response;

public class LogoutHandler extends Handler<LogoutRequest>{

    private static LogoutHandler instance;

    public LogoutHandler(){
        this.authMemory = MemoryAuthDao.getInstance();
        this.userMemory = MemoryUserDAO.getInstance();
        this.gson = new Gson();
        this.service = new UserService(userMemory, authMemory);
    }



    public Object LogoutHandle(Request request, Response response) {
       String auth = getAuth(request);
       LogoutRequest logoutRequest = new LogoutRequest(auth);
       try {
           LogoutResult result = service.logout(logoutRequest);

           response.type("application/json");
           response.status(200);
           return gson.toJson(result);
       }catch(DataAccessException ex){
           response.type("application/json");
           response.status(500);
           return gson.toJson(ex.getMessage());
       }catch(UnauthorizedException ex){
           response.type("application/json");
           response.status(401);
           return gson.toJson(ex.getMessage());
       }

    }


    public static synchronized LogoutHandler getInstance(){
        if(instance == null){
            instance = new LogoutHandler();
        }

        return instance;
    }

}
