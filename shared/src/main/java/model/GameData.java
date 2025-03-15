package model;

import chess.ChessGame;


public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    public GameData whiteJoin(String newWhite){
        return new GameData(gameID, newWhite, blackUsername, gameName, game);
    }
    public GameData blackJoin(String newBlack){
        return new GameData(gameID, whiteUsername, newBlack, gameName, game);
    }
    public GameData updateGame(ChessGame newGame){
        return new GameData(gameID, whiteUsername, blackUsername, gameName, newGame);
    }
}
