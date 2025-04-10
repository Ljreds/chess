package websocket.messages;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonReader;
import facade.ResponseException;

import java.io.IOException;

public class MessageAdapter extends TypeAdapter<ServerMessage> {

        @Override
        public void write(JsonWriter jsonWriter, ServerMessage message) throws IOException {
            Gson gson = new Gson();

            switch(message.getServerMessageType()) {
                case LOAD_GAME -> gson.getAdapter(LoadGame.class).write(jsonWriter, (LoadGame) message);
                case NOTIFICATION -> gson.getAdapter(Notification.class).write(jsonWriter, (Notification) message);
                case ERROR -> gson.getAdapter(Error.class).write(jsonWriter, (Error) message);
            }
        }

        @Override
        public ServerMessage read(JsonReader jsonReader) throws IOException {
            Gson gson = new Gson();
            ServerMessage.ServerMessageType type = null;
            String message = null;
            ChessGame chess = null;
            String exception = null;

            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                switch (name) {
                    case "serverMessageType" -> type = ServerMessage.ServerMessageType.valueOf(jsonReader.nextString());
                    case "message" -> message = jsonReader.nextString();
                    case "game" -> chess = gson.fromJson(jsonReader, ChessGame.class);
                    case "errorMessage" -> exception = jsonReader.nextString();
                }
            }

            jsonReader.endObject();

            if(type == null) {
                return null;
            } else {
                return switch (type) {
                    case ERROR -> new Error(type, exception);
                    case NOTIFICATION -> new Notification(type, message);
                    case LOAD_GAME -> new LoadGame(type, chess);
                };
            }
        }
}

