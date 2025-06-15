package domain.entities.inventory;

import domain.entities.projectiles.Projectile;
import domain.entities.projectiles.ProjectileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {

    private List<InventoryItem> inventoryItems;

    public Inventory() {
        inventoryItems = new ArrayList<>();
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public Projectile useInventoryItem(ProjectileType projectileType) {
        Optional<InventoryItem> item = inventoryItems.stream()
                .filter(inventoryItem -> inventoryItem.getProjectileType().equals(projectileType))
                .findFirst();
        return item.map(InventoryItem::useProjectile).orElse(null);
    }

    public boolean isEmpty() {
        for (InventoryItem item : inventoryItems) {
            if (item.getCount() > 0) {
                return false;
            }
        }
        return true;
    }
}
