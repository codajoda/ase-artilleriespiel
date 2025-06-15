package domain.entities.projectiles;

import domain.valueobjects.MyPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HorizontalProjectile implements Projectile {

    @Override
    public List<MyPoint> getPattern(MyPoint p) {
        List<MyPoint> result = new ArrayList<>();
        for (int i = -10; i <= 10; i++) {
            result.add(new MyPoint(p.x + i, p.y));
        }
        return result;
    }

    @Override
    public ProjectileType getProjectileType() {
        return ProjectileType.HORIZONTAL_PROJECTILE;
    }

    @Override
    public int getDamage() {
        return 20;
    }
}
