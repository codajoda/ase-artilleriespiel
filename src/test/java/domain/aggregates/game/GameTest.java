package domain.aggregates.game;

import domain.entities.board.Board;
import domain.entities.board.BoardIntf;
import domain.entities.inventory.InventoryItem;
import domain.entities.player.Player;
import domain.entities.tanks.BigTank;
import domain.entities.projectiles.ProjectileType;
import domain.repositories.PlayerRepositoryIntf;
import infrastructure.events.DirectionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.*;

class GameTest {
    GameIntf game;
    Player currentPlayer;
    Player opponentPlayer;
    PlayerRepositoryIntf playerRepository;
    BoardIntf board;

    @BeforeEach
    void setUp() {
        board = new Board(100, 20, 1);
        game = new Game(board);
        currentPlayer = new Player("Player 1", new BigTank());
        opponentPlayer = new Player("Player 2", new BigTank());
        playerRepository = mock(PlayerRepositoryIntf.class);
        when(playerRepository.getPlayers()).thenReturn(new ArrayList<>(List.of(currentPlayer, opponentPlayer)));
        game.initGame(playerRepository);
        currentPlayer.setInventory(List.of(new InventoryItem(ProjectileType.NORMAL_PROJECTILE, 1)));
        opponentPlayer.setInventory(List.of(new InventoryItem(ProjectileType.NORMAL_PROJECTILE, 1)));
    }

    @Test
    void testValidLaunchProjectile() {
        game.shoot(45, 21, ProjectileType.NORMAL_PROJECTILE);

        assertEquals(opponentPlayer, game.getCurrentPlayer());
        assertThat(opponentPlayer.getTank().getMaxHealth(), greaterThan(opponentPlayer.getHealth()));
    }

    @Test
    void testInvalidTooStrongLaunchProjectile() {
        boolean successful = game.shoot(45, 31, ProjectileType.NORMAL_PROJECTILE);

        assertFalse(successful);
        assertEquals(currentPlayer, game.getCurrentPlayer());
        assertEquals(opponentPlayer.getTank().getMaxHealth(), opponentPlayer.getHealth());
    }

    @Test
    void testInvalidEmptyInventoryProjectileLaunchProjectile() {
        currentPlayer.setInventory(List.of());
        boolean successful = game.shoot(45, 21, ProjectileType.NORMAL_PROJECTILE);

        assertFalse(successful);
        assertEquals(currentPlayer, game.getCurrentPlayer());
        assertEquals(opponentPlayer.getTank().getMaxHealth(), opponentPlayer.getHealth());
    }

    @Test
    void testInvalidNoProjectileProjectileLaunchProjectile() {
        currentPlayer.setInventory(List.of(new InventoryItem(ProjectileType.NORMAL_PROJECTILE, 0)));
        boolean successful = game.shoot(45, 21, ProjectileType.NORMAL_PROJECTILE);

        assertFalse(successful);
        assertEquals(currentPlayer, game.getCurrentPlayer());
        assertEquals(opponentPlayer.getTank().getMaxHealth(), opponentPlayer.getHealth());
    }

    @Test
    void testInvalidGameAlreadyOverLaunchProjectile() {
        opponentPlayer.getTank().reduceHealth(opponentPlayer.getHealth() - 1);
        game.shoot(45, 21, ProjectileType.NORMAL_PROJECTILE);
        boolean successful =  game.shoot(45, 21, ProjectileType.NORMAL_PROJECTILE);
        assertFalse(successful);
    }

    @Test
    void testNextPlayer() {
        game.shoot(45, 21, ProjectileType.NORMAL_PROJECTILE);

        Assertions.assertEquals(opponentPlayer, game.getCurrentPlayer());
    }

    @Test
    void testNextPlayerBackToFirst() {
        game.shoot(45, 21, ProjectileType.NORMAL_PROJECTILE);
        game.shoot(45, 21, ProjectileType.NORMAL_PROJECTILE);

        assertEquals(currentPlayer, game.getCurrentPlayer());
    }

    @Test
    void testMovePlayer() {
        int oldXPosition = currentPlayer.getxPosition();
        game.movePlayer(10, DirectionType.RIGHT);

        assertEquals(oldXPosition + 10, currentPlayer.getxPosition());
    }

    @Test
    void testGetBoard() {
        assertEquals(this.board.getMap(), game.getBoard());
    }

    @Test
    void testWinner() {
        opponentPlayer.getTank().reduceHealth(opponentPlayer.getHealth() - 1);

        game.shoot(45, 21, ProjectileType.NORMAL_PROJECTILE);

        boolean successful = game.movePlayer(1, DirectionType.RIGHT);

        assertFalse(successful);
        assertTrue(game.isGameEnd());
        assertEquals(currentPlayer, game.getWinner());
    }
}