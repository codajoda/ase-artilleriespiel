package domain.rules;

import domain.entities.player.Player;
import domain.entities.projectiles.Projectile;

import java.util.List;

public class DamageRule {
    public static boolean execute(List<Player> playersHit, List<Player> players, Projectile projectile) {
        for (Player player : playersHit) {
            player.takeDamage(projectile.getDamage());
            if (player.getTank().getHealth() <= 0) {
                players.remove(player);
            }
        }
        return true;
    }
}
