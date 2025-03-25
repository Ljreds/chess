package client;


import java.util.Scanner;

import static ui.EscapeSequences.*;


public class Repl {
    private Client client;
    private final String serverUrl;

    public Repl(String serverUrl) {
        this.serverUrl = serverUrl;
        client = new PreLoginClient(serverUrl);
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
                    client = new PostLoginClient(serverUrl);
                    System.out.print(SET_TEXT_COLOR_BLUE + client.help());
                }else{
                    client = new PreLoginClient(serverUrl);
                }
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
