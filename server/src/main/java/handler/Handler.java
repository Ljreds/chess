package handler;

import com.google.gson.Gson;
import dataaccess.*;
import dataaccess.UserDao;
import request.*;
import response.*;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {

    private static final Gson gson = new Gson();
    private static RegisterHandler instance;
    private final AuthDao authMemory;
    private final UserDao userMemory;
    private final GameDao gameMemory;

    public  RegisterHandler(){
        this.authMemory = MemoryAuthDao.getInstance();
        this.userMemory = MemoryUserDAO.getInstance();
        this.gameMemory = MemoryGameDAO.getInstance();
    }



    @Override
    public Object handle(Request request, Response response) throws Exception {
        RegisterRequest regReq = gson.fromJson(request, RegisterRequest.class);

        UserService service = new UserService(this.userMemory, this.authMemory);
        RegisterResult result = service.register(regReq);

        return gson.toJson(result);

    }

    public static synchronized RegisterHandler getInstance(){
        if(instance == null){
            instance = new RegisterHandler();
        }

        return instance;
    }

}
