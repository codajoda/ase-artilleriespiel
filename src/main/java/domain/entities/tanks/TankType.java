package domain.entities.tanks;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum TankType {
    SMALL("Small") {
        @Override
        public Tank createTank() {
            return new SmallTank();
        }
    },
    MEDIUM("Medium") {
        @Override
        public Tank createTank() {
            return new MediumTank();
        }
    },
    BIG("Big") {
        @Override
        public Tank createTank() {
            return new BigTank();
        }
    };

    private final String name;

    TankType(String name) {
        this.name = name;
    }

    public abstract Tank createTank();

    public String getName() {
        return name;
    }

    public static TankType fromString(String input) {
        for (TankType tt : values()) {
            if (tt.name.equalsIgnoreCase(input)) {
                return tt;
            }
        }
        throw new IllegalArgumentException("Unbekannter Tank-Typ: " + input);
    }

    public static String listAllNames() {
        return " " + Arrays.stream(values())
                .map(TankType::getName)
                .collect(Collectors.joining(", "));
    }
}