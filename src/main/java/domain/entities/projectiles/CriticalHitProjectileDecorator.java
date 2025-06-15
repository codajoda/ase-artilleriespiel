package domain.entities.projectiles;

import domain.valueobjects.MyPoint;

import java.util.List;

public class CriticalHitProjectileDecorator implements Projectile {

    private final Projectile projectile;

    public CriticalHitProjectileDecorator(Projectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public int getDamage() {
        return projectile.getDamage() * 2;
    }

    @Override
    public List<MyPoint> getPattern(MyPoint p) {
        return projectile.getPattern(p);
    }

    @Override
    public ProjectileType getProjectileType() {
        return projectile.getProjectileType();
    }
}
