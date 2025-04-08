package response;

import chess.ChessGame;

public record JoinResult(ChessGame chessGame, int gameId){
}
