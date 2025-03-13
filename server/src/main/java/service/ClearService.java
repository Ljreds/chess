package service;

import dataaccess.*;

public class ClearService {

    private final AuthDao auth;
    private final GameDao game;
    private final UserDao user;

    public ClearService(UserDao user, AuthDao auth, GameDao game){
        this.user = user;
        this.auth = auth;
        this.game = game;
    }

    public void clear() throws DataAccessException {
        user.clear();
        auth.clear();
        game.clear();
    }
}
