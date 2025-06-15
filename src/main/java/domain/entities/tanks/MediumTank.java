package domain.entities.tanks;

public class MediumTank extends Tank {
    public MediumTank() {
        super(12, 3, 100,15);    }

    @Override
    public TankType getTankType() {
        return TankType.MEDIUM;
    }
}
