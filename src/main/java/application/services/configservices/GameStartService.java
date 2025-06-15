package application.services.configservices;

import domain.aggregates.game.GameIntf;
import domain.repositories.PlayerRepositoryIntf;

public class GameStartService {

    private final PlayerRepositoryIntf playerRepository;
    private final GameIntf game;

    public GameStartService(PlayerRepositoryIntf playerRepository, GameIntf game) {
        this.playerRepository = playerRepository;
        this.game = game;
    }

    public boolean start() {
        game.initGame(playerRepository);
        return true;
    }
}
