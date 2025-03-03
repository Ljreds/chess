package server;

import handler.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.post("/user", (req, res) -> (RegisterHandler.getInstance().RegisterHandle(req, res)));
        Spark.post("/session", (req, res) -> (LoginHandler.getInstance().LoginHandle(req, res)));
        Spark.delete("/session",(req, res) -> (LogoutHandler.getInstance().LogoutHandle(req, res)));
        Spark.post("/game", (req, res) -> (GameHandler.getInstance().gameHandle(req,res)));
        Spark.get("/game", (req, res) -> (ListHandler.getInstance().listHandle(req, res)));
        Spark.put("/game", (req, res) -> (JoinHandler.getInstance().joinHandle(req, res)));
        Spark.delete("/db", (req, res) -> (ClearHandler.getInstance().clearHandle(req, res)));


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
