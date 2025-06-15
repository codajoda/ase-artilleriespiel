package domain.entities.inventory;

import domain.entities.projectiles.Projectile;
import domain.entities.projectiles.ProjectileType;

public class InventoryItem {
    private final Projectile projectile;
    private int count;

    public InventoryItem(ProjectileType projectileType, int count) {
        this.count = count;
        this.projectile = projectileType.createProjectile();
    }

    public void incrementCount() {
        count++;
    }

    public int getCount() {
        return count;
    }

    public ProjectileType getProjectileType() {
        return projectile.getProjectileType();
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Projectile useProjectile() {
        if (count <= 0) {
            return null;
        }
        count--;
        return projectile;
    }
}
