package DomainModel;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public class Room {
    private int number;
    private short seats; //TODO: controlla se va bene FAI COMBACIARE COL DB
    // valutare se aggiungere una lista di eventi che si svolgono in quella stanza
    private ArrayList<Event> scheduledEvents;

    public Room(int number, short seats) {
        if (seats <= 0)
            throw new IllegalArgumentException("Seats must be positive");

        this.number = number;
        this.seats = seats;
        this.scheduledEvents = new ArrayList<>(); //TODO: controlla se serve un altro costruttore, passando la lista di eventi proveniente dal db
    }

    public int getNumber() {
        return number;
    }

    public short getSeats() {
        return seats;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setSeats(short seats) {
        this.seats = seats;
    }

    public ArrayList<Event> getScheduledEvents() {
        return scheduledEvents;
    }

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

    //FIXME: spostare nella business logic
    public void scheduleEvent(Event newEvent) {
        if (!isAvailable(newEvent.getStartDate(), newEvent.getEventDuration()))
            throw new IllegalStateException("Room is not available");

        scheduledEvents.add(newEvent);
    }

}
