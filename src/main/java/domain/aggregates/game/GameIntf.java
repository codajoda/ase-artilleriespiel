package domain.aggregates.game;

import domain.entities.player.Player;
import domain.entities.projectiles.ProjectileType;
import domain.repositories.PlayerRepositoryIntf;
import infrastructure.events.DirectionType;

public interface GameIntf {
    void initGame(PlayerRepositoryIntf playerRepository);
    boolean launchProjectile(int degrees, int strength, ProjectileType projectileType);
    boolean movePlayer(int steps, DirectionType direction);
    Player getCurrentPlayer();
    String[][] getBoard();
    boolean isGameEnd();
    Player getWinner();
}