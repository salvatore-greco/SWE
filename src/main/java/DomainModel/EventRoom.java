package DomainModel;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class EventRoom extends Room{

    private ArrayList<Event> scheduledEvents;

    public EventRoom(int number, int seats) {
        super(number, seats);
        this.scheduledEvents = new ArrayList<>();
    }

    public EventRoom(int number, int seats, ArrayList<Event> scheduledEvents) {
        super(number, seats);
        this.scheduledEvents = scheduledEvents;
    }


    public List<Event> getScheduledEvents() {
        return Collections.unmodifiableList(scheduledEvents);
    }

    @Override
    public boolean isAvailable (LocalDateTime start, Duration duration) {
        if(start == null || duration == null || duration.isNegative() || duration.isZero())
            throw new IllegalArgumentException("Invalid time interval");

        LocalDateTime newEventEnd = start.plus(duration);
        for (Event event : scheduledEvents) {
            if (start.isBefore(event.getEndDate()) && newEventEnd.isAfter(event.getStartDate())) {
                return false; // Overlaps with an existing event
            }
        }
        return true; // No overlap, room is available
    }

    public void scheduleEvent(Event newEvent) {
        if (!isAvailable(newEvent.getStartDate(), newEvent.getEventDuration()))
            throw new IllegalStateException("Room is not available");

        scheduledEvents.add(newEvent);
    }

}
