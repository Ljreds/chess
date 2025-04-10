package client;


import chess.ChessGame;
import ui.ChessUi;
import websocket.NotificationHandler;

import java.util.Scanner;

import static ui.EscapeSequences.*;


public class Repl implements NotificationHandler {
    private Client client;

    public Repl(Client chessClient){
        client = PreLoginClient.getInstance();
        client.setNotificationHandler(this);
    }

    public void run(){
        System.out.println("Welcome to Chess Game");
        System.out.print(SET_TEXT_COLOR_BLUE + client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while(!result.equals("quit")){
            printPrompt();
            String line = scanner.nextLine();

            try{
                result = client.eval(line);
                System.out.println(result + SET_TEXT_COLOR_BLUE);
                if(client.getState() == State.SIGNEDIN){
                    client = PostLoginClient.getInstance();
                }else if(client.getState() == State.SIGNEDOUT){
                    client = PreLoginClient.getInstance();
                }else{
                    client = InGameClient.getInstance();
                }
            }catch(Throwable ex) {
                var msg = ex.getMessage();
                System.out.println(msg);
            }
        }
        System.out.println();

    }

    @Override
    public void notify(String message) {
        System.out.println(SET_TEXT_COLOR_BLUE + message);
        printPrompt();
    }

    @Override
    public void load(ChessGame chess){
        ChessGame.TeamColor color = Client.getTeamColor();
        ChessUi ui = new ChessUi();
        Client.setGame(chess);
        System.out.println();
        ui.createBoard(chess, color);
        printPrompt();

    }

    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_MAGENTA + ">>> " + SET_TEXT_COLOR_BLUE);
    }


}
