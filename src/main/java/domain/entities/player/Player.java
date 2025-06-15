package domain.entities.player;

import domain.entities.inventory.Inventory;
import domain.entities.inventory.InventoryItem;
import domain.entities.tanks.Tank;
import domain.entities.projectiles.Projectile;
import domain.entities.projectiles.ProjectileType;

import java.util.List;

public class Player {
    private static int playerIncrement = 1;

    private final String name;
    private final Tank tank;
    private final int id;
    private Inventory inventory;
    private int xPosition;
    private int yPosition;

    public Player(String name, Tank tank) {
        this.name = name;
        this.tank = tank;
        this.inventory = new Inventory();
        this.id = playerIncrement;
        playerIncrement++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getFuel() {
        return tank.getFuel();
    }

    public int getHealth() { return tank.getHealth(); }

    public void refill() {
        tank.refill();
    }

    public void consumeFuel(int amount) {
        tank.consumeFuel(amount);
    }

    public void takeDamage(int amount) {
        tank.reduceHealth(amount);
    }

    public Tank getTank() {
        return tank;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public Projectile useProjectile(ProjectileType projectileType) { return inventory.useInventoryItem(projectileType); }

    public void setInventory(List<InventoryItem> inventoryItems) {
        inventory.setInventoryItems(inventoryItems);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean inventoryIsEmpty() { return inventory.isEmpty(); }

    public void reset() {
        this.inventory = new Inventory();
        this.tank.reset();
    }
}
