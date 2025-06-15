package infrastructure.events;

public record AddPlayerEvent(String name, String tank) implements Event {

    @Override
    public EventType getEventType() {
        return EventType.ADD_PLAYER;
    }
}
