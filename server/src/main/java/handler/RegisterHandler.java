package handler;

import com.google.gson.Gson;
import dataaccess.*;
import dataaccess.UserDao;
import org.eclipse.jetty.server.Authentication;
import request.*;
import response.RegisterResult;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {

    private static RegisterHandler instance;
    private final Gson gson = new Gson();
    private final MemoryAuthDao authMemory;
    private final MemoryUserDAO userMemory;

    public  RegisterHandler(){
        this.authMemory = MemoryAuthDao.getInstance();
        this.userMemory = MemoryUserDAO.getInstance();
    }



    @Override
    public Object handle(Request request, Response response) throws Exception {
       RegisterRequest regBody = getBody(request, RegisterRequest.class);
       UserService service = new UserService(userMemory, authMemory);
       RegisterResult result = service.register(regBody);

       response.type("application/json");
       response.status(200);
       return gson.toJson(result);
    }

    public RegisterRequest getBody(Request request, Class<RegisterRequest> regClass){
        RegisterRequest body = gson.fromJson(request.body(), regClass);
        if(body == null){
            throw new RuntimeException("Error: bad request");
        }
        return body;
    }

    public static synchronized RegisterHandler getInstance(){
        if(instance == null){
            instance = new RegisterHandler();
        }

        return instance;
    }

}
