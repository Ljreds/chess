package client;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import facade.ResponseException;
import facade.ServerFacade;
import ui.ChessUi;

import java.util.Arrays;

import static client.State.INGAME;
import static client.State.SIGNEDIN;

public class InGameClient extends Client {

    private final ChessUi chessUi;
    private static InGameClient instance;

    private InGameClient(){
        super(serverUrl);
        server = new ServerFacade(serverUrl);
        chessUi = new ChessUi();

    }


    @Override
    public String eval(String input) {
        try {

            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            state = INGAME;
            return switch (cmd) {
                case "move", "m" -> makeMove(params);
                case "highlight", "hl" -> highlight(params);
                case "redraw", "r" -> redraw();
                case "exit", "e" -> leave();
                case "resign" -> resign();
                case "quit", "q" -> "quit";
                case "h", "help" -> help();
                default -> throw new IllegalStateException("Invalid Command: " + cmd + ". Type 'help' for commands");
            };
        }catch(ArrayIndexOutOfBoundsException ex) {
            return "Error: Some inputs left blank";
        }catch(Throwable ex){
            return ex.getMessage();
        }
    }

    private String resign() throws ResponseException {
        ws.resign(authToken, saveGameId);
        return "";
    }

    private String leave() throws ResponseException {
        ws.leave(authToken, saveGameId);
        state = SIGNEDIN;
        return "";
    }

    private String redraw() {
        chessUi.createBoard(game,teamColor);
        return "";
    }

    private String highlight(String[] params) {
        if(params.length == 1){
            ChessPosition position = positionTranslator(params[0]);

        }else{
            return "Error: Parameters are incorrect";
        }

    }

    private String makeMove(String[] params) throws ResponseException {
        if (params.length <= 3 && params.length >=2) {
            try {
                ChessPosition startPosition = positionTranslator(params[0]);
                ChessPosition endPosition = positionTranslator(params[1]);
                ChessPiece.PieceType promotion = null;
                if (params.length == 3) {
                    promotion = ChessPiece.PieceType.valueOf(params[2].toUpperCase());
                }
                ChessMove move = new ChessMove(startPosition, endPosition, promotion);
                ws.makeMove(authToken, saveGameId, move);
                return "";

            }catch(Throwable ex){
                throw new ResponseException(500, ex.getMessage());
            }
        }else{
            return "Error: Parameters are incorrect";
        }
    }


    @Override
    public String help(){
       return """
               Redraw the board: "r", "redraw"
               Make a move on your turn: "m", "move" <Source> <Destination>
               Highlight all available moves: "hl", "highlight" <Piece>
               Leave game: "e", "exit"
               Resign game: "resign"
               Print this message: "h", "help"
               """;
    }

    private ChessPosition positionTranslator(String position){
        char file = position.charAt(0);
        char rank = position.charAt(1);
        int col = file - 'a' + 1;
        int row = rank - '0';

        return new ChessPosition(row, col);


    }

    public static synchronized InGameClient getInstance(){
        if(instance == null){
            instance = new InGameClient();
        }

        return instance;
    }

}
