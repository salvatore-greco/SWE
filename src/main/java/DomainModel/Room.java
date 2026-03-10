package DomainModel;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Room {
    private int number;
    private int seats; //TODO: controlla se va bene FAI COMBACIARE COL DB

    abstract public boolean isAvailable(LocalDateTime start, Duration duration);

    public Room(int number, int seats) {
        if (seats <= 0)
            throw new IllegalArgumentException("Seats must be positive");
        this.number = number;
        this.seats = seats;
    }

    public int getNumber() {
        return number;
    }

    public int getSeats() {
        return seats;
    }
}
