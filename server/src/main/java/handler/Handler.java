package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDao;
import dataaccess.MemoryUserDAO;
import request.RegisterRequest;
import spark.Request;

public class Handler {

    private static Handler instance;
    protected Gson gson = new Gson();
    protected MemoryAuthDao authMemory;
    protected MemoryUserDAO userMemory;

    public Handler() {
        this.authMemory = MemoryAuthDao.getInstance();
        this.userMemory = MemoryUserDAO.getInstance();
    }

    public RegisterRequest getBody(Request request, Class<RegisterRequest> regClass){
        RegisterRequest body = gson.fromJson(request.body(), regClass);
        if(body == null){
            throw new RuntimeException("Error: bad request");
        }
        return body;
    }
}
