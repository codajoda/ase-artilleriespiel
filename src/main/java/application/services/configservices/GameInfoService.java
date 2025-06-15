package application.services.configservices;

import domain.entities.inventory.InventoryItem;
import domain.entities.player.Player;
import domain.entities.tanks.TankType;
import domain.aggregates.game.GameIntf;

import java.util.List;

public class GameInfoService {

    private final GameIntf game;

    public GameInfoService(GameIntf game) {
        this.game = game;
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public String getCurrentPlayerProjectile() {
        List<InventoryItem> inventoryItems = game.getCurrentPlayer().getInventory().getInventoryItems();
        StringBuilder builder = new StringBuilder();
        for (InventoryItem item : inventoryItems) {
            builder.append(item.getProjectileType().getName()).append(": ").append(item.getCount()).append("\n");
        }
        return builder.toString();
    }

    public String[][] getBoard() {
        return game.getBoard();
    }

    public boolean checkGameEnd() {
        return game.isGameEnd();
    }
    public String getWinner() {
        return game.getWinner().getName();
    }

    public String getAllTanks() {
        return TankType.listAllNames();
    }

    public boolean currentPlayerCanMove() {
        return game.getCurrentPlayer().getFuel() > 0;
    }
}
