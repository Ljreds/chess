package websocket.commands;

import chess.ChessMove;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;


public class CommandAdapter extends TypeAdapter<UserGameCommand> {

        @Override
        public void write(JsonWriter jsonWriter, UserGameCommand command) throws IOException {
            Gson gson = new Gson();

            switch(command.getCommandType()) {
                case MAKE_MOVE -> gson.getAdapter(MakeMoveCommand.class).write(jsonWriter, (MakeMoveCommand) command);
                default -> gson.getAdapter(UserGameCommand.class).write(jsonWriter, command);
            }
        }

        @Override
        public UserGameCommand read(JsonReader jsonReader) throws IOException {
            Gson gson = new Gson();
            UserGameCommand.CommandType type = null;
            String authToken = null;
            Integer gameId = null;
            ChessMove chessMove = null;

            jsonReader.beginObject();

            while (jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                switch (name) {
                    case "type" -> type = UserGameCommand.CommandType.valueOf(jsonReader.nextString());
                    case "authToken" -> authToken = jsonReader.nextString();
                    case "gameId" -> gameId = jsonReader.nextInt();
                    case "chessMove" -> chessMove = gson.fromJson(jsonReader, ChessMove.class);
                }
            }

            jsonReader.endObject();

            if(type == null) {
                return null;
            } else {
                return switch (type) {
                    case MAKE_MOVE -> new MakeMoveCommand(type, authToken, gameId, chessMove);
                    default -> new UserGameCommand(type, authToken, gameId);
                };
            }
        }
}

