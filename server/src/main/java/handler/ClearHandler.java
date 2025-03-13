package handler;


import request.ClearRequest;
import response.ClearResult;
import spark.Request;
import spark.Response;

public class ClearHandler extends Handler<ClearRequest>{

    private static ClearHandler instance;


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
