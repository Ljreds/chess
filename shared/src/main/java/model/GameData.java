package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    public GameData updateGame(ChessGame game) {
        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
    }
    public GameData updateWhiteUser(String newWUsername) {
        return new GameData(gameID, newWUsername, blackUsername, gameName, game);
    }
    public GameData updateBlackUser(String newBUsername) {
        return new GameData(gameID, whiteUsername, newBUsername, gameName, game);
    }
}
