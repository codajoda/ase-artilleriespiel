package application.services.gameservices;

import domain.aggregates.game.GameIntf;
import infrastructure.events.MoveEvent;

public class MovePlayerService {

    private final GameIntf game;

    public MovePlayerService(GameIntf game) {
        this.game = game;
    }

    public boolean movePlayer(MoveEvent event) {

        return game.movePlayer(event.getFuel(), event.getDirection());
    }
}
