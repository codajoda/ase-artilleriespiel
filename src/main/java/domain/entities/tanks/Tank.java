package domain.entities.tanks;

public abstract class Tank {
    private final int width;
    private final int height;
    private final int maxHealth;
    private int currentHealth;
    private final int maxFuel;
    private int currentFuel;

    public Tank(int width, int height, int health, int fuel) {
        this.width = width;
        this.height = height;
        this.maxHealth = health;
        this.currentHealth = maxHealth;
        this.maxFuel = fuel;
        this.currentFuel = maxFuel;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getHealth() {
        return currentHealth;
    }
    public void reduceHealth(int amount) {
        currentHealth -= amount;
    }
    public int getFuel() {
        return currentFuel;
    }
    public void consumeFuel(int amount) {
        currentFuel -= amount;
    }
    public void refill() {
        currentFuel = maxFuel;
    }
    public int getMaxFuel() {
        return maxFuel;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public abstract TankType getTankType();

    public void reset() {
        this.currentHealth = maxHealth;
    }
}
