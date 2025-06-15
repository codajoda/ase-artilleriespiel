package domain.entities.tanks;

public class SmallTank extends Tank {
    public SmallTank() {
        super(5, 2, 70, 20);
    }
    @Override
    public TankType getTankType() {
        return TankType.SMALL;
    }
}
