package domain.entities.projectiles;

import domain.valueobjects.MyPoint;

import java.util.List;

public class NormalProjectile implements Projectile {
    @Override
    public List<MyPoint> getPattern(MyPoint p) {
        return List.of(p);
    }

    @Override
    public ProjectileType getProjectileType() {
        return ProjectileType.NORMAL_PROJECTILE;
    }

    @Override
    public int getDamage() {
        return 40;
    }
}
