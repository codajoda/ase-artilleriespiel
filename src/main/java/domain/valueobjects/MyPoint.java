package domain.valueobjects;

public class MyPoint {
    public final int x;
    public final int y;
    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o) {
        if (o.getClass() == this.getClass()) {
            return this.x == ((MyPoint) o).x && this.y == ((MyPoint) o).y;
        }
        return false;
    }
}
