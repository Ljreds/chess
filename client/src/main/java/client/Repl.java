package client;


import websocket.messages.ServerMessage;

import java.util.Scanner;

import static ui.EscapeSequences.*;


public class Repl {
    private Client client;

    public Repl(Client chessClient) {
        client = PreLoginClient.getInstance();
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
                    client.compileGames();
                }else{
                    client = PreLoginClient.getInstance();
                }
            }catch(Throwable ex) {
                var msg = ex.getMessage();
                System.out.println(msg);
            }
        }
        System.out.println();

    }


    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_MAGENTA + ">>> " + SET_TEXT_COLOR_BLUE);
    }
}
