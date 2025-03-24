package ui;


import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.State.SIGNEDOUT;


public class Repl {
    private final BaseClient client;
    private Object state = SIGNEDOUT;

    public Repl(String serverUrl) {
        client = new BaseClient(serverUrl);
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
            }catch(Throwable ex) {
                var msg = ex.toString();
                System.out.println(msg);
            }
        }
        System.out.println();

    }


    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_MAGENTA + ">>> " + SET_TEXT_COLOR_BLUE);
    }

}
