package application.services.gameservices;

import domain.entities.projectiles.ProjectileType;
import domain.aggregates.game.GameIntf;
import infrastructure.events.LaunchProjectileEvent;

public class LaunchProjectileService {

    private final GameIntf game;

    public LaunchProjectileService(GameIntf game) {
        this.game = game;
    }

    public boolean launchProjectile(LaunchProjectileEvent event) {
        try {
            ProjectileType wt = ProjectileType.fromString(event.getProjectile());
            return game.shoot(event.getDegrees(), event.getStrength(), wt);
        }
        catch (Exception e) {
            return false;
        }
    }
}
