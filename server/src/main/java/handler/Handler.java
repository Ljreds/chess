package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDao;
import dataaccess.MemoryUserDAO;
import service.UserService;
import spark.Request;

public class Handler<T> {

    protected Gson gson = new Gson();
    protected MemoryAuthDao authMemory;
    protected MemoryUserDAO userMemory;
    protected UserService service;


    public Handler() {
        this.authMemory = MemoryAuthDao.getInstance();
        this.userMemory = MemoryUserDAO.getInstance();
    }

    public T getBody(Request request, Class<T> regClass){
        var body = gson.fromJson(request.body(), regClass);
        if(body == null){
            throw new RuntimeException("Error: bad request");
        }
        return body;
    }
}
