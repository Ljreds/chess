package handler;

import com.google.gson.Gson;
import dataaccess.MemoryAuthDao;
import dataaccess.MemoryGameDAO;
import request.ListRequest;
import response.ErrorResult;
import response.ListResult;
import service.GameService;
import service.UnauthorizedException;
import spark.Request;
import spark.Response;

public class ListHandler extends Handler<ListRequest>{

    private static ListHandler instance;

    public ListHandler(){
        this.authMemory = MemoryAuthDao.getInstance();
        this.gameMemory = MemoryGameDAO.getInstance();
        this.gson = new Gson();
        this.gameService = new GameService(authMemory, gameMemory);
    }



    public Object listHandle(Request request, Response response) {
       String auth = getAuth(request);
       try{
           ListResult result = gameService.listGame(auth);
           response.type("application/json");
           response.status(200);
           return gson.toJson(result);
       }catch(UnauthorizedException ex){
           response.type("application/json");
           response.status(401);
           return gson.toJson(new ErrorResult(ex.getMessage()));
       }
    }


    public static synchronized ListHandler getInstance(){
        if(instance == null){
            instance = new ListHandler();
        }

        return instance;
    }

}
