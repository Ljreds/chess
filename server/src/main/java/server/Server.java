package server;

import com.google.gson.Gson;
import handler.*;
import spark.*;

import java.util.Map;

public class Server {

    public int run(int desiredPort){

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");


        Spark.post("/user", (req, res) -> (RegisterHandler.getInstance().registerHandle(req, res)));
        Spark.post("/session", (req, res) -> (LoginHandler.getInstance().loginHandle(req, res)));
        Spark.delete("/session",(req, res) -> (LogoutHandler.getInstance().logoutHandle(req, res)));
        Spark.post("/game", (req, res) -> (GameHandler.getInstance().gameHandle(req,res)));
        Spark.get("/game", (req, res) -> (ListHandler.getInstance().listHandle(req, res)));
        Spark.put("/game", (req, res) -> (JoinHandler.getInstance().joinHandle(req, res)));
        Spark.delete("/db", (req, res) -> (ClearHandler.getInstance().clearHandle(req, res)));

        Spark.exception(Exception.class, this::errorHandler);


        Spark.awaitInitialization();
        return Spark.port();
    }


    public Object errorHandler(Exception e, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        res.type("application/json");
        res.status(500);
        res.body(body);
        return body;
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
