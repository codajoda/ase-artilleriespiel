package domain.entities.projectiles;

import domain.valueobjects.MyPoint;

import java.util.ArrayList;
import java.util.List;

public class XProjectile implements Projectile {
    @Override
    public List<MyPoint> getPattern(MyPoint p) {
        List<MyPoint> result = new ArrayList<>();
        for (int i = -6; i <= 6; i++) {
            result.add(new MyPoint(p.x + i, p.y + i));
            result.add(new MyPoint(p.x - i, p.y + i));
            result.add(new MyPoint(p.x + i, p.y - i));
        }
        return result;
    }

    @Override
    public ProjectileType getProjectileType() {
        return ProjectileType.X_PROJECTILE;
    }

    @Override
    public int getDamage() {
        return 25;
    }
}
