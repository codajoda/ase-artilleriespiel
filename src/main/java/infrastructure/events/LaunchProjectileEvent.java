package infrastructure.events;

public class LaunchProjectileEvent implements Event {

    private final int strength;
    private final int degrees;
    private final String projectile;

    public LaunchProjectileEvent(int degrees, int strength, String projectile) {
        this.strength = strength;
        this.degrees = degrees;
        this.projectile = projectile;
    }

    @Override
    public EventType getEventType() {
        return EventType.LAUNCH;
    }

    public int getStrength() {
        return strength;
    }

    public int getDegrees() {
        return degrees;
    }

    public String getProjectile() {
        return projectile;
    }
}
