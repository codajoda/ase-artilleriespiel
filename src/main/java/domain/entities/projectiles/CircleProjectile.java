package domain.entities.projectiles;

import domain.valueobjects.MyPoint;

import java.util.ArrayList;
import java.util.List;

public class CircleProjectile implements Projectile {
    @Override
    public List<MyPoint> getPattern(MyPoint p) {
        int radius = 3;
        int radiusSq = radius * radius;
        List<MyPoint> result = new ArrayList<>();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx*dx + dy*dy <= radiusSq) {
                    result.add(new MyPoint(p.x + dx, p.y + dy));
                }
            }
        }
        return result;
    }

    @Override
    public ProjectileType getProjectileType() {
        return ProjectileType.CIRCLE_PROJECTILE;
    }

    @Override
    public int getDamage() {
        return 30;
    }
}
