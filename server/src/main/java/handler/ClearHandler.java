package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDao;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import request.ClearRequest;
import request.GameRequest;
import response.ClearResult;
import response.GameResult;
import service.ClearService;
import service.RequestException;
import service.UnauthorizedException;
import spark.Request;
import spark.Response;

public class ClearHandler extends Handler<ClearRequest>{

    private static ClearHandler instance;

    public ClearHandler(){
        this.authMemory = MemoryAuthDao.getInstance();
        this.gameMemory = MemoryGameDAO.getInstance();
        this.userMemory = MemoryUserDAO.getInstance();
        this.gson = new Gson();
        this.clearService = new ClearService(userMemory, authMemory, gameMemory);
    }



    public Object clearHandle(Request request, Response response) {

           clearService.clear();
           ClearResult result = new ClearResult();
           response.type("application/json");
           response.status(200);
           return gson.toJson(result);

    }


    public static synchronized ClearHandler getInstance(){
        if(instance == null){
            instance = new ClearHandler();
        }

        return instance;
    }

}
