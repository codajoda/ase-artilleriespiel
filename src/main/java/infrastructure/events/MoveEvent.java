package infrastructure.events;

public class MoveEvent implements Event {

    private final int fuel;
    private final DirectionType direction;

    public MoveEvent(int fuel, DirectionType direction) {
        this.fuel = fuel;
        this.direction = direction;
    }

    @Override
    public EventType getEventType() {
        return EventType.MOVE;
    }

    public int getFuel() {
        return fuel;
    }

    public DirectionType getDirection() {
        return direction;
    }
}