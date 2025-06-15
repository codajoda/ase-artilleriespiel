package domain.entities.projectiles;

import java.util.List;

public enum ProjectileType {
    NORMAL_PROJECTILE("Normal") {
        @Override
        public Projectile createProjectile() {
            return new NormalProjectile();
        }
    },
    CIRCLE_PROJECTILE("Circle") {
        @Override
        public Projectile createProjectile() {
            return new CircleProjectile();
        }
    },
    X_PROJECTILE("X") {
        @Override
        public Projectile createProjectile() {
            return new XProjectile();
        }
    },
    HORIZONTAL_PROJECTILE("Horizontal") {
        @Override
        public Projectile createProjectile() {
            return new HorizontalProjectile();
        }
    };

    private final String name;
    ProjectileType(String name) {
        this.name = name;
    }

    public abstract Projectile createProjectile();

    public String getName() {
        return name;
    }

    public static ProjectileType fromString(String input) {
        for (ProjectileType wt : values()) {
            if (wt.name.equalsIgnoreCase(input)) {
                return wt;
            }
        }
        throw new IllegalArgumentException("Unknown Projectile-Type: " + input);
    }
    public static List<ProjectileType> listAllProjectileTypes() {
        return List.of(values());
    }
}
