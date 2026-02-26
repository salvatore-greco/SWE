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

    //FIXME: change event parameter into LocalDateTime and Duration
    public boolean isAvailable (Event event) {
        for (Event scheduled : scheduledEvents) {
            if (scheduled.overlaps(event)) {
                return false; // There is a scheduling conflict
            }
        }
        return true; // No conflicts, the room is available
    }

    //FIXME: spostare nella business logic
    public void scheduleEvent(Event newEvent) {
        if (!isAvailable(newEvent))
            throw new IllegalStateException("Room is not available");

        scheduledEvents.add(newEvent);
    }

}
