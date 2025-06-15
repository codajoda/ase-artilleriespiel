package infrastructure.events;

public enum DirectionType {

    LEFT("left"), RIGHT("right");

    public final String direction;

    DirectionType(String direction) {
        this.direction = direction;
    }

    public static DirectionType fromString(String direction) {
        for (DirectionType type : DirectionType.values()) {
            if (type.direction.equalsIgnoreCase(direction)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unbekannte Richtung: " + direction);
    }
}
