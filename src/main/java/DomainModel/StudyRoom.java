package DomainModel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudyRoom extends Room{
    private ArrayList<LibraryUser> reservedSeats;

    public StudyRoom(int number, int seats) {
        super(number, seats);
        reservedSeats = new ArrayList<>();
    }

    public StudyRoom(int number, int seats, ArrayList<LibraryUser> reservedSeats){
        super(number,seats);
        this.reservedSeats = reservedSeats;
    }

    public List<LibraryUser> getReservedSeats() {
        return Collections.unmodifiableList(reservedSeats);
    }

    @Override
    public boolean isAvailable(LocalDateTime start, Duration duration) {
        return false; // Le aule studio non possono essere prenotabili per eventi.
    }

    public void reserveSeat(LibraryUser user){
        if (hasUserReserved(user)){
            throw new IllegalStateException("User already has a reserved seat in this room");
        }
        if (isSeatAvailable()){
            throw new IllegalStateException("No more seats available in this room");
        }
        reservedSeats.add(user);
    }

    public boolean isSeatAvailable() {
        return reservedSeats.size() >= getSeats();
    }

    public boolean hasUserReserved(LibraryUser user) {
        return reservedSeats.contains(user);
    }

    public void leaveSeat(LibraryUser user){
        if (!hasUserReserved(user)){
            throw new IllegalStateException("User does not have a reserved seat in this room");
        }
        reservedSeats.remove(user);
    }
}
