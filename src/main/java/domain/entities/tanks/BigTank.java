package domain.entities.tanks;

public class BigTank extends Tank {
    public BigTank() {
        super(20, 4, 150, 10);
    }
    @Override
    public TankType getTankType() {
        return TankType.BIG;
    }
}
