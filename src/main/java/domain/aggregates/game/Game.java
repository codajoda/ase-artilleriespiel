package domain.aggregates.game;

import domain.entities.board.BoardIntf;
import domain.entities.inventory.InventoryItem;
import domain.entities.player.Player;
import domain.entities.projectiles.CriticalHitProjectileDecorator;
import domain.entities.projectiles.Projectile;
import domain.entities.projectiles.ProjectileType;
import domain.repositories.PlayerRepositoryIntf;
import domain.rules.DamageRule;
import domain.rules.MoveRule;
import domain.rules.ShootRule;
import infrastructure.events.DirectionType;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class Game implements GameIntf {

    private final Random random = new Random();

    private List<Player> activePlayers;
    private Player currentPlayer;
    private final BoardIntf board;
    private Player winner;

    public Game(BoardIntf board) {
        this.board = board;
    }

    @Override
    public void initGame(PlayerRepositoryIntf playerRepository) {
        winner = null;
        activePlayers = new ArrayList<>(playerRepository.getPlayers());
        currentPlayer = activePlayers.get(0);

        int i = 0;
        for (Player player : activePlayers) {
            player.reset();
            player.setxPosition((board.getWidth()/activePlayers.size()) * i + (board.getWidth()/activePlayers.size())/2);
            player.setyPosition(board.getGround() + 1);
            setupInventory(player);
            board.setPlayerPositionOnBoard(player, player.getxPosition(), player.getyPosition());
            i++;
        }
    }

    private void setupInventory(Player player) {
        List<ProjectileType> projectileTypes = ProjectileType.listAllProjectileTypes();
        List<InventoryItem> inventoryItems = projectileTypes.stream().map(projectileType -> new InventoryItem(projectileType, 1)).toList();
        for (int i = 0; i <= 15; i++) {
            int rnd = random.nextInt(inventoryItems.size());
            inventoryItems.get(rnd).incrementCount();
        }
        player.setInventory(inventoryItems);
    }

    @Override
    public boolean shoot(int degrees, int strength, ProjectileType projectileType) {
        if (isGameEnd() || strength > 30) {
            return false;
        }
        board.clearProjectileTexturesFromBoard();
        for (Player player : activePlayers) {
            board.setPlayerPositionOnBoard(player, player.getxPosition(), player.getyPosition());
        }

        Projectile projectile = currentPlayer.useProjectile(projectileType);
        if (projectile == null) {
            return false;
        }
        List<Player> playersHit = ShootRule.execute(degrees, strength, activePlayers, currentPlayer, board, projectile);
        if (random.nextInt(100) < 20) {
            DamageRule.execute(playersHit, activePlayers, new CriticalHitProjectileDecorator(projectile));
        } else {
            DamageRule.execute(playersHit, activePlayers, projectile);
        }
        if (!isGameEnd()) {
            nextPlayer();
        } else {
            winner = activePlayers.get(0);
        }
        return true;
    }

    private void nextPlayer() {
        for (Player player : activePlayers) {
            player.refill();
            if (player.inventoryIsEmpty()) {
                setupInventory(player);
            }
        }
        if (activePlayers.indexOf(currentPlayer) == activePlayers.size() - 1) {
            currentPlayer = activePlayers.get(0);
        } else {
            currentPlayer = activePlayers.get(activePlayers.indexOf(currentPlayer) + 1);
        }
    }

    @Override
    public boolean movePlayer(int steps, DirectionType direction) {
        if (isGameEnd()) {
            return false;
        }
        board.clearProjectileTexturesFromBoard();
        for (Player player : activePlayers) {
            board.setPlayerPositionOnBoard(player, player.getxPosition(), player.getyPosition());
        }
        return MoveRule.execute(steps, direction, currentPlayer, board);
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public String[][] getBoard() {
        return board.getMap();
    }

    @Override
    public boolean isGameEnd() {
        return activePlayers.size() == 1;
    }

    @Override
    public Player getWinner() {
        return winner;
    }
}