package model;

import chess.ChessGame;

import java.util.Random;

public class GameData {

    private final int gameID;
    private final String gameName;
    private String whiteUsername;
    private String blackUsername;
    private final ChessGame chessGame;



    public GameData(String gameName) {
        Random random = new Random();
        this.gameName = gameName;
        this.chessGame = new ChessGame();
        this.gameID = 100000 + random.nextInt(900000);
        this.whiteUsername = null;
        this.blackUsername = null;

    }
//    public GameData updateGame (ChessGame game){
//
//    }
    public String getName() {
        return gameName;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public int getGameID() {
        return gameID;
    }

    public void updateWhiteUser (String newWUsername){
        whiteUsername = newWUsername;
    }
    public void updateBlackUser (String newBUsername){
        blackUsername = newBUsername;
    }
}
