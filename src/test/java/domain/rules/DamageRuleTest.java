package domain.rules;

import domain.entities.player.Player;
import domain.entities.projectiles.CriticalHitProjectileDecorator;
import domain.entities.projectiles.NormalProjectile;
import domain.entities.projectiles.Projectile;
import domain.entities.projectiles.XProjectile;
import domain.entities.tanks.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class DamageRuleTest {

    Projectile projectile;

    @BeforeEach
    void setUp() {
        projectile = spy(new NormalProjectile());
    }

    @Test
    void testIfHitPlayerGetsDamage() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());

        List<Player> players = List.of(currentPlayer, opponentPlayer);
        List<Player> playersHit = List.of(opponentPlayer);

        DamageRule.execute(playersHit, players, projectile);

        Assertions.assertEquals(opponentPlayer.getTank().getMaxHealth() - projectile.getDamage(), opponentPlayer.getHealth());
    }

    @Test
    void testIfKilledPlayerIsThrownOutOfTheGame() {
        Player currentPlayer = mock(Player.class);
        Player opponentPlayer = new Player("Player 2", new BigTank());
        doReturn(opponentPlayer.getHealth())
                .when(projectile).getDamage();

        List<Player> players = new ArrayList<>(List.of(currentPlayer, opponentPlayer));
        List<Player> playersHit = List.of(opponentPlayer);

        DamageRule.execute(playersHit, players, projectile);

        Assertions.assertEquals(opponentPlayer.getHealth(), opponentPlayer.getTank().getMaxHealth() - projectile.getDamage());
        Assertions.assertEquals(List.of(currentPlayer), players);
    }

    @Test
    void testCriticalHit() {
        Player currentPlayer = new Player("Player 1", new SmallTank());
        Player opponentPlayer = new Player("Player 2", new BigTank());

        List<Player> players = List.of(currentPlayer, opponentPlayer);
        List<Player> playersHit = List.of(opponentPlayer);

        CriticalHitProjectileDecorator criticalProjectile = new CriticalHitProjectileDecorator(projectile);
        DamageRule.execute(playersHit, players, criticalProjectile);

        Assertions.assertEquals(projectile.getDamage() * 2, criticalProjectile.getDamage());
        Assertions.assertEquals(opponentPlayer.getTank().getMaxHealth() - projectile.getDamage() * 2, opponentPlayer.getHealth());
    }
}