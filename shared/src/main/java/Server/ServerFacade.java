package Server;

import com.google.gson.Gson;
import request.*;
import response.*;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url){
        this.serverUrl = url;
    }

    public RegisterResult register(RegisterRequest request) throws ResponseException {
        var path = "/user";
        return makeRequest("POST", path, request, RegisterResult.class);
    }

    public LoginResult login(LoginRequest request) throws ResponseException {
        var path = "/session";
        return makeRequest("POST", path, request, LoginResult.class);
    }








    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException{
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw ResponseException.fromJson(status, respErr);
                }
            }

            throw new ResponseException(status, "Error:" + status);
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if(request != null){
            http.addRequestProperty("Content-Type", "application/json");
            var body = new Gson().toJson(request);
            try(var outputStream = http.getOutputStream()){
                outputStream.write(body.getBytes());

            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> resultClass) throws IOException{
        T result = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (resultClass != null) {
                    result = new Gson().fromJson(reader, resultClass);
                }
            }
        }
        return result;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
