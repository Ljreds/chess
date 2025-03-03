package handler;

import com.google.gson.Gson;
import dataaccess.*;
import service.*;
import spark.Request;

public class Handler<T> {

    protected Gson gson = new Gson();
    protected MemoryAuthDao authMemory;
    protected MemoryUserDAO userMemory;
    protected MemoryGameDAO gameMemory;
    protected UserService service;
    protected GameService gameService;
    protected ClearService clearService;


    public Handler() {
        this.authMemory = MemoryAuthDao.getInstance();
        this.userMemory = MemoryUserDAO.getInstance();
        this.gameMemory = MemoryGameDAO.getInstance();

    }

    public T getBody(Request request, Class<T> regClass){
        var body = gson.fromJson(request.body(), regClass);
        if(body == null){
            throw new RuntimeException("Error: bad request");
        }
        return body;
    }

    public String getAuth(Request request){
        return request.headers("Authorization");
    }
}
